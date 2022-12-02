package io.github.rkeeves.richtext.auxiliary;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Map;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class CKEditorInstanceVanilla implements CKEditorInstance {

    private static final String ATTR_PRESSED = "aria-pressed";

    private static final Map<String, String> PRESSED_INVERSE = Map.ofEntries(
            Map.entry("true", "false"),
            Map.entry("false", "true")
    );

    private static final By LOCATOR_BTN_BOLD = By.cssSelector(".cke_button__bold");

    private static final By LOCATOR_BTN_UNDERLINE = By.cssSelector(".cke_button__underline");

    private final WebDriverWait await;

    private final By rootLocator;

    public static CKEditorInstanceVanilla of(final @NonNull WebDriverWait await, final @NonNull String id) {
        final var prefixedId = "cke_" + id;
        return new CKEditorInstanceVanilla(await, By.id(prefixedId));
    }

    @Override
    public CKEditorInstance toggleBold() {
        log.info("Attempting to toggle bold");
        toggleButton(LOCATOR_BTN_BOLD);
        return this;
    }

    @Override
    public CKEditorInstance toggleUnderline() {
        log.info("Attempting to toggle underline");
        toggleButton(LOCATOR_BTN_UNDERLINE);
        return this;
    }

    private void toggleButton(By buttonLocator) {
        final var chainedLocator = new ByChained(rootLocator, buttonLocator);
        final var driver = await.until(Function.identity());
        final var button = await.until(ExpectedConditions.elementToBeClickable(chainedLocator));
        final var oldState = await.until(d -> d.findElement(chainedLocator).getAttribute(ATTR_PRESSED));
        if (!PRESSED_INVERSE.containsKey(oldState)) {
            throw new IllegalStateException(ATTR_PRESSED + " had illegal value " + oldState);
        }
        final var expectedNewState = PRESSED_INVERSE.get(oldState);
        new Actions(driver)
                .moveToElement(button)
                .clickAndHold(button)
                .release(button)
                .perform();
        await.until(ExpectedConditions.attributeToBe(button, ATTR_PRESSED, expectedNewState));
    }

    @Override
    public String getData() {
        log.info("Attempting to get data");
        final var driver = await.until(Function.identity());
        await.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(new ByChained(rootLocator, By.tagName("iframe"))));
        final var body = driver.findElement(By.tagName("body"));
        final var rawInnerHtml = body.getAttribute("innerHTML");
        driver.switchTo().defaultContent();
        return rawInnerHtml;
    }

    public CKEditorInstance dataMustBe(final @NonNull String expected) {
        log.info("Attempting to ensure data is {}", expected);
        final var driver = await.until(Function.identity());
        await.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(new ByChained(rootLocator, By.tagName("iframe"))));
        final var body = driver.findElement(By.tagName("body"));
        await.until(d -> {
            final var result = body.getAttribute("innerHTML");
            if (result == null) return false;
            final var trimmed = result.trim()
                    .replaceAll("&nbsp;", " ");
            return expected.equals(trimmed);
        });
        return this;
    }

    @Override
    public CKEditorInstance insertText(@NonNull String text) {
        log.info("Attempting to insert text '{}'", text);
        final var driver = await.until(Function.identity());
        await.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(new ByChained(rootLocator, By.tagName("iframe"))));
        final var body = driver.findElement(By.tagName("body"));
        body.sendKeys(text, Keys.ARROW_LEFT, Keys.ARROW_RIGHT);
        driver.switchTo().defaultContent();
        return this;
    }
}

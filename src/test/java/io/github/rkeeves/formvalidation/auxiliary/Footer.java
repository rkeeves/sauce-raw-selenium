package io.github.rkeeves.formvalidation.auxiliary;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.Function;

@RequiredArgsConstructor(staticName = "of")
public final class Footer {

    private final WebDriverWait await;

    private final By locator;

    private static final By GG = By.cssSelector("div.footer_copy");

    public void scrollTo(boolean alignToTop) {
        final var footer = await.until(ExpectedConditions.visibilityOfElementLocated(locator));
        final var driver = await.until(Function.identity());
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(arguments[1])", footer, alignToTop);
    }

    public void mustContainEachOf(String... texts) {
        final var chained = new ByChained(locator, GG);
        for (var text : texts) {
            await.until(ExpectedConditions.textToBePresentInElementLocated(chained, text));
        }
    }
}

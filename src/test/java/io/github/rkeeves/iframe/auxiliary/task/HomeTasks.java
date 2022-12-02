package io.github.rkeeves.iframe.auxiliary.task;

import io.github.rkeeves.iframe.auxiliary.step.Step;
import io.github.rkeeves.iframe.auxiliary.step.Url;
import lombok.experimental.UtilityClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.HashSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.endsWith;

@UtilityClass
public class HomeTasks {

    private static final By ADS_IFRAME = By.cssSelector("iframe[src='ads.html']");

    private static final By SELENIUM_LIVE_PROJECT_LINK = By.cssSelector("a[href='http://www.guru99.com/live-selenium-project.html']");

    private static final By MENU = By.cssSelector("#rt-header .menu-block");

    private static final By GDPR_IFRAME = By.id("gdpr-consent-notice");

    public static Task<Void> visit() {
        return actor -> {
            actor.chain(Url.visit("https://demo.guru99.com/test/guru99home/"));
            return null;
        };
    }

    public static Step<Void> awaitAndAcceptGdpr() {
        return (driver, await) -> {
            await.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(GDPR_IFRAME));
            final var button = await.until(ExpectedConditions.elementToBeClickable(By.id("save")));
            new Actions(driver)
                    .clickAndHold(button)
                    .release(button)
                    .perform();
            driver.switchTo().defaultContent();
            await.until(ExpectedConditions.invisibilityOfElementLocated(GDPR_IFRAME));
            return null;
        };
    }

    public static Step<String> openLiveProjectOnNewTab() {
        return (driver, await) -> {
            final WebElement iframe = driver.findElement(ADS_IFRAME);
            driver.switchTo().frame(iframe);
            final var adLink = driver.findElement(SELENIUM_LIVE_PROJECT_LINK);
            final var adPic = adLink.findElement(By.tagName("img"));
            final var oldHandles = new HashSet<>(driver.getWindowHandles());
            assertThat(adPic.getAttribute("src"), endsWith("/test/guru99home/Jmeter720.png"));
            adPic.click();
            final var expectedWindowHandleCount = oldHandles.size() + 1;
            await.until(d -> d.getWindowHandles().size() == expectedWindowHandleCount);
            return driver.getWindowHandles().stream()
                    .filter(possiblyOldHandle -> !oldHandles.contains(possiblyOldHandle))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("After opening a new tab," +
                            "one should expect to have at least 1 new window handle, but there was none"));
        };
    }

    public static Step<Void> openMenuItem(String submenuText, String itemText) {
        return (driver, await) -> {
            final var submenuLocator = new ByChained(MENU, By.linkText(submenuText));
            final var submenu = await.until(ExpectedConditions.elementToBeClickable(submenuLocator));
            new Actions(driver).moveToElement(submenu).perform();
            await.until(ExpectedConditions.elementToBeClickable(By.linkText(itemText))).click();
            return null;
        };
    }


}

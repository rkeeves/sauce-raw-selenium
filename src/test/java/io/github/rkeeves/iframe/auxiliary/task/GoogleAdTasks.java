package io.github.rkeeves.iframe.auxiliary.task;

import io.github.rkeeves.iframe.auxiliary.step.Step;
import lombok.experimental.UtilityClass;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;

@UtilityClass
public class GoogleAdTasks {

    private static final By GOOGLE_AD_IFRAME = By.cssSelector("iframe[id^='google_ads_iframe']");

    private static final By AD_IFRAME = By.tagName("iframe");

    private static final By DISMISS = By.cssSelector("*[id='dismiss-button']");

    public static Step<Void> awaitAndClose() {
        return (driver, await) -> {
            await.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(GOOGLE_AD_IFRAME));
            try {
                await.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(AD_IFRAME));
                await.until(ExpectedConditions.elementToBeClickable(DISMISS)).click();
            } catch (TimeoutException ignored) {
                driver.switchTo().defaultContent();
                await.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(GOOGLE_AD_IFRAME));
                await.until(ExpectedConditions.elementToBeClickable(DISMISS)).click();
            }
            driver.switchTo().defaultContent();
            return null;
        };
    }
}

package io.github.rkeeves.iframe.auxiliary.task;

import io.github.rkeeves.iframe.auxiliary.step.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class TutorialTasks {

    private static final By GDPR_DIALOG = By.cssSelector(".fc-consent-root");

    private static final By GDPR_CONSENT_BUTTON = new ByChained(GDPR_DIALOG, By.cssSelector("button.fc-cta-consent"));

    public static Step<Void> awaitAndAcceptGdpr() {
        return (driver, await) -> {
            await.until(ExpectedConditions.visibilityOfElementLocated(GDPR_CONSENT_BUTTON)).click();
            return null;
        };
    }

    public static Step<Void> ensureTextIsShown(String text) {
        return (driver, await) -> {
            await.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format("//span[contains(., '%s')]", text))));
            return null;
        };
    }
}

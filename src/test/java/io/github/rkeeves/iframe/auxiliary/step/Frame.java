package io.github.rkeeves.iframe.auxiliary.step;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Frame {

    public static Step<Void> switchToDefaultContent() {
        return (driver, webDriverWait) -> {
            driver.switchTo().defaultContent();
            return null;
        };
    }

    public static Step<Void> awaitAndSwitch(By iframeLocator) {
        return (driver, await) -> {
            await.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframeLocator));
            return null;
        };
    }
}

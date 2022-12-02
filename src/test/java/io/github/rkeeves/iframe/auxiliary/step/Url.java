package io.github.rkeeves.iframe.auxiliary.step;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.openqa.selenium.support.ui.ExpectedConditions;

@UtilityClass
public class Url {

    public static Step<Void> mustBe(@NonNull String url) {
        return (driver, webDriverWait) -> {
            webDriverWait.until(ExpectedConditions.urlToBe(url));
            return null;
        };
    }

    public static Step<Void> visit(String url) {
        return (driver, await) -> {
            driver.navigate().to(url);
            return null;
        };
    }
}

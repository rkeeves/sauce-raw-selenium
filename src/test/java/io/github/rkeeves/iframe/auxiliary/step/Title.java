package io.github.rkeeves.iframe.auxiliary.step;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.openqa.selenium.support.ui.ExpectedConditions;

@UtilityClass
public class Title {

    public static Step<Void> mustBe(@NonNull String text) {
        return (driver, await) -> {
            await.until(ExpectedConditions.titleIs(text));
            return null;
        };
    }
}

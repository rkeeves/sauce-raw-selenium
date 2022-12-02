package io.github.rkeeves.purchase.auxiliary.selenium;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.openqa.selenium.By;

@UtilityClass
public class CustomBy {

    public By dataTest(final @NonNull String dataTest) {
        return By.cssSelector("*[data-test='" + dataTest + "']");
    }
}
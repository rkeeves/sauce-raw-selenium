package io.github.rkeeves.formvalidation.auxiliary;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.Arrays;

@UtilityClass
public class ExpectClass {

    public static ExpectedCondition<WebElement> toContain(final @NonNull By by, final @NonNull String cls) {
        return new ExpectedCondition<>() {
            public WebElement apply(WebDriver driver) {
                final var element = driver.findElement(by);
                final var classes = element.getAttribute("class");
                if (classes == null) return null;
                final var match = Arrays.asList(classes.split(" ")).contains(cls);
                return match ? element : null;
            }

            public String toString() {
                return String.format("css classes to contain \"%s\".", cls);
            }
        };
    }
}

package io.github.rkeeves.purchase.auxiliary.selenium;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ExpectTexts {

    public static ExpectedCondition<Boolean> toBe(final @NonNull By by, final @NonNull  List<String> expected) {
        return new ExpectedCondition<>() {

            List<String> last = Collections.emptyList();

            public Boolean apply(WebDriver driver) {
                final var elements = driver.findElements(by);
                final var texts = elements.stream()
                        .map(WebElement::getText)
                        .map(s -> s == null ? "" : s)
                        .map(String::trim)
                        .collect(Collectors.toList());
                last = texts;
                return texts.equals(expected);
            }

            public String toString() {
                return String.format("texts to be \"%s\", but was \"%s\"", expected, last);
            }
        };
    }
}

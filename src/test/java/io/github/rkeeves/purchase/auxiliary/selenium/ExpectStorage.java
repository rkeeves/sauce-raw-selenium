package io.github.rkeeves.purchase.auxiliary.selenium;

import io.github.rkeeves.purchase.auxiliary.storage.StorageKind;
import lombok.experimental.UtilityClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.support.ui.ExpectedCondition;

@UtilityClass
public class ExpectStorage {

    public static ExpectedCondition<Boolean> toHaveEntry(StorageKind storageKind, String key, String expectedValue) {
        return new ExpectedCondition<>() {

            String last = "";

            public Boolean apply(WebDriver driver) {
                final var webStorage = (WebStorage) driver;
                final var theStorage = StorageKind.LOCAL.equals(storageKind) ? webStorage.getLocalStorage() : webStorage.getSessionStorage();
                final var rawValue = theStorage.getItem(key);
                return expectedValue.equals(rawValue);
            }

            public String toString() {
                return String.format("expected %s to have item with key '%s' and value '%s', but value was '%s'",
                        storageKind,
                        key,
                        expectedValue,
                        last);
            }
        };
    }

    public static ExpectedCondition<Boolean> toNotHaveEntry(StorageKind storageKind, String key) {
        return new ExpectedCondition<>() {

            public Boolean apply(WebDriver driver) {
                final var webStorage = (WebStorage) driver;
                final var theStorage = StorageKind.LOCAL.equals(storageKind) ? webStorage.getLocalStorage() : webStorage.getSessionStorage();
                final var rawValue = theStorage.getItem(key);
                return rawValue == null || "".equals(rawValue);
            }

            public String toString() {
                return String.format("expected %s to NOT have item with key '%s', but it existed",
                        storageKind,
                        key);
            }
        };
    }
}

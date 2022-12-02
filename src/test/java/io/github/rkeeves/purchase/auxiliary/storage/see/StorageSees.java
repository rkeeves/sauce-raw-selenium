package io.github.rkeeves.purchase.auxiliary.storage.see;

import io.github.rkeeves.purchase.auxiliary.selenium.ExpectStorage;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StorageSees {

    public static StorageSee entryToBe(String key, String expectedValue) {
        return (await, storageKind) -> {
            await.until(ExpectStorage.toHaveEntry(storageKind, key, expectedValue));
        };
    }

    public static StorageSee entryToNotExist(String key) {
        return (await, storageKind) -> {
            await.until(ExpectStorage.toNotHaveEntry(storageKind, key));
        };
    }
}

package io.github.rkeeves.purchase.auxiliary.storage.get;

import io.github.rkeeves.purchase.auxiliary.storage.StorageKind;
import org.openqa.selenium.support.ui.WebDriverWait;

public interface StorageGet<T> {

    T get(final WebDriverWait await, final StorageKind storageKind);
}

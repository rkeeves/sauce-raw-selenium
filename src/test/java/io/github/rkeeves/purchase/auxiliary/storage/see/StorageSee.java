package io.github.rkeeves.purchase.auxiliary.storage.see;

import io.github.rkeeves.purchase.auxiliary.storage.StorageKind;
import org.openqa.selenium.support.ui.WebDriverWait;

public interface StorageSee {

    void see(final WebDriverWait await, final StorageKind storageKind);
}

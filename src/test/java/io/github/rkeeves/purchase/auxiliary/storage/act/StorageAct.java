package io.github.rkeeves.purchase.auxiliary.storage.act;

import io.github.rkeeves.purchase.auxiliary.storage.StorageKind;
import org.openqa.selenium.support.ui.WebDriverWait;

public interface StorageAct {

    void act(final WebDriverWait await, final StorageKind storageKind);
}

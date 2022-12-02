package io.github.rkeeves.purchase.auxiliary.bot;

import io.github.rkeeves.purchase.auxiliary.element.act.ElementAct;
import io.github.rkeeves.purchase.auxiliary.element.get.ElementGet;
import io.github.rkeeves.purchase.auxiliary.element.see.ElementSee;
import io.github.rkeeves.purchase.auxiliary.page.act.PageAct;
import io.github.rkeeves.purchase.auxiliary.page.get.PageGet;
import io.github.rkeeves.purchase.auxiliary.page.see.PageSee;
import io.github.rkeeves.purchase.auxiliary.storage.StorageKind;
import io.github.rkeeves.purchase.auxiliary.storage.act.StorageAct;
import io.github.rkeeves.purchase.auxiliary.storage.get.StorageGet;
import io.github.rkeeves.purchase.auxiliary.storage.see.StorageSee;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.Supplier;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
final class DefaultBot implements Bot {

    @Getter
    private final WebDriverWait await;

    @Override
    public Bot fail(Supplier<RuntimeException> supplier) {
        throw supplier.get();
    }

    @Override
    public Bot act(@NonNull PageAct pageAct) {
        pageAct.act(await);
        return this;
    }

    @Override
    public <T> T get(@NonNull PageGet<T> pageGet) {
        return pageGet.get(await);
    }

    @Override
    public Bot see(@NonNull PageSee pageSee) {
        pageSee.see(await);
        return this;
    }

    @Override
    public Bot act(@NonNull By by, @NonNull ElementAct elementAct) {
        elementAct.act(this, await, by);
        return this;
    }

    @Override
    public <T> T get(@NonNull By by, @NonNull ElementGet<T> elementGet) {
        return elementGet.get(this, await, by);
    }

    @Override
    public Bot see(@NonNull By by, @NonNull ElementSee elementSee) {
        elementSee.see(this, await, by);
        return this;
    }

    @Override
    public Bot act(@NonNull StorageKind storageKind, @NonNull StorageAct storageAct) {
        storageAct.act(await, storageKind);
        return this;
    }

    @Override
    public <T> T get(@NonNull StorageKind storageKind, @NonNull StorageGet<T> storageGet) {
        return storageGet.get(await, storageKind);
    }

    @Override
    public Bot see(@NonNull StorageKind storageKind, @NonNull StorageSee storageSee) {
        storageSee.see(await, storageKind);
        return this;
    }
}

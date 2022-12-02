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
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.Supplier;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
final class ObservableBot implements Bot {

    private final Bot bot;

    private final ThrowableObserver throwableObserver;

    @Override
    public WebDriverWait getAwait() {
        return bot.getAwait();
    }

    @Override
    public Bot fail(Supplier<RuntimeException> supplier) {
        attempt(() -> bot.fail(supplier));
        return this;
    }

    @Override
    public Bot act(@NonNull PageAct pageAct) {
        attempt(() -> bot.act(pageAct));
        return this;
    }

    @Override
    public <T> T get(@NonNull PageGet<T> pageGet) {
        return attempt(() -> bot.get(pageGet));
    }

    @Override
    public Bot see(@NonNull PageSee pageSee) {
        attempt(() -> bot.see(pageSee));
        return this;
    }

    @Override
    public Bot act(@NonNull By by, @NonNull ElementAct elementAct) {
        attempt(() -> bot.act(by, elementAct));
        return this;
    }

    @Override
    public <T> T get(@NonNull By by, @NonNull ElementGet<T> elementGet) {
        return attempt(() -> bot.get(by, elementGet));
    }

    @Override
    public Bot see(@NonNull By by, @NonNull ElementSee elementSee) {
        attempt(() -> bot.see(by, elementSee));
        return this;
    }

    @Override
    public Bot act(@NonNull StorageKind storageKind, @NonNull StorageAct storageAct) {
        attempt(() -> bot.act(storageKind, storageAct));
        return this;
    }

    @Override
    public <T> T get(@NonNull StorageKind storageKind, @NonNull StorageGet<T> storageGet) {
        return attempt(() -> bot.get(storageKind, storageGet));
    }

    @Override
    public Bot see(@NonNull StorageKind storageKind, @NonNull StorageSee storageSee) {
        attempt(() -> bot.see(storageKind, storageSee));
        return this;
    }

    private <T> T attempt(Supplier<T> effect) {
        try {
            return effect.get();
        } catch (Throwable originalCause) {
            try {
                throwableObserver.onFail(bot.getAwait(), originalCause);
            } catch (Exception e) {
                log.error("The throwable observer encountered error", e);
            }
            throw originalCause;
        }
    }
}

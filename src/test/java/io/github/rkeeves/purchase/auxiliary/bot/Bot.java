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
import lombok.NonNull;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.Supplier;


public interface Bot {

    static Bot of(final @NonNull WebDriverWait await) {
        return new DefaultBot(await);
    }

    static Bot withObserver(final ThrowableObserver throwableObserver, final Bot bot) {
        return new ObservableBot(bot, throwableObserver);
    }

    WebDriverWait getAwait();

    Bot fail(Supplier<RuntimeException> supplier);

    Bot act(final @NonNull PageAct pageAct);

    <T> T get(final @NonNull PageGet<T> pageGet);

    Bot see(final @NonNull PageSee pageSee);

    Bot act(final @NonNull By by, final @NonNull ElementAct elementAct);

    <T> T get(final @NonNull By by, final @NonNull ElementGet<T> elementGet);

    Bot see(final @NonNull By by, final @NonNull ElementSee elementSee);

    Bot act(final @NonNull StorageKind storageKind, final @NonNull StorageAct storageAct);

    <T> T get(final @NonNull StorageKind storageKind, final @NonNull StorageGet<T> storageGet);

    Bot see(final @NonNull StorageKind storageKind, final @NonNull StorageSee storageSee);
}

package io.github.rkeeves.purchase.auxiliary.bot;

import org.openqa.selenium.support.ui.WebDriverWait;

public interface ThrowableObserver {

    default void onFail(final WebDriverWait webDriverWait, final Throwable t) throws Exception {

    }
}

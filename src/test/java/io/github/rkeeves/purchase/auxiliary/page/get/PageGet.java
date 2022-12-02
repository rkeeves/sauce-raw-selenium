package io.github.rkeeves.purchase.auxiliary.page.get;

import org.openqa.selenium.support.ui.WebDriverWait;

public interface PageGet<T> {

    T get(final WebDriverWait await);
}

package io.github.rkeeves.purchase.auxiliary.element.get;

import io.github.rkeeves.purchase.auxiliary.bot.Bot;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

public interface ElementGet<T> {

    T get(final Bot bot, final WebDriverWait await, final By by);
}

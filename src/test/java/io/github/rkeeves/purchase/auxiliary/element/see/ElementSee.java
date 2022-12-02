package io.github.rkeeves.purchase.auxiliary.element.see;

import io.github.rkeeves.purchase.auxiliary.bot.Bot;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

public interface ElementSee {

    void see(final Bot bot, final WebDriverWait await, final By by);
}

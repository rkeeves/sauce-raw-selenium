package io.github.rkeeves.purchase.auxiliary.element.act;

import io.github.rkeeves.purchase.auxiliary.bot.Bot;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

public interface ElementAct {

    void act(final Bot bot, final WebDriverWait await, final By by);
}

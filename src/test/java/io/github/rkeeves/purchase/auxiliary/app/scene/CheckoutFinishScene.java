package io.github.rkeeves.purchase.auxiliary.app.scene;

import io.github.rkeeves.formvalidation.auxiliary.CustomBy;
import io.github.rkeeves.purchase.auxiliary.bot.Bot;
import io.github.rkeeves.purchase.auxiliary.element.act.ElementActs;
import io.github.rkeeves.purchase.auxiliary.page.see.PageSees;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;

@RequiredArgsConstructor(staticName = "of")
public class CheckoutFinishScene {

    private static final String URL = "https://www.saucedemo.com/checkout-complete.html";

    private static final By BACK_HOME = CustomBy.dataTest("back-to-products");

    private final Bot bot;

    public CheckoutFinishScene mustBeAt() {
        bot.see(PageSees.urlIs(URL));
        return this;
    }

    public CheckoutFinishScene followLinkHome() {
        bot.act(BACK_HOME, ElementActs.click());
        return this;
    }
}

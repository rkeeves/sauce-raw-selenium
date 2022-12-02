package io.github.rkeeves.purchase.auxiliary.app.scene;

import io.github.rkeeves.formvalidation.auxiliary.CustomBy;
import io.github.rkeeves.purchase.auxiliary.bot.Bot;
import io.github.rkeeves.purchase.auxiliary.element.act.ElementActs;
import io.github.rkeeves.purchase.auxiliary.page.see.PageSees;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;

@RequiredArgsConstructor(staticName = "of")
public class CheckoutStepOneScene {

    private static final String URL = "https://www.saucedemo.com/checkout-step-one.html";

    private static final By FIRST_NAME = CustomBy.dataTest("firstName");

    private static final By LAST_NAME = CustomBy.dataTest("lastName");

    private static final By POSTAL_CODE = CustomBy.dataTest("postalCode");

    private static final By CONTINUE_CHECKOUT = CustomBy.dataTest("continue");

    private final Bot bot;

    public CheckoutStepOneScene mustBeAt() {
        bot.see(PageSees.urlIs(URL));
        return this;
    }

    public CheckoutStepOneScene enterFistname(String firstName) {
        bot.act(FIRST_NAME, ElementActs.setValue(firstName));
        return this;
    }

    public CheckoutStepOneScene enterLastName(String lastName) {
        bot.act(LAST_NAME, ElementActs.setValue(lastName));
        return this;
    }

    public CheckoutStepOneScene enterPostalCode(String postalCode) {
        bot.act(POSTAL_CODE, ElementActs.setValue(postalCode));
        return this;
    }

    public CheckoutStepOneScene followContinue() {
        bot.act(CONTINUE_CHECKOUT, ElementActs.click());
        return this;
    }
}

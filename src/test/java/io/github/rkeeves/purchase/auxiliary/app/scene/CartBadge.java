package io.github.rkeeves.purchase.auxiliary.app.scene;

import io.github.rkeeves.purchase.auxiliary.bot.Bot;
import io.github.rkeeves.purchase.auxiliary.element.act.ElementActs;
import io.github.rkeeves.purchase.auxiliary.element.see.ElementSees;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;

@RequiredArgsConstructor(staticName = "of")
public class CartBadge {

    private static final By CART_BADGE = By.cssSelector(".shopping_cart_container .shopping_cart_badge");

    private static final By CART_LINK = By.cssSelector(".shopping_cart_link");

    private final Bot bot;

    public CartBadge mustShow(int number) {
        bot.see(CART_BADGE, ElementSees.text(Integer.toString(number)));
        return this;
    }

    public CartBadge followCartLink() {
        bot.act(CART_LINK, ElementActs.click());
        return this;
    }
}

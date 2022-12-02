package io.github.rkeeves.purchase.auxiliary.app.scene;

import io.github.rkeeves.formvalidation.auxiliary.CustomBy;
import io.github.rkeeves.purchase.auxiliary.app.model.Product;
import io.github.rkeeves.purchase.auxiliary.bot.Bot;
import io.github.rkeeves.purchase.auxiliary.element.act.ElementActs;
import io.github.rkeeves.purchase.auxiliary.element.see.ElementSees;
import io.github.rkeeves.purchase.auxiliary.page.see.PageSees;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RequiredArgsConstructor(staticName = "of")
public class CartScene {

    private static final String URL = "https://www.saucedemo.com/cart.html";

    private static final By LINK_TO_CHECKOUT = CustomBy.dataTest("checkout");

    private static final By ITEM_NAME = By.cssSelector(".inventory_item_name");

    private static final By ITEM_DESCRIPTION = By.cssSelector(".inventory_item_desc");

    private static final By ITEM_PRICE = By.cssSelector(".inventory_item_price");

    private static final By ITEM_QUANTITY = By.cssSelector(".cart_quantity");

    private final Bot bot;

    public CartScene mustBeAt() {
        bot.see(PageSees.urlIs(URL));
        return this;
    }

    public CartScene mustHaveProducts(List<Product> products) {

        final var names = products.stream()
                .map(Product::getName)
                .collect(Collectors.toList());
        bot.see(ITEM_NAME, ElementSees.texts(names));

        final var prices = products.stream()
                .map(Product::getPrice)
                .map(dbl -> String.format(Locale.US, "$%.2f", dbl))
                .collect(Collectors.toList());
        bot.see(ITEM_PRICE, ElementSees.texts(prices));

        final var descriptions = products.stream()
                .map(Product::getDescription)
                .collect(Collectors.toList());
        bot.see(ITEM_DESCRIPTION, ElementSees.texts(descriptions));


        final var quantities = products.stream()
                .map(product -> "1")
                .collect(Collectors.toList());
        bot.see(ITEM_QUANTITY, ElementSees.texts(quantities));
        return this;
    }

    public CartScene followCheckoutLink() {
        bot.act(LINK_TO_CHECKOUT, ElementActs.click());
        return this;
    }
}

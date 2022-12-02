package io.github.rkeeves.purchase.auxiliary.app.scene;

import io.github.rkeeves.formvalidation.auxiliary.CustomBy;
import io.github.rkeeves.purchase.auxiliary.app.model.Product;
import io.github.rkeeves.purchase.auxiliary.app.model.ProductKey;
import io.github.rkeeves.purchase.auxiliary.bot.Bot;
import io.github.rkeeves.purchase.auxiliary.element.act.ElementActs;
import io.github.rkeeves.purchase.auxiliary.page.see.PageSees;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;

import java.util.Map;

@RequiredArgsConstructor(staticName = "of")
public class InventoryScene {

    private static final String URL = "https://www.saucedemo.com/inventory.html";

    private static final By ADD_LIGHT = CustomBy.dataTest("add-to-cart-sauce-labs-bike-light");

    private static final By ADD_BOLT_SHIRT = CustomBy.dataTest("add-to-cart-sauce-labs-bolt-t-shirt");

    private static final By ADD_ONESIE = CustomBy.dataTest("add-to-cart-sauce-labs-onesie");

    private static final By ADD_TATT = CustomBy.dataTest("add-to-cart-test.allthethings()-t-shirt-(red)");

    private static final By ADD_BACKPACK = CustomBy.dataTest("add-to-cart-sauce-labs-backpack");

    private static final By ADD_JACKET = CustomBy.dataTest("add-to-cart-sauce-labs-fleece-jacket");

    private static final Map<Integer, By> ADD_BY_PRODUCT_KEY = Map.ofEntries(
            Map.entry(ProductKey.LIGHT.getId(), ADD_LIGHT),
            Map.entry(ProductKey.BOLT_SHIRT.getId(), ADD_BOLT_SHIRT),
            Map.entry(ProductKey.ONESIE.getId(), ADD_ONESIE),
            Map.entry(ProductKey.TATT.getId(), ADD_TATT),
            Map.entry(ProductKey.BACKPACK.getId(), ADD_BACKPACK),
            Map.entry(ProductKey.JACKET.getId(), ADD_JACKET)
    );

    private final Bot bot;

    public InventoryScene mustBeAt() {
        bot.see(PageSees.urlIs(URL));
        return this;
    }

    public InventoryScene addToCart(Product product) {
        if (product.getId() == null || !ADD_BY_PRODUCT_KEY.containsKey(product.getId())) {
            bot.fail(() -> new RuntimeException(
                    String.format("The test wanted to add a product by id '%d', but add button was not defined",
                            product.getId()))
            );
        } else {
            final var button = ADD_BY_PRODUCT_KEY.get(product.getId());
            bot.act(button, ElementActs.click());
        }
        return this;
    }
}

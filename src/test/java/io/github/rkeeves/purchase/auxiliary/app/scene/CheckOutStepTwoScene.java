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
public class CheckOutStepTwoScene {

    private static final String URL = "https://www.saucedemo.com/checkout-step-two.html";

    private static final By ITEM_NAME = By.cssSelector(".inventory_item_name");

    private static final By ITEM_QUANTITY = By.cssSelector(".cart_quantity");

    private static final By SUBTOTAL = By.cssSelector(".summary_subtotal_label");

    private static final By TAX = By.cssSelector(".summary_tax_label");

    private static final By TOTAL = By.cssSelector(".summary_total_label");

    private static final By FINISH_CHECKOUT = CustomBy.dataTest("finish");

    private final Bot bot;


    public CheckOutStepTwoScene mustBeAt() {
        bot.see(PageSees.urlIs(URL));
        return this;
    }

    public CheckOutStepTwoScene mustHaveProducts(List<Product> products) {
        final var names = products.stream()
                .map(Product::getName)
                .collect(Collectors.toList());
        bot.see(ITEM_NAME, ElementSees.texts(names));
        final var quantities =  products.stream()
                .map(s -> "1")
                .collect(Collectors.toList());
        bot.see(ITEM_QUANTITY, ElementSees.texts(quantities));
        final var subTotal = products.stream().map(Product::getPrice).reduce(0.0, Double::sum);
        bot.see(SUBTOTAL, ElementSees.text(String.format(Locale.US, "Item total: $%.2f", subTotal)));
        return this;
    }

    public CheckOutStepTwoScene followFinish() {
        bot.act(FINISH_CHECKOUT, ElementActs.click());
        return this;
    }
}

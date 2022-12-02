package io.github.rkeeves.formvalidation.auxiliary;

import lombok.NonNull;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.Consumer;

public final class InventoryPage {

    private final WebDriverWait await;

    private final Page page;

    private final Footer footer;

    private InventoryPage(final @NonNull WebDriverWait await) {
        this.await = await;
        this.page = Page.of(await, "https://www.saucedemo.com/inventory.html");
        this.footer = Footer.of(await, By.tagName("footer"));
    }

    public static InventoryPage of(final @NonNull WebDriverWait await) {
        return new InventoryPage(await);
    }

    public InventoryPage page(Consumer<Page> action) {
        action.accept(page);
        return this;
    }

    public InventoryPage footer(Consumer<Footer> action) {
        action.accept(footer);
        return this;
    }
}

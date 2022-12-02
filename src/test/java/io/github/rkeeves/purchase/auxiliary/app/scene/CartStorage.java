package io.github.rkeeves.purchase.auxiliary.app.scene;

import io.github.rkeeves.purchase.auxiliary.app.model.Product;
import io.github.rkeeves.purchase.auxiliary.bot.Bot;
import io.github.rkeeves.purchase.auxiliary.storage.StorageKind;
import io.github.rkeeves.purchase.auxiliary.storage.see.StorageSees;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor(staticName = "of")
public class CartStorage {

    private static final String KEY_CART_CONTENTS = "cart-contents";

    private final Bot bot;

    public CartStorage mustHaveProducts(List<Product> products) {
        if (products.isEmpty()) {
            bot.see(StorageKind.LOCAL, StorageSees.entryToNotExist(KEY_CART_CONTENTS));
        } else {
            final var commaSeparatedInts = products.stream()
                    .map(Product::getId)
                    .map(Objects::toString)
                    .collect(Collectors.joining(","));
            bot.see(StorageKind.LOCAL, StorageSees.entryToBe(KEY_CART_CONTENTS, String.format("[%s]", commaSeparatedInts)
            ));
        }
        return this;
    }
}

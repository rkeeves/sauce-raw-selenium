package io.github.rkeeves.purchase.auxiliary.assumptions;

import io.github.rkeeves.purchase.auxiliary.app.model.Credential;
import io.github.rkeeves.purchase.auxiliary.app.model.Credentials;
import io.github.rkeeves.purchase.auxiliary.app.model.Product;
import io.github.rkeeves.purchase.auxiliary.app.model.ProductKey;
import io.github.rkeeves.purchase.auxiliary.app.model.Products;
import lombok.experimental.UtilityClass;
import org.junit.jupiter.api.Assumptions;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class DataAssumptions {

    public static Credential assumeCredentialByUsername(final Credentials credentials, final String username) {
        final var possibleCredential = credentials.findByUserName(username);
        Assumptions.assumeTrue(possibleCredential.isPresent(),
                "test assumes credential by username " + username + " exists in json");
        return possibleCredential.get();
    }

    public static List<Product> assumeProductsByKey(final Products products, ProductKey... keys) {
        final List<Product> someProducts = new ArrayList<>();
        for (var key : keys) {
            final var possibleProduct = products.findById(key.getId());
            Assumptions.assumeTrue(
                    possibleProduct.isPresent(),
                    "test assumes product by id " + key.getId() + " exists in json");
            someProducts.add(possibleProduct.get());
        }
        Assumptions.assumeTrue(
                keys.length == someProducts.size(),
                String.format("test assumes passed in keys size (%d) and someProducts size (%d) to be equal",
                        keys.length,
                        someProducts.size())
        );
        return someProducts;
    }
}

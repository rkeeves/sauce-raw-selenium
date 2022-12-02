package io.github.rkeeves.purchase.auxiliary.app.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
public final class Products {

    @SerializedName("products")
    private List<Product> products;

    public Optional<Product> findById(final Integer id) {
        return products.stream()
                .filter(product -> id.equals(product.getId()))
                .findFirst();
    }
}

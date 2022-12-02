package io.github.rkeeves.purchase.auxiliary.junit;

import com.google.gson.Gson;
import io.github.rkeeves.purchase.auxiliary.app.model.Products;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.io.IOException;

public final class ProductStore implements ParameterResolver {

    private static final ExtensionContext.Namespace CREDENTIAL_NAMESPACE = ExtensionContext.Namespace.create("io.github.rkeeves.products");

    private static final String KEY = "Products";

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter()
                .getType()
                .equals(Products.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        final var cached = extensionContext.getRoot().getStore(CREDENTIAL_NAMESPACE).get(KEY);
        if (cached != null) return cached;
        try {
            final var raw = JsonResources.readRaw("products.json");
            final var products = new Gson().fromJson(raw, Products.class);
            extensionContext.getRoot().getStore(CREDENTIAL_NAMESPACE).put(KEY, products);
            return products;
        } catch (IOException e) {
            throw new ParameterResolutionException("Failed to read products file", e);
        }
    }
}

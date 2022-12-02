package io.github.rkeeves.purchase.auxiliary.junit;

import com.google.gson.Gson;
import io.github.rkeeves.purchase.auxiliary.app.model.Credentials;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.io.IOException;

public final class CredentialStore implements ParameterResolver {

    private static final ExtensionContext.Namespace CREDENTIAL_NAMESPACE = ExtensionContext.Namespace.create("io.github.rkeeves.credential");

    private static final String KEY = "Credentials";

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter()
                .getType()
                .equals(Credentials.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        final var cached = extensionContext.getRoot().getStore(CREDENTIAL_NAMESPACE).get(KEY);
        if (cached != null) return cached;
        try {
            final var raw = JsonResources.readRaw("credentials.json");
            final var credentials = new Gson().fromJson(raw, Credentials.class);
            extensionContext.getRoot().getStore(CREDENTIAL_NAMESPACE).put(KEY, credentials);
            return credentials;
        } catch (IOException e) {
            throw new ParameterResolutionException("Failed to read credentials file", e);
        }
    }
}

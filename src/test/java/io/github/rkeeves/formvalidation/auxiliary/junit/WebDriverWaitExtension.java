package io.github.rkeeves.formvalidation.auxiliary.junit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

@Slf4j
@RequiredArgsConstructor(staticName = "of")
public final class WebDriverWaitExtension implements ParameterResolver, TestExecutionExceptionHandler {

    private static final ExtensionContext.Namespace CREDENTIAL_NAMESPACE = ExtensionContext.Namespace.create("io.github.rkeeves.webdriverwait");

    private static final String KEY = "Await";

    private final Supplier<WebDriverWait> factory;

    private final BiConsumer<Throwable, WebDriverWait> onFail;

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter()
                .getType()
                .equals(WebDriverWait.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        final var cached = extensionContext.getStore(CREDENTIAL_NAMESPACE).get(KEY);
        if (cached != null)  throw new ParameterResolutionException("There was one driver already in context");
        try {
            final var await = factory.get();
            extensionContext.getStore(CREDENTIAL_NAMESPACE).put(KEY, new QuittingWebDriver(await));
            return await;
        } catch (Throwable t) {
            throw new ParameterResolutionException("Failed to create webdriver", t);
        }
    }

    @Override
    public void handleTestExecutionException(ExtensionContext extensionContext, Throwable throwable) throws Throwable {
        try {
            final var quittingWebDriver = extensionContext.getStore(CREDENTIAL_NAMESPACE).get(KEY);
            if (quittingWebDriver instanceof QuittingWebDriver) {
                onFail.accept(throwable, ((QuittingWebDriver) quittingWebDriver).getWebDriverWait());
            }
        } catch (Throwable t) {
            throw new RuntimeException("No webdriver was in the Extension Context store", t);
        }
    }
}

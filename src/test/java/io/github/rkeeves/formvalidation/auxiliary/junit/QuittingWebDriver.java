package io.github.rkeeves.formvalidation.auxiliary.junit;

import lombok.Value;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.Function;

@Value
public class QuittingWebDriver implements ExtensionContext.Store.CloseableResource {

    WebDriverWait webDriverWait;

    @Override
    public void close() throws Throwable {
        webDriverWait.until(Function.identity()).quit();
    }
}

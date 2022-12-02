package io.github.rkeeves.formvalidation.auxiliary;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.Function;

@RequiredArgsConstructor(staticName = "of")
public final class Page {

    private final WebDriverWait await;

    private final String url;

    public void open() {
        await.until(Function.identity()).navigate().to(url);
    }

    public void mustBeLoaded() {
        await.until(ExpectedConditions.urlToBe(url));
    }
}

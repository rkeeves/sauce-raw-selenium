package io.github.rkeeves.iframe.auxiliary.step;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Js {

    public static Step<Void> syncScript(String script, Object... args) {
        return (driver, await) -> {
            ((JavascriptExecutor) driver).executeScript(script, args);
            return null;
        };
    }

    public static Step<Void> awaitNonNilReturn(String script) {
        return (driver, await) -> {
            await.until(ExpectedConditions.jsReturnsValue(script));
            return null;
        };
    }

    public static Step<Void> awaitTrue(String script) {
        return (driver, await) -> {
            await.until(d -> {
                final var res = ((JavascriptExecutor) driver).executeScript(script);
                if (res instanceof Boolean) return res;
                return null;
            });
            return null;
        };
    }
}

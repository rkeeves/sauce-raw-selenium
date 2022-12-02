package io.github.rkeeves.formvalidation.auxiliary;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.Function;

@RequiredArgsConstructor(staticName = "of")
public final class Input {

    private final WebDriverWait await;

    private final By by;

    public void setValue(String value) {
        final var element = await.until(ExpectedConditions.elementToBeClickable(by));
        new Actions(await.until(Function.identity()))
                .scrollToElement(element)
                .moveToElement(element)
                .clickAndHold(element)
                .release(element)
                .perform();
        element.clear();
        element.sendKeys(value);
    }

    public void mustShowError() {
        await.until(ExpectClass.toContain(by, "error"));
    }
}

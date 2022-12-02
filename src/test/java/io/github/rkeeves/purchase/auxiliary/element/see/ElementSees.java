package io.github.rkeeves.purchase.auxiliary.element.see;

import io.github.rkeeves.purchase.auxiliary.selenium.ExpectClass;
import io.github.rkeeves.purchase.auxiliary.selenium.ExpectTexts;
import lombok.experimental.UtilityClass;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

@UtilityClass
public class ElementSees {

    public static ElementSee present() {
        return (bot, await, by) -> {
            await.until(ExpectedConditions.presenceOfElementLocated(by));
        };
    }

    public static ElementSee clickable() {
        return (bot, await, by) -> {
            await.until(ExpectedConditions.elementToBeClickable(by));
        };
    }

    public static ElementSee text(final String text) {
        return (bot, await, by) -> {
            await.until(ExpectedConditions.textToBe(by, text));
        };
    }

    public static ElementSee attr(final String attrName, final String attrValue) {
        return (bot, await, by) -> {
            await.until(ExpectedConditions.attributeToBe(by, attrName, attrValue));
        };
    }

    public static ElementSee cls(final String cls) {
        return (bot, await, by) -> {
            await.until(ExpectClass.toContain(by, cls));
        };
    }

    public static ElementSee count(int count) {
        return (bot, await, by) -> {
            await.until(ExpectedConditions.numberOfElementsToBe(by, count));
        };
    }

    public static ElementSee texts(List<String> texts) {
        return (bot, await, by) -> {
            await.until(ExpectTexts.toBe(by, texts));
        };
    }
}

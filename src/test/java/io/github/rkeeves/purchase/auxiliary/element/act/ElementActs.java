package io.github.rkeeves.purchase.auxiliary.element.act;

import io.github.rkeeves.purchase.auxiliary.element.see.ElementSees;
import lombok.experimental.UtilityClass;

import java.util.function.Function;

@UtilityClass
public class ElementActs {

    public static ElementAct setValue(final String value) {
        return (bot, await, by) -> {
          bot.see(by, ElementSees.clickable());
          await.until(Function.identity()).findElement(by).sendKeys(value);
        };
    }

    public static ElementAct click() {
        return (bot, await, by) -> {
            bot.see(by, ElementSees.clickable());
            await.until(Function.identity()).findElement(by).click();
        };
    }
}

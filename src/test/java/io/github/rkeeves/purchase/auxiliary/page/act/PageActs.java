package io.github.rkeeves.purchase.auxiliary.page.act;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.function.Function;

@UtilityClass
public class PageActs {

    public static PageAct open(final @NonNull String url) {
        return await -> await.until(Function.identity()).navigate().to(url);
    }
}

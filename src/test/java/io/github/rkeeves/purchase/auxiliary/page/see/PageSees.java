package io.github.rkeeves.purchase.auxiliary.page.see;

import lombok.experimental.UtilityClass;
import org.openqa.selenium.support.ui.ExpectedConditions;

@UtilityClass
public class PageSees {

    public static PageSee urlIs(String url) {
        return await -> await.until(ExpectedConditions.urlToBe(url));
    }
}

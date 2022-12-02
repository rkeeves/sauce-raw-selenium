package io.github.rkeeves.purchase.auxiliary.app.scene;

import io.github.rkeeves.formvalidation.auxiliary.CustomBy;
import io.github.rkeeves.purchase.auxiliary.bot.Bot;
import io.github.rkeeves.purchase.auxiliary.element.act.ElementActs;
import io.github.rkeeves.purchase.auxiliary.page.act.PageActs;
import io.github.rkeeves.purchase.auxiliary.page.see.PageSees;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;

@RequiredArgsConstructor(staticName = "of")
public class LoginScene {

    private static final String URL = "https://www.saucedemo.com/";

    private static final By USERNAME = CustomBy.dataTest("username");

    private static final By PASSWORD = CustomBy.dataTest("password");

    private static final By LOGIN_BUTTON = CustomBy.dataTest("login-button");

    private final Bot bot;

    public LoginScene visit() {
        bot.act(PageActs.open(URL));
        return this;
    }

    public LoginScene enterUsername(final String username) {
        bot.act(USERNAME, ElementActs.setValue(username));
        return this;
    }

    public LoginScene enterPassword(final String password) {
        bot.act(PASSWORD, ElementActs.setValue(password));
        return this;
    }

    public LoginScene attemptLogin() {
        bot.act(LOGIN_BUTTON, ElementActs.click());
        return this;
    }

    public LoginScene mustBeAt() {
        bot.see(PageSees.urlIs("https://www.saucedemo.com/"));
        return this;
    }
}

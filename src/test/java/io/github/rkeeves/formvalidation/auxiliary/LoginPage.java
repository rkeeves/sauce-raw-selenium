package io.github.rkeeves.formvalidation.auxiliary;

import lombok.NonNull;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.Consumer;

public final class LoginPage {

    private final WebDriverWait await;

    private final Page page;

    private final Input username;

    private final Input password;

    private final Errors errors;

    private final Button submit;

    private LoginPage(WebDriverWait await) {
        this.await = await;
        this.page = Page.of(await, "https://www.saucedemo.com/");
        this.username = Input.of(await, CustomBy.dataTest("username"));
        this.password = Input.of(await, CustomBy.dataTest("password"));
        this.errors = Errors.of(await, By.cssSelector(".error-message-container"));
        this.submit = Button.of(await, CustomBy.dataTest("login-button"));
    }

    public static LoginPage of(final @NonNull WebDriverWait await) {
        return new LoginPage(await);
    }

    public LoginPage page(Consumer<Page> action) {
        action.accept(page);
        return this;
    }

    public LoginPage submit(Consumer<Button> action) {
        action.accept(submit);
        return this;
    }

    public LoginPage errors(Consumer<Errors> action) {
        action.accept(errors);
        return this;
    }

    public LoginPage username(Consumer<Input> action) {
        action.accept(username);
        return this;
    }

    public LoginPage password(Consumer<Input> action) {
        action.accept(password);
        return this;
    }
}

package io.github.rkeeves.formvalidation;

import io.github.rkeeves.formvalidation.auxiliary.Button;
import io.github.rkeeves.formvalidation.auxiliary.Input;
import io.github.rkeeves.formvalidation.auxiliary.InventoryPage;
import io.github.rkeeves.formvalidation.auxiliary.LoginPage;
import io.github.rkeeves.formvalidation.auxiliary.Page;
import io.github.rkeeves.formvalidation.auxiliary.junit.WebDriverWaitExtension;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

@Slf4j
@DisplayName("Case 2 – Verify error messages for mandatory fields ")
public class FormValidationTest {

    @RegisterExtension
    static WebDriverWaitExtension webDriverWaitExtension = WebDriverWaitExtension.of(
            () -> new WebDriverWait(new ChromeDriver(), Duration.ofSeconds(4L)),
            (err, await) -> log.error("I'm essentially a 'hook', you can execute arbitrary code with me on error.")
    );

    @DisplayName("Login to Demo Sauce, and look at the footer. The scenario intentionally mixes sad and happy.")
    @Test
    void testLoginProcess(WebDriverWait await) {
        // sad :(
        LoginPage.of(await)
                .page(Page::open)
                .submit(Button::press)
                .errors(errors -> errors.mustBe(List.of("Epic sadface: Username is required")))
                .username(Input::mustShowError)
                .password(Input::mustShowError);

        // happy :)
        LoginPage.of(await)
                .username(input -> input.setValue("standard_user"))
                .password(input -> input.setValue("secret_sauce"))
                .submit(Button::press);

        InventoryPage.of(await)
                .page(Page::mustBeLoaded)
                .footer(footer -> footer.scrollTo(false))
                .footer(footer -> footer.mustContainEachOf("2022", "Terms of Service"));
    }

    @DisplayName("Intentionally fail, to cause WebDriverWaitExtension::handleTestExecutionException to fire")
    @Test
    void intentionallyFail(WebDriverWait await) {
        LoginPage.of(await)
                .page(Page::open)
                .submit(Button::press);

        InventoryPage.of(await)
                .page(Page::mustBeLoaded);
    }
}

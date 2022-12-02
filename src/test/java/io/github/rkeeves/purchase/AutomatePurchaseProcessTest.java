package io.github.rkeeves.purchase;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.rkeeves.purchase.auxiliary.app.model.Credentials;
import io.github.rkeeves.purchase.auxiliary.app.model.ProductKey;
import io.github.rkeeves.purchase.auxiliary.app.model.Products;
import io.github.rkeeves.purchase.auxiliary.app.scene.CartBadge;
import io.github.rkeeves.purchase.auxiliary.app.scene.CartScene;
import io.github.rkeeves.purchase.auxiliary.app.scene.CartStorage;
import io.github.rkeeves.purchase.auxiliary.app.scene.CheckOutStepTwoScene;
import io.github.rkeeves.purchase.auxiliary.app.scene.CheckoutFinishScene;
import io.github.rkeeves.purchase.auxiliary.app.scene.CheckoutStepOneScene;
import io.github.rkeeves.purchase.auxiliary.app.scene.InventoryScene;
import io.github.rkeeves.purchase.auxiliary.app.scene.LoginScene;
import io.github.rkeeves.purchase.auxiliary.assumptions.DataAssumptions;
import io.github.rkeeves.purchase.auxiliary.bot.Bot;
import io.github.rkeeves.purchase.auxiliary.junit.CredentialStore;
import io.github.rkeeves.purchase.auxiliary.junit.ProductStore;
import io.github.rkeeves.purchase.auxiliary.observer.ScreenshotAndPageSourceObserver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Paths;
import java.time.Duration;
import java.util.Collections;

import static java.util.function.Function.identity;

@ExtendWith(CredentialStore.class)
@ExtendWith(ProductStore.class)
@DisplayName("Case 1 – Automate Purchase Process")
public class AutomatePurchaseProcessTest {

    @DisplayName("Order a backpack and a jacket. Be aware: it lags like hell")
    @Test
    void testLengthyWorkflow(Credentials credentials, Products products) {
        final var credential = DataAssumptions.assumeCredentialByUsername(
                credentials,
                "performance_glitch_user");

        final var productsToCheckout = DataAssumptions.assumeProductsByKey(
                products,
                ProductKey.BACKPACK,
                ProductKey.JACKET);

        final var bot = Bot.of(await);

        LoginScene.of(bot)
                .visit()
                .mustBeAt()
                .enterUsername(credential.getUsername())
                .enterPassword(credential.getPassword())
                .attemptLogin();

        InventoryScene.of(bot)
                .mustBeAt()
                .addToCart(productsToCheckout.get(0))
                .addToCart(productsToCheckout.get(1));

        CartStorage.of(bot)
                .mustHaveProducts(productsToCheckout);

        CartBadge.of(bot)
                .mustShow(2)
                .followCartLink();

        CartScene.of(bot)
                .mustBeAt()
                .mustHaveProducts(productsToCheckout)
                .followCheckoutLink();

        CheckoutStepOneScene.of(bot)
                .mustBeAt()
                .enterFistname("John")
                .enterLastName("Johnson")
                .enterPostalCode("Countristan, Cityville, 69")
                .followContinue();

        CheckOutStepTwoScene.of(bot)
                .mustBeAt()
                .mustHaveProducts(productsToCheckout)
                .followFinish();

        CheckoutFinishScene.of(bot)
                .mustBeAt()
                .followLinkHome();

        InventoryScene.of(bot)
                .mustBeAt();

        CartStorage.of(bot)
                .mustHaveProducts(Collections.emptyList());
    }

    @DisplayName("Error out due to invalid credentials, so we can take a screenshot and a page source")
    @Test
    void intentionalError() {
        final var bot = Bot.withObserver(
                ScreenshotAndPageSourceObserver.of(
                        Paths.get("artifact", "img", "intentionally-fail.png"),
                        Paths.get("artifact", "page-source", "intentionally-fail.html")),
                Bot.of(await));

        LoginScene.of(bot)
                .visit()
                .mustBeAt()
                .enterUsername("stranger")
                .enterPassword("danger")
                .attemptLogin();

        InventoryScene.of(bot)
                .mustBeAt();
    }

    WebDriverWait await;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void beforeEach() {
        await = new WebDriverWait(new ChromeDriver(), Duration.ofSeconds(8L));
    }

    @AfterEach
    void afterEach() {
        if (await != null) await.until(identity()).quit();
    }
}

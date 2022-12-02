package io.github.rkeeves.richtext;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.rkeeves.richtext.auxiliary.CKEditorFactory;
import io.github.rkeeves.richtext.auxiliary.CKEditorInstance;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.function.Function.identity;

@Slf4j
@DisplayName("Case 3 - Rich Text Editor")
public class RichTextTest {

    @DisplayName("CKEditor version 4, with vanilla methods and pure js interactions")
    @ParameterizedTest
    @MethodSource
    void testCKEditor(BiFunction<WebDriverWait, String, CKEditorInstance> factory, final String expectedText) {
        final var driver = await.until(Function.identity());
        driver.navigate().to("https://onlinehtmleditor.dev/");
        await.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".spinner")));
        final var editor = factory.apply(await, "ckeditor-4-demo");
        editor
                .toggleBold()
                .insertText("Automation")
                .toggleBold()
                .insertText(" ")
                .toggleUnderline()
                .insertText("Test")
                .toggleUnderline()
                .insertText(" example")
                .dataMustBe(expectedText);
    }

    static Stream<Arguments> testCKEditor() {
        final BiFunction<WebDriverWait, String, CKEditorInstance> vanilla = CKEditorFactory::vanillaInstance;
        final BiFunction<WebDriverWait, String, CKEditorInstance> js = CKEditorFactory::jsInstance;
        return Stream.of(
                Arguments.of(Named.of("Vanilla", vanilla),
                        "<p><strong>Automation</strong> <u>Test</u> example<br></p>"),
                Arguments.of(Named.of("JS", js),
                        "<p><strong>Automation</strong> <u>Test</u> example</p>")
        );
    }

    WebDriverWait await;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void beforeEach() {
        await = new WebDriverWait(new ChromeDriver(), Duration.ofSeconds(4L));
    }

    @AfterEach
    void afterEach() {
        if (await != null) await.until(identity()).quit();
    }
}

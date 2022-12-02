package io.github.rkeeves.richtext.auxiliary;

import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Slf4j
@Value(staticConstructor = "of")
public class CKEditorInstanceJS implements CKEditorInstance {

    WebDriverWait await;

    String id;

    public CKEditorInstance toggleBold() {
        log.info("Attempting to toggle bold");
        final var script = String.format("return window.CKEDITOR.instances['%s'].commands.bold.exec()", id);
        await.until(ExpectedConditions.jsReturnsValue(script));
        return this;
    }

    public CKEditorInstance toggleUnderline() {
        log.info("Attempting to toggle underline");
        final var script = String.format("return window.CKEDITOR.instances['%s'].commands.underline.exec()", id);
        await.until(ExpectedConditions.jsReturnsValue(script));
        return this;
    }

    public String getData() {
        log.info("Attempting to get data");
        final var script = String.format("return window.CKEDITOR.instances['%s'].getData()", id);
        return await.until(d -> {
            final var js = (JavascriptExecutor) d;
            final var result = js.executeScript(script);
            if (result instanceof String) {
                return (String) result;
            }
            return null;
        });
    }

    public CKEditorInstance dataMustBe(@NonNull String expected) {
        log.info("Attempting to ensure data is {}", expected);
        final var script = String.format("return window.CKEDITOR.instances['%s'].getData()", id);
        await.until(d -> {
            final var js = (JavascriptExecutor) d;
            final var result = js.executeScript(script);
            if (result instanceof String) {
                return expected.equals(((String) result).trim().replaceAll("&nbsp;", " "));
            }
            return null;
        });
        return this;
    }

    public CKEditorInstance insertText(@NonNull String text) {
        log.info("Attempting to insert text '{}'", text);
        final var script = String.format("window.CKEDITOR.instances['%s'].insertText('%s')", id, text);
        await.until(d -> {
            ((JavascriptExecutor) d).executeScript(script);
            // if js errors out polling will continue...
            return "success";
        });
        return this;
    }
}

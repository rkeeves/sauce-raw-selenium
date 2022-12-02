package io.github.rkeeves.richtext.auxiliary;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;

// Not a real factory I know, but instead a Set of static factory methods.
@Slf4j
@UtilityClass
public class CKEditorFactory {

    public static CKEditorInstance vanillaInstance(final @NonNull WebDriverWait await, final @NonNull String id) {
        return CKEditorInstanceVanilla.of(await, id);
    }

    public static CKEditorInstance jsInstance(final @NonNull WebDriverWait await, final @NonNull String id) {
        log.info("Attempting to acquire CKEditor['{}'] from client JS", id);
        final var script = "return (window.CKEDITOR.instances['" + id + "']) ? true : false";
        await.until(d -> ((JavascriptExecutor) d).executeScript(script));
        return CKEditorInstanceJS.of(await, id);
    }
}

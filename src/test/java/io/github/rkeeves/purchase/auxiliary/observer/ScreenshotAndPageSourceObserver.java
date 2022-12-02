package io.github.rkeeves.purchase.auxiliary.observer;

import io.github.rkeeves.purchase.auxiliary.bot.ThrowableObserver;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.function.Function;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@RequiredArgsConstructor(staticName = "of")
public final class ScreenshotAndPageSourceObserver implements ThrowableObserver {

    private final Path pathToScreenshotFile;

    private final Path pathToPageSourceFile;

    @Override
    public void onFail(WebDriverWait webDriverWait, Throwable t) throws Exception {
        if (t instanceof WebDriverException) {
            final var driver = webDriverWait.until(Function.identity());
            final var screenShotTempFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.createDirectories(pathToScreenshotFile.getParent());
            Files.move(screenShotTempFile.toPath(), pathToScreenshotFile, REPLACE_EXISTING);
            final var pageSource = driver.getPageSource();
            Files.createDirectories(pathToPageSourceFile.getParent());
            Files.writeString(pathToPageSourceFile, pageSource,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE);
        }
    }
}

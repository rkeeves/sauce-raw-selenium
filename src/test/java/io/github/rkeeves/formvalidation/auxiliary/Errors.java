package io.github.rkeeves.formvalidation.auxiliary;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor(staticName = "of")
public final class Errors {

    private final WebDriverWait await;

    private final By locator;

    private static final By LOCATOR_ERROR = CustomBy.dataTest("error");

    public void mustBe(final @NonNull List<String> messages) {
        await.until(d -> {
            final var elements = d.findElements(locator);
            if (elements == null || elements.size() != messages.size()) return false;
            return elements.stream().map(WebElement::getText).collect(Collectors.toList()).equals(messages);
        });
    }
}

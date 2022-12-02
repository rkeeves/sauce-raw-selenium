package io.github.rkeeves.iframe.auxiliary.actor;

import io.github.rkeeves.iframe.auxiliary.step.Step;
import io.github.rkeeves.iframe.auxiliary.task.Task;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.Function;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DefaultActor implements Actor {

    private final WebDriverWait await;

    private final WebDriver driver;

    public static DefaultActor of(@NonNull WebDriverWait await) {
        return new DefaultActor(await, await.until(Function.identity()));
    }

    public <T> DefaultActor chain(Step<T> step) {
        step.apply(driver, await);
        return this;
    }

    public <T> DefaultActor chain(Task<T> task) {
        task.apply(this);
        return this;
    }

    public <T> T fetch(Step<T> step) {
        return step.apply(driver, await);
    }

    public <T> T fetch(Task<T> task) {
        return task.apply(this);
    }

}

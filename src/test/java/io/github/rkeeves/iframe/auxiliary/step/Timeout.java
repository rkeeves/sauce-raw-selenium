package io.github.rkeeves.iframe.auxiliary.step;

import lombok.experimental.UtilityClass;

import java.time.Duration;

@UtilityClass
public class Timeout {

    public static Step<Void> change(Duration newTimeout) {
        return (driver, await) -> {
            await.withTimeout(newTimeout);
            return null;
        };
    }
}

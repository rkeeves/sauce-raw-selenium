package io.github.rkeeves.iframe.auxiliary.step;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class WindowHandle {

    public static Step<Void> currentMustBe(@NonNull String windowHandle) {
        return (driver, await) -> {
            await.until(d -> windowHandle.equals(d.getWindowHandle()));
            return null;
        };
    }

    public static Step<String> getCurrent() {
        return (driver, await) -> driver.getWindowHandle();
    }

    public static Step<Void> switchTo(String newPageHandle) {
        return (driver, await) -> {
            driver.switchTo().window(newPageHandle);
            return null;
        };
    }

    public static Step<Void> closeCurrent() {
        return (driver, await) -> {
            driver.close();
            return null;
        };
    }

    public static Step<Void> countMustBe(int windowHandleCount) {
        return (driver, await) -> {
            await.until(d -> d.getWindowHandles().size() == windowHandleCount);
            return null;
        };
    }
}

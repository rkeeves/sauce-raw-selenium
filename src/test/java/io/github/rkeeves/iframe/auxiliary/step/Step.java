package io.github.rkeeves.iframe.auxiliary.step;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.BiFunction;

public interface Step<T> extends BiFunction<WebDriver, WebDriverWait, T> {

}

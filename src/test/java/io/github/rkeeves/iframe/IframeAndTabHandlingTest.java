package io.github.rkeeves.iframe;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.rkeeves.iframe.auxiliary.actor.DefaultActor;
import io.github.rkeeves.iframe.auxiliary.actor.NoisyActor;
import io.github.rkeeves.iframe.auxiliary.step.Title;
import io.github.rkeeves.iframe.auxiliary.step.Url;
import io.github.rkeeves.iframe.auxiliary.step.WindowHandle;
import io.github.rkeeves.iframe.auxiliary.task.GoogleAdTasks;
import io.github.rkeeves.iframe.auxiliary.task.HomeTasks;
import io.github.rkeeves.iframe.auxiliary.task.TutorialTasks;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static java.util.function.Function.identity;

@DisplayName("Case 4 - iFrame and tab handling")
public class IframeAndTabHandlingTest {

    @DisplayName("Traversing Guru99Demo home, live project, tutorial pages. Repeated to test non-determistic ads.")
    @RepeatedTest(4)
    void testHandleAndFrameJuggling() {
        final var actor = NoisyActor.of( DefaultActor.of(await));

        final var baseTab = actor.fetch(WindowHandle.getCurrent());

        actor
                .memo("I will try to open Guru99 Home page!")
                .chain("open Guru99 Home page",
                        HomeTasks.visit())
                .chain("accept gdpr",
                        HomeTasks.awaitAndAcceptGdpr());

        final var newTab = actor
                .memo("I'll now open the live project in a new tab by following an ad!")
                .fetch(HomeTasks.openLiveProjectOnNewTab());

        actor
                .memo("I'll check out the new tab(live project), then go back to the original tab(guru home)!")
                .chain("switch to newtab with live project",
                        WindowHandle.switchTo(newTab))
                .chain("Check live project title check",
                        Title.mustBe("Selenium Live Project: FREE Real Time Project for Practice"))
                .chain("close the new tab",
                        WindowHandle.closeCurrent())
                .chain("ensure there's only the original tab left",
                        WindowHandle.countMustBe(1))
                .chain("go back to original tab",
                        WindowHandle.switchTo(baseTab));

        actor
                .memo("I'll now go to the tutorials by the menu!")
                .chain("Menu > Testing > Selenium",
                        HomeTasks.openMenuItem("Testing", "Selenium"))
                .chain("Two kinds of google ad can show up at this point, but we have to close it",
                        GoogleAdTasks.awaitAndClose())
                .chain("I have to end up on the tutorial page",
                        Url.mustBe("https://www.guru99.com/selenium-tutorial.html"))
                .chain("The tutorial page also shows gdpr notice, which we must accept",
                        TutorialTasks.awaitAndAcceptGdpr())
                .chain("The 'Join our FREE Course' must exist",
                        TutorialTasks.ensureTextIsShown("Join our FREE Course"));
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

package io.github.rkeeves.iframe.auxiliary.actor;

import io.github.rkeeves.iframe.auxiliary.step.Step;
import io.github.rkeeves.iframe.auxiliary.task.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor(staticName = "of")
public class NoisyActor implements Actor {

    private final Actor defaultActor;

    public <T> NoisyActor memo(String memo) {
        log.info(memo);
        return this;
    }

    public <T> NoisyActor chain(String memo, Step<T> step) {
        log.info(memo);
        defaultActor.chain(step);
        return this;
    }

    public <T> NoisyActor chain(Step<T> step) {
        defaultActor.chain(step);
        return this;
    }

    public <T> NoisyActor chain(String memo, Task<T> task) {
        log.info(memo);
        defaultActor.chain(task);
        return this;
    }

    public <T> NoisyActor chain(Task<T> task) {
        defaultActor.chain(task);
        return this;
    }

    public <T> T fetch(String memo, Step<T> step) {
        log.info(memo);
        return defaultActor.fetch(step);
    }

    public <T> T fetch(Step<T> step) {
        return defaultActor.fetch(step);
    }

    public <T> T fetch(String memo, Task<T> task) {
        log.info(memo);
        return defaultActor.fetch(task);
    }

    public <T> T fetch(Task<T> task) {
        return defaultActor.fetch(task);
    }
}

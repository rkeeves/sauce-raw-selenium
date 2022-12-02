package io.github.rkeeves.iframe.auxiliary.actor;

import io.github.rkeeves.iframe.auxiliary.step.Step;
import io.github.rkeeves.iframe.auxiliary.task.Task;

public interface Actor {

    <T> Actor chain(Step<T> step);

    <T> Actor chain(Task<T> task);

    <T> T fetch(Step<T> step);

    <T> T fetch(Task<T> task);
}

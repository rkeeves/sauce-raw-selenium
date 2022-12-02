package io.github.rkeeves.iframe.auxiliary.task;

import io.github.rkeeves.iframe.auxiliary.actor.DefaultActor;

import java.util.function.Function;

public interface Task<T> extends Function<DefaultActor, T>  {
}

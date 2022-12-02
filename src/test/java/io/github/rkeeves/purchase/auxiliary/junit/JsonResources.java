package io.github.rkeeves.purchase.auxiliary.junit;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@UtilityClass
public class JsonResources {

    public static String readRaw(final @NonNull String fname) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        final var stream = classloader.getResourceAsStream(fname);
        if (stream == null) throw new IOException("Returned stream for resource " + fname + " was null");
        try(final var bufferedReader = new BufferedReader(new InputStreamReader(stream))) {
            return bufferedReader.lines().collect(Collectors.joining());
        }
    }
}

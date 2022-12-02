package io.github.rkeeves.restdetour;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.github.rkeeves.restdetour.auxiliary.model.UserDto;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;

@Slf4j
@DisplayName("Case 5 – REST API testing")
public class RestDetourTest {

    private static final String BASE = "https://jsonplaceholder.typicode.com";

    @DisplayName("GET, parse, log, and assert")
    @Test
    void test() {
        final var client = new OkHttpClient();
        // Mapping the whole response is inefficient,
        // because we need only username and password.
        // But at least this shows an example of getting around type erasure in generics...
        final List<UserDto> users = get(client, "/users", new TypeToken<ArrayList<UserDto>>(){}.getType());
        users.stream()
                .map(u -> String.format("%s | %s", u.getUsername(), u.getEmail()))
                .forEach(log::info);
        assertThat("There must be at least 1 user in the list",
                users.size(), greaterThan(0));
        final var user = users.get(0);
        assertThat(
                "The first email address must contain @, aka it can be '@@@@'",
                user.getEmail(), containsString("@"));
    }

    public static <T> T get(OkHttpClient client, String path, Type type) {
        final var request = new Request.Builder()
                .url(BASE + path)
                .build();
        try (Response response = client.newCall(request).execute()) {
            final var raw = response.body().string();
            return new Gson().fromJson(raw, type);
        } catch (IOException e) {
            throw new RuntimeException("OKHttp and json parsing failed", e);
        }
    }
}

package io.github.rkeeves.purchase.auxiliary.app.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
public final class Credentials {

    @SerializedName("credentials")
    private List<Credential> credentials;

    public Optional<Credential> findByUserName(final String username) {
        return credentials.stream()
                .filter(credential -> username.equals(credential.getUsername()))
                .findFirst();
    }
}

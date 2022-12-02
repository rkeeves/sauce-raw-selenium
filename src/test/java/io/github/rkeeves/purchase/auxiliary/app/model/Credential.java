package io.github.rkeeves.purchase.auxiliary.app.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public final class Credential {

    @SerializedName("username")
    String username;

    @SerializedName("password")
    String password;
}

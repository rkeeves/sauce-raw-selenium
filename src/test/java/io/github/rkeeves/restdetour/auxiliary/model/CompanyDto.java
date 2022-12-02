package io.github.rkeeves.restdetour.auxiliary.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class CompanyDto {

    @SerializedName("name")
    String name;

    @SerializedName("catchPhrase")
    String catchPhrase;

    @SerializedName("bs")
    String bs;
}

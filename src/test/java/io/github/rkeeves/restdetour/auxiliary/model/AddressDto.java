package io.github.rkeeves.restdetour.auxiliary.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class AddressDto {

    @SerializedName("street")
    String street;

    @SerializedName("suite")
    String suite;

    @SerializedName("city")
    String city;

    @SerializedName("zipcode")
    String zipcode;

    @SerializedName("geo")
    GeoDto geo;
}

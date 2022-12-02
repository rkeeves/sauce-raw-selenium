package io.github.rkeeves.restdetour.auxiliary.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class GeoDto {

    @SerializedName("lat")
    String latitude;

    @SerializedName("lng")
    String longitude;
}

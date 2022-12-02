package io.github.rkeeves.restdetour.auxiliary.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class UserDto {

    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    @SerializedName("username")
    private String username;

    @SerializedName("email")
    private String email;

    @SerializedName("address")
    private AddressDto address;

    @SerializedName("phone")
    private String phone;

    @SerializedName("website")
    private String website;

    @SerializedName("company")
    private CompanyDto company;
}

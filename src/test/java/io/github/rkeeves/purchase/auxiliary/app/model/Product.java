package io.github.rkeeves.purchase.auxiliary.app.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public final class Product {

    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    @SerializedName("desc")
    private String description;

    @SerializedName("price")
    private Double price;

    @SerializedName("image_url")
    private String imageUrl;
}

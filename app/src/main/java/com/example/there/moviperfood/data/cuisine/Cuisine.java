package com.example.there.moviperfood.data.cuisine;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

public @Data class Cuisine {
    @SerializedName("cuisine_id") private int cuisineId;
    @SerializedName("cuisine_name") private String cuisineName;
}

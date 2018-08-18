package com.example.there.moviperfood.data.food.review;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

public @Data class ReviewUser {
    @SerializedName("profile_url")
    private String profileUrl;

    @SerializedName("profile_image")
    private String profileImage;

    @SerializedName("profileDeeplink")
    private String profileDeeplink;

    private String name;

    @SerializedName("foodie_level")
    private String foodieLevel;

    @SerializedName("foodie_color")
    private String foodieColor;

    @SerializedName("foodie_leverl_num")
    private String foodieLevelNum;
}

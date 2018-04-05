package com.example.there.moviperfood.data.restaurant;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

public @Data class RestaurantUserRating {
    @SerializedName("rating_text")
    private String ratingText;

    @SerializedName("rating_color")
    private String ratingColor;

    private String votes;

    @SerializedName("aggregate_rating")
    private String aggregateRating;
}

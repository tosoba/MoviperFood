package com.example.there.moviperfood.data.restaurant;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

public @Data class RestaurantsResponse {
    @SerializedName("results_start")
    private String resultsStart;

    @SerializedName("results_found")
    private String resultsFound;

    private RestaurantResponse[] restaurants;

    @SerializedName("results_shown")
    private String resultsShown;
}

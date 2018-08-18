package com.example.there.moviperfood.data.food.review;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

public @Data class ReviewsResponse {
    @SerializedName("reviews_shown")
    private String reviewsShown;

    @SerializedName("reviews_start")
    private String reviewsStart;

    @SerializedName("reviews_count")
    private String reviewsCount;

    @SerializedName("user_reviews")
    private ReviewResponse[] userReviews;
}

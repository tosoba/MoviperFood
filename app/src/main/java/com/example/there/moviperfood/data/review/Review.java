package com.example.there.moviperfood.data.review;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

public @Data class Review {
    private String timestamp;

    @SerializedName("rating_tex")
    private String ratingText;

    private String id;

    @SerializedName("comments_count")
    private String commentsCount;

    @SerializedName("rating_color")
    private String ratingColor;

    private String likes;

    private String rating;

    private ReviewUser user;

    @SerializedName("review_text")
    private String reviewText;

    @SerializedName("review_time_friendly")
    private String reviewTimeFriendly;
}

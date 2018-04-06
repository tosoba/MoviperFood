package com.example.there.moviperfood.data.review;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

public @Data class Review implements Parcelable {
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

    private Review(Parcel in) {
        timestamp = in.readString();
        ratingText = in.readString();
        id = in.readString();
        commentsCount = in.readString();
        ratingColor = in.readString();
        likes = in.readString();
        rating = in.readString();
        reviewText = in.readString();
        reviewTimeFriendly = in.readString();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(timestamp);
        dest.writeString(ratingText);
        dest.writeString(id);
        dest.writeString(commentsCount);
        dest.writeString(ratingColor);
        dest.writeString(likes);
        dest.writeString(rating);
        dest.writeString(reviewText);
        dest.writeString(reviewTimeFriendly);
    }
}

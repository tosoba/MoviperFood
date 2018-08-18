package com.example.there.moviperfood.data.food.restaurant;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

public @Data class RestaurantUserRating implements Parcelable{
    @SerializedName("rating_text")
    private String ratingText;

    @SerializedName("rating_color")
    private String ratingColor;

    private String votes;

    @SerializedName("aggregate_rating")
    private String aggregateRating;

    private RestaurantUserRating(Parcel in) {
        ratingText = in.readString();
        ratingColor = in.readString();
        votes = in.readString();
        aggregateRating = in.readString();
    }

    public static final Creator<RestaurantUserRating> CREATOR = new Creator<RestaurantUserRating>() {
        @Override
        public RestaurantUserRating createFromParcel(Parcel in) {
            return new RestaurantUserRating(in);
        }

        @Override
        public RestaurantUserRating[] newArray(int size) {
            return new RestaurantUserRating[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ratingText);
        dest.writeString(ratingColor);
        dest.writeString(votes);
        dest.writeString(aggregateRating);
    }
}

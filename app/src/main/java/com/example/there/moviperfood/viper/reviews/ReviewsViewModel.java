package com.example.there.moviperfood.viper.reviews;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.there.moviperfood.data.food.restaurant.Restaurant;
import com.example.there.moviperfood.data.food.review.Review;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReviewsViewModel implements Parcelable {
    private Restaurant restaurant;
    private ObservableArrayList<Review> reviews;
    private ObservableField<Boolean> isLoading;

    private ReviewsViewModel(Parcel in) {
        restaurant = in.readParcelable(Restaurant.class.getClassLoader());
        reviews.addAll(Arrays.asList(in.createTypedArray(Review.CREATOR)));
    }

    public static final Creator<ReviewsViewModel> CREATOR = new Creator<ReviewsViewModel>() {
        @Override
        public ReviewsViewModel createFromParcel(Parcel in) {
            return new ReviewsViewModel(in);
        }

        @Override
        public ReviewsViewModel[] newArray(int size) {
            return new ReviewsViewModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(restaurant, flags);
        dest.writeTypedArray(reviews.toArray(new Review[reviews.size()]), flags);
    }
}

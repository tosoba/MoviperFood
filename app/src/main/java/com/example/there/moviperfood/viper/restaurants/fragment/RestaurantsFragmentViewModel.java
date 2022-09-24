package com.example.there.moviperfood.viper.restaurants.fragment;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.there.moviperfood.data.food.restaurant.Restaurant;

import java.util.Arrays;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RestaurantsFragmentViewModel implements Parcelable {
    private ObservableList<Restaurant> restaurants = new ObservableArrayList<>();

    private RestaurantsFragmentViewModel(Parcel in) {
        restaurants.addAll(Arrays.asList(in.createTypedArray(Restaurant.CREATOR)));
    }

    public static final Creator<RestaurantsFragmentViewModel> CREATOR = new Creator<RestaurantsFragmentViewModel>() {
        @Override
        public RestaurantsFragmentViewModel createFromParcel(Parcel in) {
            return new RestaurantsFragmentViewModel(in);
        }

        @Override
        public RestaurantsFragmentViewModel[] newArray(int size) {
            return new RestaurantsFragmentViewModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedArray(restaurants.toArray(new Restaurant[restaurants.size()]), flags);
    }
}

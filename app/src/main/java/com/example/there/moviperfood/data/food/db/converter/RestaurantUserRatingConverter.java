package com.example.there.moviperfood.data.food.db.converter;

import android.arch.persistence.room.TypeConverter;

import com.example.there.moviperfood.data.food.restaurant.RestaurantUserRating;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class RestaurantUserRatingConverter {
    @TypeConverter
    public RestaurantUserRating fromJson(String json) {
        Type RestaurantUserRatingType = new TypeToken<RestaurantUserRating>() {}.getType();
        return new Gson().fromJson(json, RestaurantUserRatingType);
    }

    @TypeConverter
    public String toJson(RestaurantUserRating rating) {
        Gson gson = new Gson();
        return gson.toJson(rating);
    }
}

package com.example.there.moviperfood.data.food.db.converter;

import android.arch.persistence.room.TypeConverter;

import com.example.there.moviperfood.data.food.restaurant.RestaurantLocation;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class RestaurantLocationConverter {
    @TypeConverter
    public RestaurantLocation fromJson(String json) {
        Type restaurantLocationType = new TypeToken<RestaurantLocation>() {}.getType();
        return new Gson().fromJson(json, restaurantLocationType);
    }

    @TypeConverter
    public String toJson(RestaurantLocation location) {
        Gson gson = new Gson();
        return gson.toJson(location);
    }
}

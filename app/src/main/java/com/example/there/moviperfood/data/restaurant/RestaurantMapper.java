package com.example.there.moviperfood.data.restaurant;

public class RestaurantMapper {
    public Restaurant toRestaurant(RestaurantResponse response) {
        return response.getRestaurant();
    }
}

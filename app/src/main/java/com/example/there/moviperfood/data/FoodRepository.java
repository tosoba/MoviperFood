package com.example.there.moviperfood.data;

import com.annimon.stream.Stream;
import com.example.there.moviperfood.data.cuisine.Cuisine;
import com.example.there.moviperfood.data.cuisine.CuisineMapper;
import com.example.there.moviperfood.data.restaurant.Restaurant;
import com.example.there.moviperfood.data.restaurant.RestaurantMapper;
import com.example.there.moviperfood.domain.BaseFoodRepository;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import io.reactivex.Observable;

public class FoodRepository implements BaseFoodRepository {
    private CuisineMapper cuisineMapper;
    private RestaurantMapper restaurantMapper;
    private FoodApiService service;

    public FoodRepository(CuisineMapper cuisineMapper, RestaurantMapper restaurantMapper, FoodApiService service) {
        this.cuisineMapper = cuisineMapper;
        this.restaurantMapper = restaurantMapper;
        this.service = service;
    }

    @Override
    public Observable<List<Cuisine>> loadCuisines(LatLng latLng) {
        return service.loadCuisines(String.valueOf(latLng.latitude), String.valueOf(latLng.longitude))
                .map(response -> Stream.of(response.getCuisines()).map(cuisineMapper::toCuisine).toList());
    }

    @Override
    public Observable<List<Restaurant>> loadRestaurants(LatLng latLng, Cuisine cuisine) {
        String[] cuisines = {String.valueOf(cuisine.getCuisineId())};
        return service.loadRestaurants(String.valueOf(latLng.latitude), String.valueOf(latLng.longitude), cuisines)
                .map(restaurantsResponse -> Stream.of(restaurantsResponse.getRestaurants()).map(restaurantMapper::toRestaurant).toList());
    }
}

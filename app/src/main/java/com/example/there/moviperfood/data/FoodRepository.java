package com.example.there.moviperfood.data;

import com.annimon.stream.Stream;
import com.example.there.moviperfood.data.cuisine.Cuisine;
import com.example.there.moviperfood.data.cuisine.CuisineResponse;
import com.example.there.moviperfood.data.restaurant.Restaurant;
import com.example.there.moviperfood.data.restaurant.RestaurantResponse;
import com.example.there.moviperfood.data.review.Review;
import com.example.there.moviperfood.data.review.ReviewResponse;
import com.example.there.moviperfood.domain.BaseFoodRepository;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import io.reactivex.Observable;

public class FoodRepository implements BaseFoodRepository {
    private FoodApiService service;

    public FoodRepository(FoodApiService service) {
        this.service = service;
    }

    @Override
    public Observable<List<Cuisine>> loadCuisines(LatLng latLng) {
        return service.loadCuisines(String.valueOf(latLng.latitude), String.valueOf(latLng.longitude))
                .map(response -> Stream.of(response.getCuisines()).map(CuisineResponse::getCuisine).toList());
    }

    @Override
    public Observable<List<Restaurant>> loadRestaurants(LatLng latLng, Cuisine cuisine) {
        String[] cuisines = {String.valueOf(cuisine.getCuisineId())};
        return service.loadRestaurants(String.valueOf(latLng.latitude), String.valueOf(latLng.longitude), cuisines)
                .map(restaurantsResponse -> Stream.of(restaurantsResponse.getRestaurants()).map(RestaurantResponse::getRestaurant).toList());
    }

    @Override
    public Observable<List<Review>> loadReviews(Restaurant restaurant) {
        return service.loadReviews(restaurant.getId())
                .map(reviewsResponse -> Stream.of(reviewsResponse.getUserReviews()).map(ReviewResponse::getReview).toList());
    }
}

package com.example.there.moviperfood.domain;

import com.example.there.moviperfood.data.food.cuisine.Cuisine;
import com.example.there.moviperfood.data.food.restaurant.Restaurant;
import com.example.there.moviperfood.data.food.review.Review;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;

public interface BaseFoodRepository {
    Observable<List<Cuisine>> loadCuisines(LatLng latLng);

    Observable<List<Restaurant>> loadRestaurants(LatLng latLng, Cuisine cuisine);

    Observable<List<Review>> loadReviews(Restaurant restaurant);

    Completable insertRestaurant(Restaurant restaurant);

    Flowable<List<Restaurant>> getSavedRestaurants();
}

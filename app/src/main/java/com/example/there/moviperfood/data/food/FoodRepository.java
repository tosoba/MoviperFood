package com.example.there.moviperfood.data.food;

import com.annimon.stream.Stream;
import com.example.there.moviperfood.data.food.api.FoodApiService;
import com.example.there.moviperfood.data.food.cuisine.Cuisine;
import com.example.there.moviperfood.data.food.cuisine.CuisineResponse;
import com.example.there.moviperfood.data.food.db.RestaurantDao;
import com.example.there.moviperfood.data.food.db.RestaurantsDb;
import com.example.there.moviperfood.data.food.restaurant.Restaurant;
import com.example.there.moviperfood.data.food.restaurant.RestaurantResponse;
import com.example.there.moviperfood.data.food.review.Review;
import com.example.there.moviperfood.data.food.review.ReviewResponse;
import com.example.there.moviperfood.domain.BaseFoodRepository;
import com.google.android.gms.maps.model.LatLng;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;

public class FoodRepository implements BaseFoodRepository {
    private FoodApiService service;

    private RestaurantDao restaurantDao;

    @Inject
    public FoodRepository(FoodApiService service, RestaurantsDb restaurantsDb) {
        this.service = service;
        restaurantDao = restaurantsDb.restaurantDao();
    }

    @Override
    public Observable<List<Cuisine>> loadCuisines(LatLng latLng) {
        return service.loadCuisines(String.valueOf(latLng.latitude), String.valueOf(latLng.longitude))
                .map(response -> {
                    List<CuisineResponse> cuisines = response.getCuisines();
                    if (cuisines == null) cuisines = Collections.emptyList();
                    return Stream.of(cuisines).map(CuisineResponse::getCuisine).toList();
                });
    }

    @Override
    public Observable<List<Restaurant>> loadRestaurants(LatLng latLng, Cuisine cuisine) {
        String[] cuisines = {String.valueOf(cuisine.getCuisineId())};
        return service.loadRestaurants(String.valueOf(latLng.latitude), String.valueOf(latLng.longitude), cuisines)
                .map(restaurantsResponse -> {
                    RestaurantResponse[] restaurants = restaurantsResponse.getRestaurants();
                    if (restaurants == null) restaurants = new RestaurantResponse[]{};
                    return Stream.of(restaurants).map(RestaurantResponse::getRestaurant).toList();
                });
    }

    @Override
    public Observable<List<Review>> loadReviews(Restaurant restaurant) {
        return service.loadReviews(restaurant.getId())
                .map(reviewsResponse -> {
                    ReviewResponse[] userReviews = reviewsResponse.getUserReviews();
                    if (userReviews == null) userReviews = new ReviewResponse[]{};
                    return Stream.of(userReviews).map(ReviewResponse::getReview).toList();
                });
    }

    @Override
    public Completable insertRestaurant(Restaurant restaurant) {
        return Completable.fromAction(() -> restaurantDao.insert(restaurant));
    }

    @Override
    public Flowable<List<Restaurant>> getSavedRestaurants() {
        return restaurantDao.getRestaurants();
    }
}

package com.example.there.moviperfood.data.food.api;

import com.example.there.moviperfood.data.food.cuisine.CuisinesResponse;
import com.example.there.moviperfood.data.food.restaurant.RestaurantsResponse;
import com.example.there.moviperfood.data.food.review.ReviewsResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface FoodApiService {
    @GET("cuisines")
    @Headers({"Accept: application/json", "user-key: " + Api.KEY})
    Observable<CuisinesResponse> loadCuisines(@Query("lat") String lat, @Query("lon") String lon);

    @GET("search")
    @Headers({"Accept: application/json", "user-key: " + Api.KEY})
    Observable<RestaurantsResponse> loadRestaurants(@Query("lat") String lat,
                                                    @Query("lon") String lon,
                                                    @Query("cuisines") String[] cuisines,
                                                    @Query("sort") String sort,
                                                    @Query("order") String order);

    @GET("search")
    @Headers({"Accept: application/json", "user-key: " + Api.KEY})
    default Observable<RestaurantsResponse> loadRestaurants(@Query("lat") String lat,
                                                            @Query("lon") String lon,
                                                            @Query("cuisines") String[] cuisines) {
        return loadRestaurants(lat, lon, cuisines, "real_distance", "asc");
    }

    @GET("reviews")
    @Headers({"Accept: application/json", "user-key: " + Api.KEY})
    Observable<ReviewsResponse> loadReviews(@Query("res_id") String restaurantId);
}

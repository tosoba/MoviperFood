package com.example.there.moviperfood.data;

import com.example.there.moviperfood.data.cuisine.CuisinesResponse;
import com.example.there.moviperfood.data.restaurant.RestaurantsResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface FoodApiService {
    @GET("cuisines")
    @Headers({"Accept: application/json", "user-key: ebe8cf93fb5e46a2c09bed310f932eb2"})
    Observable<CuisinesResponse> loadCuisines(@Query("lat") String lat, @Query("lon") String lon);

    @GET("search")
    @Headers({"Accept: application/json", "user-key: ebe8cf93fb5e46a2c09bed310f932eb2"})
    Observable<RestaurantsResponse> loadRestaurants(@Query("lat") String lat,
                                                    @Query("lon") String lon,
                                                    @Query("cuisines") String[] cuisines,
                                                    @Query("sort") String sort,
                                                    @Query("order") String order);

    @GET("search")
    @Headers({"Accept: application/json", "user-key: ebe8cf93fb5e46a2c09bed310f932eb2"})
    default Observable<RestaurantsResponse> loadRestaurants(@Query("lat") String lat,
                                                    @Query("lon") String lon,
                                                    @Query("cuisines") String[] cuisines) {
        return loadRestaurants(lat, lon, cuisines, "real_distance", "desc");
    }
}

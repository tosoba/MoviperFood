package com.example.there.moviperfood.viper.search;

import com.example.there.moviperfood.data.FoodApiService;
import com.example.there.moviperfood.data.FoodRepository;
import com.example.there.moviperfood.data.cuisine.CuisineMapper;
import com.example.there.moviperfood.data.restaurant.RestaurantMapper;
import com.example.there.moviperfood.domain.BaseFoodRepository;
import com.google.gson.annotations.SerializedName;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lombok.val;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class SearchInteractorModule {

    @Provides
    @Singleton
    Retrofit foodRetrofit() {
        val baseUrl = "https://developers.zomato.com/api/v2.1/";
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
    }

    @Provides
    @Singleton
    FoodApiService foodApiService(Retrofit foodRetrofit) {
        return foodRetrofit.create(FoodApiService.class);
    }

    @Provides
    @Singleton
    CuisineMapper cuisineMapper() {
        return new CuisineMapper();
    }

    @Provides
    @Singleton
    RestaurantMapper restaurantMapper() {
        return new RestaurantMapper();
    }

    @Provides
    @Singleton
    BaseFoodRepository foodRepository(CuisineMapper cuisineMapper, RestaurantMapper restaurantMapper, FoodApiService service) {
        return new FoodRepository(cuisineMapper, restaurantMapper, service);
    }
}

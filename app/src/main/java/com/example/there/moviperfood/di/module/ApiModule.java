package com.example.there.moviperfood.di.module;

import com.example.there.moviperfood.data.food.api.FoodApiService;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import dagger.Module;
import dagger.Provides;
import lombok.val;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public abstract class ApiModule {

    @Provides
    static Retrofit foodRetrofit() {
        val baseUrl = "https://developers.zomato.com/api/v2.1/";
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
    }

    @Provides
    static FoodApiService foodApiService(Retrofit foodRetrofit) {
        return foodRetrofit.create(FoodApiService.class);
    }
}

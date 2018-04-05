package com.example.there.moviperfood.di;

import com.example.there.moviperfood.data.FoodApiService;
import com.example.there.moviperfood.data.FoodRepository;
import com.example.there.moviperfood.domain.BaseFoodRepository;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lombok.val;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class FoodApiModule {

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
    BaseFoodRepository foodRepository(FoodApiService service) {
        return new FoodRepository(service);
    }
}

package com.example.there.moviperfood.di;

import android.app.Application;
import android.content.Context;

import com.example.there.moviperfood.data.FoodApiService;
import com.example.there.moviperfood.data.FoodRepository;
import com.example.there.moviperfood.data.cuisine.CuisineMapper;
import com.example.there.moviperfood.domain.BaseFoodRepository;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {
    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application application() {
        return application;
    }

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application.getApplicationContext();
    }
}

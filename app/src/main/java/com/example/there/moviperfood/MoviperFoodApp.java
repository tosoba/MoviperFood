package com.example.there.moviperfood;

import android.app.Application;

import com.example.there.moviperfood.di.AppComponent;
import com.example.there.moviperfood.di.DaggerAppComponent;
import com.google.android.libraries.places.api.Places;

public class MoviperFoodApp extends Application {

    private AppComponent component;

    public AppComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        Places.initialize(this, getString(R.string.api_key));

        component = DaggerAppComponent.builder()
                .application(MoviperFoodApp.instance)
                .build();
        component.inject(this);
    }

    private static MoviperFoodApp instance;

    public static MoviperFoodApp getInstance() {
        return instance;
    }
}

package com.example.there.moviperfood;

import android.app.Application;

import com.example.there.moviperfood.di.AppComponent;
import com.example.there.moviperfood.di.DaggerAppComponent;

public class MoviperFoodApp extends Application {

    private AppComponent component;

    public AppComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

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

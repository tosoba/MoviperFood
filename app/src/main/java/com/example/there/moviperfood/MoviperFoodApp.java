package com.example.there.moviperfood;

import android.app.Application;

public class MoviperFoodApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static MoviperFoodApp instance;
}

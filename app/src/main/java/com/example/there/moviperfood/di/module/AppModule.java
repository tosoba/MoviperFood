package com.example.there.moviperfood.di.module;

import android.content.Context;

import com.example.there.moviperfood.MoviperFoodApp;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class AppModule {

    @Binds
    public abstract Context provideContext(MoviperFoodApp application);
}

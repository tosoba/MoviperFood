package com.example.there.moviperfood.di.module;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.there.moviperfood.data.food.db.RestaurantsDb;
import com.example.there.moviperfood.data.place.PlacesDb;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class CacheModule {

    @Provides
    @Singleton
    static PlacesDb placesDb(Context context) {
        return Room.databaseBuilder(
                context,
                PlacesDb.class,
                "places.db"
        ).build();
    }

    @Provides
    @Singleton
    static RestaurantsDb restaurantsDb(Context context) {
        return Room.databaseBuilder(
                context,
                RestaurantsDb.class,
                "restaurants.db"
        ).build();
    }
}

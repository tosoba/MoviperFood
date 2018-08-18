package com.example.there.moviperfood.di.module;

import com.example.there.moviperfood.data.food.FoodRepository;
import com.example.there.moviperfood.data.place.PlaceRepository;
import com.example.there.moviperfood.domain.BaseFoodRepository;
import com.example.there.moviperfood.domain.BasePlaceRepository;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class DataModule {

    @Binds
    abstract BaseFoodRepository foodRepository(FoodRepository repository);

    @Binds
    abstract BasePlaceRepository placeRepository(PlaceRepository repository);
}

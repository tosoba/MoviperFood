package com.example.there.moviperfood.viper.common;

import com.example.there.moviperfood.MoviperFoodApp;
import com.example.there.moviperfood.di.DaggerAppComponent;
import com.example.there.moviperfood.domain.BaseFoodRepository;
import com.example.there.moviperfood.domain.BasePlaceRepository;
import com.mateuszkoslacz.moviper.base.interactor.BaseRxInteractor;

import javax.inject.Inject;

public abstract class BaseApiInteractor extends BaseRxInteractor {

    @Inject
    BaseFoodRepository foodRepository;

    @Inject
    BasePlaceRepository placeRepository;

    protected BaseFoodRepository getFoodRepository() {
        return foodRepository;
    }

    protected BasePlaceRepository getPlaceRepository() {
        return placeRepository;
    }

    public BaseApiInteractor() {
        DaggerAppComponent.builder()
                .application(MoviperFoodApp.instance)
                .build()
                .inject(this);
    }
}

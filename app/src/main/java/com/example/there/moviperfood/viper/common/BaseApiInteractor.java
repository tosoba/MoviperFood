package com.example.there.moviperfood.viper.common;

import com.example.there.moviperfood.MoviperFoodApp;
import com.example.there.moviperfood.domain.BaseFoodRepository;
import com.example.there.moviperfood.domain.BasePlaceRepository;
import com.mateuszkoslacz.moviper.base.interactor.BaseRxInteractor;

public abstract class BaseApiInteractor extends BaseRxInteractor {

    private BaseFoodRepository foodRepository;

    private BasePlaceRepository placeRepository;

    protected BaseFoodRepository getFoodRepository() {
        return foodRepository;
    }

    protected BasePlaceRepository getPlaceRepository() {
        return placeRepository;
    }

    public BaseApiInteractor() {
        foodRepository = MoviperFoodApp.getInstance().getComponent().baseFoodRepository();
        placeRepository = MoviperFoodApp.getInstance().getComponent().basePlaceRepository();
    }
}

package com.example.there.moviperfood.viper.common;

import com.example.there.moviperfood.di.DaggerFoodApiComponent;
import com.example.there.moviperfood.di.FoodApiModule;
import com.example.there.moviperfood.domain.BaseFoodRepository;
import com.mateuszkoslacz.moviper.base.interactor.BaseRxInteractor;

import javax.inject.Inject;

import lombok.val;

public abstract class BaseApiInteractor extends BaseRxInteractor {
    @Inject
    BaseFoodRepository foodRepository;

    protected BaseFoodRepository getFoodRepository() {
        return foodRepository;
    }

    public BaseApiInteractor() {
        super();
        val component = DaggerFoodApiComponent.builder()
                .searchInteractorModule(new FoodApiModule())
                .build();
        component.inject(this);
    }
}

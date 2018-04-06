package com.example.there.moviperfood.di;

import com.example.there.moviperfood.viper.common.BaseApiInteractor;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {FoodApiModule.class})
public interface FoodApiComponent {
    @Component.Builder
    interface Builder {
        FoodApiComponent.Builder searchInteractorModule(FoodApiModule foodApiModule);

        FoodApiComponent build();
    }

    void inject(BaseApiInteractor interactor);
}

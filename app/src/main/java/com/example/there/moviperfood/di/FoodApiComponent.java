package com.example.there.moviperfood.di;

import com.example.there.moviperfood.viper.restaurants.RestaurantsInteractor;
import com.example.there.moviperfood.viper.search.SearchInteractor;

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

    void inject(SearchInteractor interactor);

    void inject(RestaurantsInteractor interactor);
}

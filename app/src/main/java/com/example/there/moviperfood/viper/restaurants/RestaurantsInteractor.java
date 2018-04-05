package com.example.there.moviperfood.viper.restaurants;

import com.example.there.moviperfood.data.cuisine.Cuisine;
import com.example.there.moviperfood.data.restaurant.Restaurant;
import com.example.there.moviperfood.di.DaggerFoodApiComponent;
import com.example.there.moviperfood.di.FoodApiModule;
import com.example.there.moviperfood.domain.BaseFoodRepository;
import com.google.android.gms.maps.model.LatLng;
import com.mateuszkoslacz.moviper.base.interactor.BaseRxInteractor;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import lombok.val;

public class RestaurantsInteractor
        extends BaseRxInteractor
        implements RestaurantsContract.Interactor {

    @Inject
    BaseFoodRepository foodRepository;

    public RestaurantsInteractor() {
        super();
        val component = DaggerFoodApiComponent.builder()
                .searchInteractorModule(new FoodApiModule())
                .build();
        component.inject(this);
    }

    @Override
    public Observable<List<Restaurant>> loadRestaurants(LatLng latLng, Cuisine cuisine) {
        return foodRepository.loadRestaurants(latLng, cuisine);
    }
}

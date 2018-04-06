package com.example.there.moviperfood.viper.search;

import com.example.there.moviperfood.data.cuisine.Cuisine;
import com.example.there.moviperfood.di.DaggerFoodApiComponent;
import com.example.there.moviperfood.di.FoodApiModule;
import com.example.there.moviperfood.domain.BaseFoodRepository;
import com.google.android.gms.maps.model.LatLng;
import com.mateuszkoslacz.moviper.base.interactor.BaseRxInteractor;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import lombok.val;

public class SearchInteractor
        extends BaseRxInteractor
        implements SearchContract.Interactor {

    @Inject
    BaseFoodRepository foodRepository;

    SearchInteractor() {
        super();
        val component = DaggerFoodApiComponent.builder()
                .searchInteractorModule(new FoodApiModule())
                .build();
        component.inject(this);
    }

    @Override
    public Observable<List<Cuisine>> loadCuisines(LatLng latLng) {
        return foodRepository.loadCuisines(latLng);
    }
}

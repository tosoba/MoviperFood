package com.example.there.moviperfood.viper.search;

import com.example.there.moviperfood.data.cuisine.Cuisine;
import com.example.there.moviperfood.di.DaggerFoodApiComponent;
import com.example.there.moviperfood.di.FoodApiModule;
import com.example.there.moviperfood.domain.BaseFoodRepository;
import com.example.there.moviperfood.viper.common.BaseApiInteractor;
import com.google.android.gms.maps.model.LatLng;
import com.mateuszkoslacz.moviper.base.interactor.BaseRxInteractor;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import lombok.val;

public class SearchInteractor
        extends BaseApiInteractor
        implements SearchContract.Interactor {

    @Override
    public Observable<List<Cuisine>> loadCuisines(LatLng latLng) {
        return getFoodRepository().loadCuisines(latLng);
    }
}

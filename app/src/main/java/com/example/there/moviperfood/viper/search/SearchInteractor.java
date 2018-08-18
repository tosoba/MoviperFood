package com.example.there.moviperfood.viper.search;

import com.example.there.moviperfood.data.food.cuisine.Cuisine;
import com.example.there.moviperfood.viper.common.BaseApiInteractor;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import io.reactivex.Observable;

public class SearchInteractor
        extends BaseApiInteractor
        implements SearchContract.Interactor {

    @Override
    public Observable<List<Cuisine>> loadCuisines(LatLng latLng) {
        return getFoodRepository().loadCuisines(latLng);
    }
}

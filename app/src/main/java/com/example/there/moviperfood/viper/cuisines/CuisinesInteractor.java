package com.example.there.moviperfood.viper.cuisines;

import com.example.there.moviperfood.data.food.cuisine.Cuisine;
import com.example.there.moviperfood.viper.common.BaseApiInteractor;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import io.reactivex.Observable;

public class CuisinesInteractor
        extends BaseApiInteractor
        implements CuisinesContract.Interactor {

    @Override
    public Observable<List<Cuisine>> loadCuisines(LatLng latLng) {
        return getFoodRepository().loadCuisines(latLng);
    }
}

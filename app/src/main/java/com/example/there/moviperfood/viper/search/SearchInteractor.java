package com.example.there.moviperfood.viper.search;

import com.example.there.moviperfood.data.cuisine.Cuisine;
import com.example.there.moviperfood.data.restaurant.Restaurant;
import com.example.there.moviperfood.domain.BaseFoodRepository;
import com.google.android.gms.maps.model.LatLng;
import com.mateuszkoslacz.moviper.base.interactor.BaseRxInteractor;
import com.example.there.moviperfood.viper.search.DaggerSearchInteractorComponent;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import lombok.val;

class SearchInteractor
        extends BaseRxInteractor
        implements SearchContract.Interactor {

    @Inject
    BaseFoodRepository foodRepository;

    public SearchInteractor() {
        super();
        val component = DaggerSearchInteractorComponent.builder()
                .searchInteractorModule(new SearchInteractorModule())
                .build();
        component.inject(this);
    }

    @Override
    public Observable<List<Cuisine>> loadCuisines(LatLng latLng) {
        return foodRepository.loadCuisines(latLng);
    }

    @Override
    public Observable<List<Restaurant>> loadRestaurants(LatLng latLng, Cuisine cuisine) {
        return foodRepository.loadRestaurants(latLng, cuisine);
    }
}

package com.example.there.moviperfood.viper.search;

import com.example.there.moviperfood.data.food.restaurant.Restaurant;
import com.example.there.moviperfood.data.place.CachedPlace;
import com.example.there.moviperfood.viper.common.BaseApiInteractor;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class SearchInteractor
        extends BaseApiInteractor
        implements SearchContract.Interactor {

    @Override
    public Flowable<List<Restaurant>> loadPreviouslySearchedRestaurants() {
        return getFoodRepository().getSavedRestaurants();
    }

    @Override
    public Flowable<List<CachedPlace>> loadPreviouslySearchedPlaces() {
        return getPlaceRepository().getSavedPlaces();
    }

    @Override
    public Completable savePlace(CachedPlace place) {
        return getPlaceRepository().insertPlace(place);
    }
}

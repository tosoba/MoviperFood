package com.example.there.moviperfood.viper.search;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.there.moviperfood.data.food.restaurant.Restaurant;
import com.example.there.moviperfood.data.place.CachedPlace;
import com.google.android.gms.maps.model.LatLng;
import com.mateuszkoslacz.moviper.base.presenter.BaseRxPresenter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import lombok.val;

public class SearchPresenter
        extends BaseRxPresenter<SearchContract.View, SearchContract.Interactor, SearchContract.Routing>
        implements SearchContract.Presenter {

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(true);
    }

    @NonNull
    @Override
    public SearchContract.Routing createRouting() {
        return new SearchRouting();
    }

    @NonNull
    @Override
    public SearchContract.Interactor createInteractor() {
        return new SearchInteractor();
    }

    @Override
    public void startCuisinesActivity(String placeName, LatLng placeLatLng) {
        getRouting().startCuisinesActivity(placeName, placeLatLng);
    }

    private List<Restaurant> restaurantsToUpdate;
    private boolean restaurantsLoadingInProgress = false;

    private void updateRestaurants(List<Restaurant> restaurants) {
        if (restaurants == null || restaurants.isEmpty()) return;

        val view = getView();
        if (view != null) {
            view.updatePreviouslySearchedRestaurants(restaurants);
            restaurantsToUpdate = null;
        } else restaurantsToUpdate = restaurants;
    }

    @Override
    public void loadPreviouslySearchedRestaurants() {
        if (restaurantsToUpdate == null && !restaurantsLoadingInProgress) {
            restaurantsLoadingInProgress = true;
            addSubscription(getInteractor().loadPreviouslySearchedRestaurants()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(() -> restaurantsLoadingInProgress = false)
                    .subscribe(this::updateRestaurants, error -> Log.e("Error", error.getMessage())));
        } else {
            updateRestaurants(restaurantsToUpdate);
        }
    }

    private List<CachedPlace> placesToUpdate;
    private boolean placesLoadingInProgress = false;

    private void updatePlaces(List<CachedPlace> places) {
        if (places == null || places.isEmpty()) return;

        val view = getView();
        if (view != null) {
            view.updatePreviouslySearchedPlaces(places);
            placesToUpdate = null;
        } else placesToUpdate = places;
    }

    @Override
    public void loadPreviouslySearchedPlaces() {
        if (placesToUpdate == null && !placesLoadingInProgress) {
            placesLoadingInProgress = true;
            addSubscription(getInteractor().loadPreviouslySearchedPlaces()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(() -> placesLoadingInProgress = false)
                    .subscribe(this::updatePlaces, error -> Log.e("Error", error.getMessage())));
        } else {
            updatePlaces(placesToUpdate);
        }
    }

    @Override
    public void savePlace(CachedPlace place) {
        addSubscription(getInteractor().savePlace(place)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());
    }
}

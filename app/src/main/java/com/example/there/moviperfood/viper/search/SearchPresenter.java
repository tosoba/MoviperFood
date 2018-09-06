package com.example.there.moviperfood.viper.search;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
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

    @Override
    public void startReviewsActivity(Restaurant restaurant) {
        getRouting().startReviewsActivity(restaurant);
    }

    private boolean restaurantsLoadingInProgress = false;
    private MutableLiveData<List<Restaurant>> restaurants = new MutableLiveData<>();

    @Override
    public LiveData<List<Restaurant>> getRestaurants() {
        return restaurants;
    }

    @Override
    public void loadPreviouslySearchedRestaurants() {
        if (!restaurantsLoadingInProgress) {
            restaurantsLoadingInProgress = true;
            addSubscription(getInteractor().loadPreviouslySearchedRestaurants()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(() -> restaurantsLoadingInProgress = false)
                    .subscribe((restaurants) -> this.restaurants.setValue(restaurants), error -> Log.e("Error", error.getMessage())));
        }
    }

    private boolean placesLoadingInProgress = false;

    private MutableLiveData<List<CachedPlace>> places = new MutableLiveData<>();

    @Override
    public LiveData<List<CachedPlace>> getPlaces() {
        return places;
    }

    @Override
    public void loadPreviouslySearchedPlaces() {
        if (!placesLoadingInProgress) {
            placesLoadingInProgress = true;
            addSubscription(getInteractor().loadPreviouslySearchedPlaces()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(() -> placesLoadingInProgress = false)
                    .subscribe((places) -> this.places.setValue(places), error -> Log.e("Error", error.getMessage())));
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

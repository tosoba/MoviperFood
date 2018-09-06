package com.example.there.moviperfood.viper.restaurants;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.there.moviperfood.data.food.cuisine.Cuisine;
import com.example.there.moviperfood.data.food.restaurant.Restaurant;
import com.google.android.gms.maps.model.LatLng;
import com.mateuszkoslacz.moviper.base.presenter.BaseRxPresenter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RestaurantsPresenter
        extends BaseRxPresenter<RestaurantsContract.View, RestaurantsContract.Interactor, RestaurantsContract.Routing>
        implements RestaurantsContract.Presenter {

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(true);
    }

    @NonNull
    @Override
    public RestaurantsContract.Routing createRouting() {
        return new RestaurantsRouting();
    }

    @NonNull
    @Override
    public RestaurantsContract.Interactor createInteractor() {
        return new RestaurantsInteractor();
    }

    private boolean restaurantsLoadingInProgress = false;
    private MutableLiveData<List<Restaurant>> restaurants = new MutableLiveData<>();

    @Override
    public LiveData<List<Restaurant>> getRestaurants() {
        return restaurants;
    }

    @Override
    public void loadRestaurants(LatLng latLng, Cuisine cuisine) {
        if (!restaurantsLoadingInProgress) {
            restaurantsLoadingInProgress = true;
            addSubscription(getInteractor().loadRestaurants(latLng, cuisine)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(() -> restaurantsLoadingInProgress = false)
                    .subscribe((restaurants) -> this.restaurants.setValue(restaurants), error -> Log.e("Error", error.getMessage())));
        }
    }

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        addSubscription(getInteractor().saveRestaurant(restaurant)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());
    }

    @Override
    public void startReviewsActivity(Restaurant restaurant) {
        getRouting().startReviewsActivity(restaurant);
    }

    @Override
    public void startMapActivity(Restaurant restaurant) {
        getRouting().startMapActivity(restaurant);
    }
}

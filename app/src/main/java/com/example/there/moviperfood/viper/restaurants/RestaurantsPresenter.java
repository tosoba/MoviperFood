package com.example.there.moviperfood.viper.restaurants;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.there.moviperfood.data.food.cuisine.Cuisine;
import com.example.there.moviperfood.data.food.restaurant.Restaurant;
import com.google.android.gms.maps.model.LatLng;
import com.mateuszkoslacz.moviper.base.presenter.BaseRxPresenter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import lombok.val;

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

    private List<Restaurant> restaurantsToUpdate;
    private boolean restaurantsLoadingInProgress = false;

    private void updateRestaurants(List<Restaurant> restaurants) {
        if (restaurants == null || restaurants.isEmpty()) return;

        val view = getView();
        if (view != null) {
            view.updateRestaurants(restaurants);
            restaurantsToUpdate = null;
        } else restaurantsToUpdate = restaurants;
    }

    @Override
    public void loadRestaurants(LatLng latLng, Cuisine cuisine) {
        if (restaurantsToUpdate == null && !restaurantsLoadingInProgress) {
            restaurantsLoadingInProgress = true;
            addSubscription(getInteractor().loadRestaurants(latLng, cuisine)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(() -> restaurantsLoadingInProgress = false)
                    .subscribe(this::updateRestaurants, error -> Log.e("Error", error.getMessage())));
        } else {
            updateRestaurants(restaurantsToUpdate);
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
    public void startRestaurantActivity(Restaurant restaurant) {
        getRouting().startRestaurantActivity(restaurant);
    }
}

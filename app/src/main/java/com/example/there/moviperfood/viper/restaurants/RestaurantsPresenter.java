package com.example.there.moviperfood.viper.restaurants;

import android.support.annotation.NonNull;
import android.util.Log;

import com.annimon.stream.Stream;
import com.example.there.moviperfood.data.cuisine.Cuisine;
import com.google.android.gms.maps.model.LatLng;
import com.mateuszkoslacz.moviper.base.presenter.BaseRxPresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RestaurantsPresenter
        extends BaseRxPresenter<RestaurantsContract.View, RestaurantsContract.Interactor, RestaurantsContract.Routing>
        implements RestaurantsContract.Presenter {

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

    @Override
    public void loadRestaurants(LatLng latLng, Cuisine cuisine) {
        addSubscription(getInteractor().loadRestaurants(latLng, cuisine)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(restaurants -> Stream.of(restaurants).forEach(restaurant -> Log.e("Restaurant", restaurant.getName())),
                        error -> Log.e("Error", error.getMessage())));
    }
}

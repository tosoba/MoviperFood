package com.example.there.moviperfood.viper.search;

import android.app.Activity;

import com.example.there.moviperfood.data.cuisine.Cuisine;
import com.example.there.moviperfood.data.restaurant.Restaurant;
import com.google.android.gms.maps.model.LatLng;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.mateuszkoslacz.moviper.iface.presenter.ViperPresenter;
import com.mateuszkoslacz.moviper.iface.interactor.ViperRxInteractor;
import com.mateuszkoslacz.moviper.iface.routing.ViperRxRouting;

import java.util.List;

import io.reactivex.Observable;

interface SearchContract {

    interface Presenter extends ViperPresenter<View> {
        void loadCuisines(LatLng latLng);
        void loadRestaurants(LatLng latLng, Cuisine cuisine);
    }

    interface View extends MvpView {
        void updateCuisines(List<Cuisine> cuisines);
    }

    interface Interactor extends ViperRxInteractor {
        Observable<List<Cuisine>> loadCuisines(LatLng latLng);
        Observable<List<Restaurant>> loadRestaurants(LatLng latLng, Cuisine cuisine);
    }

    interface Routing extends ViperRxRouting<Activity> {

    }
}

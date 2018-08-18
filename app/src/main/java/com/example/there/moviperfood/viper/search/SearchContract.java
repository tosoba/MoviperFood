package com.example.there.moviperfood.viper.search;

import android.app.Activity;

import com.example.there.moviperfood.data.food.cuisine.Cuisine;
import com.google.android.gms.maps.model.LatLng;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.mateuszkoslacz.moviper.iface.interactor.ViperRxInteractor;
import com.mateuszkoslacz.moviper.iface.presenter.ViperPresenter;
import com.mateuszkoslacz.moviper.iface.routing.ViperRxRouting;

import java.util.List;

import io.reactivex.Observable;

interface SearchContract {

    interface Presenter extends ViperPresenter<View> {
        void loadCuisines(LatLng latLng);

        void startRestaurantsActivity(Cuisine cuisine, LatLng latLng);
    }

    interface View extends MvpView {
        void updateCuisines(List<Cuisine> cuisines);
    }

    interface Interactor extends ViperRxInteractor {
        Observable<List<Cuisine>> loadCuisines(LatLng latLng);

    }

    interface Routing extends ViperRxRouting<Activity> {
        void startRestaurantsActivity(Cuisine cuisine, LatLng latLng);
    }
}

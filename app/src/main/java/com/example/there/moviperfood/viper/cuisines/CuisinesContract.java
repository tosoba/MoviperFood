package com.example.there.moviperfood.viper.cuisines;

import android.app.Activity;

import com.example.there.moviperfood.data.food.cuisine.Cuisine;
import com.google.android.gms.maps.model.LatLng;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.mateuszkoslacz.moviper.iface.interactor.ViperRxInteractor;
import com.mateuszkoslacz.moviper.iface.presenter.ViperPresenter;
import com.mateuszkoslacz.moviper.iface.routing.ViperRxRouting;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface CuisinesContract {
    interface Interactor extends ViperRxInteractor {
        Observable<List<Cuisine>> loadCuisines(LatLng latLng);

        Completable deleteMostRecentlyAddedPlace();
    }

    interface View extends MvpView {
        void updateCuisines(List<Cuisine> cuisines);

        void noDataRetrieved();
    }

    interface Presenter extends ViperPresenter<CuisinesContract.View> {
        void loadCuisines(LatLng latLng);

        void deleteMostRecentlyAddedPlace();

        void startRestaurantsActivity(Cuisine cuisine, LatLng latLng);
    }

    interface Routing extends ViperRxRouting<Activity> {
        void startRestaurantsActivity(Cuisine cuisine, LatLng latLng);
    }
}

package com.example.there.moviperfood.viper.search;

import android.app.Activity;

import com.google.android.gms.maps.model.LatLng;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.mateuszkoslacz.moviper.iface.interactor.ViperRxInteractor;
import com.mateuszkoslacz.moviper.iface.presenter.ViperPresenter;
import com.mateuszkoslacz.moviper.iface.routing.ViperRxRouting;

public interface SearchContract {

    interface Presenter extends ViperPresenter<View> {
        void startCuisinesActivity(LatLng latLng);
    }

    interface View extends MvpView {
    }

    interface Interactor extends ViperRxInteractor {

    }

    interface Routing extends ViperRxRouting<Activity> {
        void startCuisinesActivity(LatLng latLng);
    }
}

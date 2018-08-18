package com.example.there.moviperfood.viper.search;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.mateuszkoslacz.moviper.base.presenter.BaseRxPresenter;

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
    public void startCuisinesActivity(LatLng latLng) {
        getRouting().startCuisinesActivity(latLng);
    }
}

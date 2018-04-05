package com.example.there.moviperfood.viper.search;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.there.moviperfood.data.cuisine.Cuisine;
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

    private List<Cuisine> cuisinesToUpdate;
    private boolean cuisinesLoadingInProgress = false;

    private void updateCuisines(List<Cuisine> cuisines) {
        if (cuisines == null || cuisines.isEmpty()) return;

        val view = getView();
        if (view != null) {
            view.updateCuisines(cuisines);
            cuisinesToUpdate = null;
        } else cuisinesToUpdate = cuisines;
    }

    @Override
    public void loadCuisines(LatLng latLng) {
        if (cuisinesToUpdate == null && !cuisinesLoadingInProgress) {
            cuisinesLoadingInProgress = true;
            addSubscription(getInteractor().loadCuisines(latLng)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(() -> cuisinesLoadingInProgress = false)
                    .subscribe(this::updateCuisines, error -> Log.e("Error", error.getMessage())));
        } else {
            updateCuisines(cuisinesToUpdate);
        }
    }

    @Override
    public void startRestaurantsActivity(Cuisine cuisine, LatLng latLng) {
        getRouting().startRestaurantsActivity(cuisine, latLng);
    }
}

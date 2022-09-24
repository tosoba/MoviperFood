package com.example.there.moviperfood.viper.cuisines;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.there.moviperfood.data.food.cuisine.Cuisine;
import com.google.android.gms.maps.model.LatLng;
import com.mateuszkoslacz.moviper.base.presenter.BaseRxPresenter;

import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CuisinesPresenter
        extends BaseRxPresenter<CuisinesContract.View, CuisinesContract.Interactor, CuisinesContract.Routing>
        implements CuisinesContract.Presenter {

    @NonNull
    @Override
    public CuisinesContract.Interactor createInteractor() {
        return new CuisinesInteractor();
    }

    @NonNull
    @Override
    public CuisinesContract.Routing createRouting() {
        return new CuisinesRouting();
    }

    private boolean cuisinesLoadingInProgress = false;
    private MutableLiveData<List<Cuisine>> cuisines = new MutableLiveData<>();

    @Override
    public LiveData<List<Cuisine>> getCuisines() {
        return cuisines;
    }

    @Override
    public void loadCuisines(LatLng latLng) {
        if (!cuisinesLoadingInProgress) {
            cuisinesLoadingInProgress = true;
            addSubscription(getInteractor().loadCuisines(latLng)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(() -> cuisinesLoadingInProgress = false)
                    .subscribe((cuisines) -> this.cuisines.setValue(cuisines), error -> {
                        Log.w("Error", "Error", error);
                        this.cuisines.setValue(Collections.emptyList());
                    }));
        }
    }

    @Override
    public void startRestaurantsActivity(Cuisine cuisine, LatLng latLng) {
        getRouting().startRestaurantsActivity(cuisine, latLng);
    }

    @Override
    public void deleteMostRecentlyAddedPlace() {
        addSubscription(getInteractor().deleteMostRecentlyAddedPlace()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());
    }
}

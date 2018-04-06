package com.example.there.moviperfood.viper.restaurant;

import android.support.annotation.NonNull;
import android.util.Log;

import com.annimon.stream.Stream;
import com.example.there.moviperfood.data.restaurant.Restaurant;
import com.mateuszkoslacz.moviper.base.presenter.BaseRxPresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RestaurantPresenter
        extends BaseRxPresenter
        <RestaurantContract.View,
                RestaurantContract.Interactor,
                RestaurantContract.Routing>
        implements RestaurantContract.Presenter {

    @NonNull
    @Override
    public RestaurantContract.Routing createRouting() {
        return new RestaurantRouting();
    }

    @NonNull
    @Override
    public RestaurantContract.Interactor createInteractor() {
        return new RestaurantInteractor();
    }

    @Override
    public void loadReviews(Restaurant restaurant) {
        addSubscription(getInteractor().loadReviews(restaurant)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(reviews -> {
                    Stream.of(reviews).forEach(review -> Log.e("REV", review.getUser().getName()));
                }, error -> {
                }));
    }
}

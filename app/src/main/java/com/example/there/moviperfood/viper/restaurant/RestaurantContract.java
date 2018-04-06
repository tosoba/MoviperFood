package com.example.there.moviperfood.viper.restaurant;

import android.app.Activity;
import android.app.ListActivity;

import com.example.there.moviperfood.data.restaurant.Restaurant;
import com.example.there.moviperfood.data.review.Review;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.mateuszkoslacz.moviper.iface.presenter.ViperPresenter;
import com.mateuszkoslacz.moviper.iface.interactor.ViperRxInteractor;
import com.mateuszkoslacz.moviper.iface.routing.ViperRxRouting;

import java.util.List;

import io.reactivex.Observable;

interface RestaurantContract {

    interface Presenter extends ViperPresenter<View> {
        void loadReviews(Restaurant restaurant);
    }

    interface View extends MvpView {

    }

    interface Interactor extends ViperRxInteractor {
        Observable<List<Review>> loadReviews(Restaurant restaurant);
    }

    interface Routing extends ViperRxRouting<Activity> {

    }
}

package com.example.there.moviperfood.viper.restaurants;

import android.app.Activity;

import com.example.there.moviperfood.data.food.cuisine.Cuisine;
import com.example.there.moviperfood.data.food.restaurant.Restaurant;
import com.example.there.moviperfood.viper.common.BaseRouting;
import com.google.android.gms.maps.model.LatLng;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.mateuszkoslacz.moviper.iface.interactor.ViperRxInteractor;
import com.mateuszkoslacz.moviper.iface.presenter.ViperPresenter;
import com.mateuszkoslacz.moviper.iface.routing.ViperRxRouting;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

interface RestaurantsContract {

    interface Presenter extends ViperPresenter<View> {
        void loadRestaurants(LatLng latLng, Cuisine cuisine);
        void saveRestaurant(Restaurant restaurant);

        void startReviewsActivity(Restaurant restaurant);
    }

    interface View extends MvpView {
        void updateRestaurants(List<Restaurant> restaurants);
    }

    interface Interactor extends ViperRxInteractor {
        Observable<List<Restaurant>> loadRestaurants(LatLng latLng, Cuisine cuisine);
        Completable saveRestaurant(Restaurant restaurant);
    }

    interface Routing extends ViperRxRouting<Activity>, BaseRouting.StartsReviewsActivity {
        void startReviewsActivity(Restaurant restaurant);
    }
}

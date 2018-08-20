package com.example.there.moviperfood.viper.search;

import android.app.Activity;

import com.example.there.moviperfood.data.food.restaurant.Restaurant;
import com.example.there.moviperfood.data.place.CachedPlace;
import com.example.there.moviperfood.viper.common.BaseRouting;
import com.google.android.gms.maps.model.LatLng;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.mateuszkoslacz.moviper.iface.interactor.ViperRxInteractor;
import com.mateuszkoslacz.moviper.iface.presenter.ViperPresenter;
import com.mateuszkoslacz.moviper.iface.routing.ViperRxRouting;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface SearchContract {

    interface Presenter extends ViperPresenter<View> {
        void startCuisinesActivity(String placeName, LatLng placeLatLng);

        void startReviewsActivity(Restaurant restaurant);

        void loadPreviouslySearchedRestaurants();

        void loadPreviouslySearchedPlaces();

        void savePlace(CachedPlace place);
    }

    interface View extends MvpView {
        void updatePreviouslySearchedRestaurants(List<Restaurant> restaurants);

        void updatePreviouslySearchedPlaces(List<CachedPlace> places);
    }

    interface Interactor extends ViperRxInteractor {
        Flowable<List<Restaurant>> loadPreviouslySearchedRestaurants();

        Flowable<List<CachedPlace>> loadPreviouslySearchedPlaces();

        Completable savePlace(CachedPlace place);
    }

    interface Routing extends ViperRxRouting<Activity>, BaseRouting.StartsCuisinesActivity, BaseRouting.StartsReviewsActivity {
        void startCuisinesActivity(String placeName, LatLng latLng);

        void startReviewsActivity(Restaurant restaurant);
    }
}

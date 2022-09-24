package com.example.there.moviperfood.viper.reviews;

import android.app.Activity;
import androidx.lifecycle.LiveData;

import com.example.there.moviperfood.data.food.restaurant.Restaurant;
import com.example.there.moviperfood.data.food.review.Review;
import com.example.there.moviperfood.viper.common.BaseRouting;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.mateuszkoslacz.moviper.iface.interactor.ViperRxInteractor;
import com.mateuszkoslacz.moviper.iface.presenter.ViperPresenter;
import com.mateuszkoslacz.moviper.iface.routing.ViperRxRouting;

import java.util.List;

import io.reactivex.Observable;

interface ReviewsContract {

    interface Presenter extends ViperPresenter<View> {
        void loadReviews(Restaurant restaurant);

        void startMapActivity(Restaurant restaurant);

        LiveData<List<Review>> getReviews();
    }

    interface View extends MvpView {
    }

    interface Interactor extends ViperRxInteractor {
        Observable<List<Review>> loadReviews(Restaurant restaurant);
    }

    interface Routing extends ViperRxRouting<Activity>, BaseRouting.StartsMapActivity {
        void startMapActivity(Restaurant restaurant);
    }
}

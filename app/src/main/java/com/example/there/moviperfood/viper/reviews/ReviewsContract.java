package com.example.there.moviperfood.viper.reviews;

import android.app.Activity;

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
    }

    interface View extends MvpView {
        void updateReviews(List<Review> reviews);
        void onNoReviewsFound();
    }

    interface Interactor extends ViperRxInteractor {
        Observable<List<Review>> loadReviews(Restaurant restaurant);
    }

    interface Routing extends ViperRxRouting<Activity>, BaseRouting.StartsMapActivity {
        void startMapActivity(Restaurant restaurant);
    }
}

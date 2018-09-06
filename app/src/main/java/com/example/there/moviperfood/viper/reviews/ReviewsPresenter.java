package com.example.there.moviperfood.viper.reviews;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.there.moviperfood.data.food.restaurant.Restaurant;
import com.example.there.moviperfood.data.food.review.Review;
import com.mateuszkoslacz.moviper.base.presenter.BaseRxPresenter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ReviewsPresenter
        extends BaseRxPresenter<ReviewsContract.View, ReviewsContract.Interactor, ReviewsContract.Routing>
        implements ReviewsContract.Presenter {

    @NonNull
    @Override
    public ReviewsContract.Routing createRouting() {
        return new ReviewsRouting();
    }

    @NonNull
    @Override
    public ReviewsContract.Interactor createInteractor() {
        return new ReviewsInteractor();
    }

    private boolean reviewsLoadingInProgress = false;
    private MutableLiveData<List<Review>> reviews = new MutableLiveData<>();

    @Override
    public MutableLiveData<List<Review>> getReviews() {
        return reviews;
    }

    @Override
    public void loadReviews(Restaurant restaurant) {
        if (!reviewsLoadingInProgress) {
            reviewsLoadingInProgress = true;
            addSubscription(getInteractor().loadReviews(restaurant)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(() -> reviewsLoadingInProgress = false)
                    .subscribe((reviews) -> this.reviews.setValue(reviews), error -> Log.e("ERROR", error.getMessage())));
        }
    }

    @Override
    public void startMapActivity(Restaurant restaurant) {
        getRouting().startMapActivity(restaurant);
    }
}

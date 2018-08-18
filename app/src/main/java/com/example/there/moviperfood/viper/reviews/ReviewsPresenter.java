package com.example.there.moviperfood.viper.reviews;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.there.moviperfood.data.food.restaurant.Restaurant;
import com.example.there.moviperfood.data.food.review.Review;
import com.mateuszkoslacz.moviper.base.presenter.BaseRxPresenter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import lombok.val;

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

    private List<Review> reviewsToUpdate;
    private boolean reviewsLoadingInProgress = false;

    private void updateReviews(List<Review> reviews) {
        if (reviews == null || reviews.isEmpty()) return;

        val view = getView();
        if (view != null) {
            view.updateReviews(reviews);
            reviewsToUpdate = null;
        } else reviewsToUpdate = reviews;
    }

    @Override
    public void loadReviews(Restaurant restaurant) {
        if (reviewsToUpdate == null && !reviewsLoadingInProgress) {
            reviewsLoadingInProgress = true;
            addSubscription(getInteractor().loadReviews(restaurant)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(() -> reviewsLoadingInProgress = false)
                    .subscribe(this::updateReviews, error -> Log.e("ERROR", error.getMessage())));
        } else {
            updateReviews(reviewsToUpdate);
        }
    }
}

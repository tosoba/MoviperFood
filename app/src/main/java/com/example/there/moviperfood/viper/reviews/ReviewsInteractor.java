package com.example.there.moviperfood.viper.reviews;

import com.example.there.moviperfood.data.food.restaurant.Restaurant;
import com.example.there.moviperfood.data.food.review.Review;
import com.example.there.moviperfood.viper.common.BaseApiInteractor;

import java.util.List;

import io.reactivex.Observable;

public class ReviewsInteractor
        extends BaseApiInteractor
        implements ReviewsContract.Interactor {

    @Override
    public Observable<List<Review>> loadReviews(Restaurant restaurant) {
        return getFoodRepository().loadReviews(restaurant);
    }
}

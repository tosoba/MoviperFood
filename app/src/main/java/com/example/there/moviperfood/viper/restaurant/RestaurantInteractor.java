package com.example.there.moviperfood.viper.restaurant;

import com.example.there.moviperfood.data.restaurant.Restaurant;
import com.example.there.moviperfood.data.review.Review;
import com.example.there.moviperfood.viper.common.BaseApiInteractor;

import java.util.List;

import io.reactivex.Observable;

public class RestaurantInteractor
        extends BaseApiInteractor
        implements RestaurantContract.Interactor {

    @Override
    public Observable<List<Review>> loadReviews(Restaurant restaurant) {
        return getFoodRepository().loadReviews(restaurant);
    }
}

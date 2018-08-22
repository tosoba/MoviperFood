package com.example.there.moviperfood.viper.reviews;

import android.app.Activity;

import com.example.there.moviperfood.data.food.restaurant.Restaurant;
import com.mateuszkoslacz.moviper.base.routing.BaseRxRouting;

class ReviewsRouting
        extends BaseRxRouting<Activity>
        implements ReviewsContract.Routing {

    @Override
    public void startMapActivity(Restaurant restaurant) {
        startMapActivity(isContextAttached(), getRelatedContext(), restaurant);
    }
}

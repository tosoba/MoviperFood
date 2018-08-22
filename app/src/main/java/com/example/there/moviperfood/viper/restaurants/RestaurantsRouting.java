package com.example.there.moviperfood.viper.restaurants;

import android.app.Activity;

import com.example.there.moviperfood.data.food.restaurant.Restaurant;
import com.mateuszkoslacz.moviper.base.routing.BaseRxRouting;

class RestaurantsRouting
        extends BaseRxRouting<Activity>
        implements RestaurantsContract.Routing {

    @Override
    public void startReviewsActivity(Restaurant restaurant) {
        startReviewsActivity(isContextAttached(), getRelatedContext(), restaurant);
    }

    @Override
    public void startMapActivity(Restaurant restaurant) {
        startMapActivity(isContextAttached(), getRelatedContext(), restaurant);
    }
}

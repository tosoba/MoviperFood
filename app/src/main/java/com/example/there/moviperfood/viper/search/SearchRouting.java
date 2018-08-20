package com.example.there.moviperfood.viper.search;

import android.app.Activity;

import com.example.there.moviperfood.data.food.restaurant.Restaurant;
import com.google.android.gms.maps.model.LatLng;
import com.mateuszkoslacz.moviper.base.routing.BaseRxRouting;

@SuppressWarnings({"unchecked", "ConstantConditions"})
class SearchRouting
        extends BaseRxRouting<Activity>
        implements SearchContract.Routing {

    @Override
    public void startCuisinesActivity(String placeName, LatLng placeLatLng) {
        startCuisinesActivity(isContextAttached(), getRelatedContext(), placeName, placeLatLng);
    }

    @Override
    public void startReviewsActivity(Restaurant restaurant) {
        startReviewsActivity(isContextAttached(), getRelatedContext(), restaurant);
    }
}

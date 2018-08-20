package com.example.there.moviperfood.viper.cuisines;

import android.app.Activity;

import com.example.there.moviperfood.data.food.cuisine.Cuisine;
import com.google.android.gms.maps.model.LatLng;
import com.mateuszkoslacz.moviper.base.routing.BaseRxRouting;

public class CuisinesRouting extends BaseRxRouting<Activity>
        implements CuisinesContract.Routing {

    @SuppressWarnings({"ConstantConditions", "unchecked"})
    @Override
    public void startRestaurantsActivity(Cuisine cuisine, LatLng latLng) {
        startRestaurantsActivity(isContextAttached(), getRelatedContext(), cuisine, latLng);
    }
}

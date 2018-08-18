package com.example.there.moviperfood.viper.cuisines;

import android.app.Activity;

import com.example.there.moviperfood.data.food.cuisine.Cuisine;
import com.example.there.moviperfood.viper.restaurants.RestaurantsActivity;
import com.example.there.moviperfood.viper.restaurants.RestaurantsPresenter;
import com.google.android.gms.maps.model.LatLng;
import com.mateuszkoslacz.moviper.base.routing.BaseRxRouting;
import com.mateuszkoslacz.moviper.presentersdispatcher.ActivityStarter;
import com.mateuszkoslacz.moviper.presentersdispatcher.MoviperPresentersDispatcher;

import lombok.val;

public class CuisinesRouting extends BaseRxRouting<Activity>
        implements CuisinesContract.Routing {

    @SuppressWarnings({"ConstantConditions", "unchecked"})
    @Override
    public void startRestaurantsActivity(Cuisine cuisine, LatLng latLng) {
        if (isContextAttached()) {
            val startingIntent = RestaurantsActivity.startingIntent(getRelatedContext(), cuisine, latLng);
            MoviperPresentersDispatcher.getInstance().startActivity(ActivityStarter.newBuilder()
                    .withContext(getRelatedContext())
                    .withIntent(startingIntent)
                    .withPresenter(new RestaurantsPresenter())
                    .build());
        }
    }
}

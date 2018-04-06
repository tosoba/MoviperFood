package com.example.there.moviperfood.viper.restaurants;

import android.app.Activity;

import com.example.there.moviperfood.data.restaurant.Restaurant;
import com.example.there.moviperfood.viper.restaurant.RestaurantActivity;
import com.example.there.moviperfood.viper.restaurant.RestaurantPresenter;
import com.mateuszkoslacz.moviper.base.routing.BaseRxRouting;
import com.mateuszkoslacz.moviper.presentersdispatcher.ActivityStarter;
import com.mateuszkoslacz.moviper.presentersdispatcher.MoviperPresentersDispatcher;

import lombok.val;

class RestaurantsRouting
        extends BaseRxRouting<Activity>
        implements RestaurantsContract.Routing {

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    @Override
    public void startRestaurantActivity(Restaurant restaurant) {
        if (isContextAttached()) {
            val startingIntent = RestaurantActivity.startingIntent(getRelatedContext(), restaurant);
            MoviperPresentersDispatcher.getInstance().startActivity(ActivityStarter.newBuilder()
                    .withContext(getRelatedContext())
                    .withIntent(startingIntent)
                    .withPresenter(new RestaurantPresenter())
                    .build());
        }
    }
}

package com.example.there.moviperfood.viper.search;

import android.app.Activity;

import com.example.there.moviperfood.viper.cuisines.CuisinesActivity;
import com.example.there.moviperfood.viper.cuisines.CuisinesPresenter;
import com.google.android.gms.maps.model.LatLng;
import com.mateuszkoslacz.moviper.base.routing.BaseRxRouting;
import com.mateuszkoslacz.moviper.presentersdispatcher.ActivityStarter;
import com.mateuszkoslacz.moviper.presentersdispatcher.MoviperPresentersDispatcher;

import lombok.val;

class SearchRouting
        extends BaseRxRouting<Activity>
        implements SearchContract.Routing {

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    @Override
    public void startCuisinesActivity(LatLng latLng) {
        if (isContextAttached()) {
            val startingIntent = CuisinesActivity.startingIntent(getRelatedContext(), latLng);
            MoviperPresentersDispatcher.getInstance().startActivity(ActivityStarter.newBuilder()
                    .withContext(getRelatedContext())
                    .withIntent(startingIntent)
                    .withPresenter(new CuisinesPresenter())
                    .build());
        }
    }
}

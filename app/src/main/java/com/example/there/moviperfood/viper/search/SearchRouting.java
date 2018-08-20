package com.example.there.moviperfood.viper.search;

import android.app.Activity;

import com.example.there.moviperfood.data.food.restaurant.Restaurant;
import com.example.there.moviperfood.viper.cuisines.CuisinesActivity;
import com.example.there.moviperfood.viper.cuisines.CuisinesPresenter;
import com.example.there.moviperfood.viper.reviews.ReviewsActivity;
import com.example.there.moviperfood.viper.reviews.ReviewsPresenter;
import com.google.android.gms.maps.model.LatLng;
import com.mateuszkoslacz.moviper.base.routing.BaseRxRouting;
import com.mateuszkoslacz.moviper.presentersdispatcher.ActivityStarter;
import com.mateuszkoslacz.moviper.presentersdispatcher.MoviperPresentersDispatcher;

import lombok.val;

@SuppressWarnings({"unchecked", "ConstantConditions"})
class SearchRouting
        extends BaseRxRouting<Activity>
        implements SearchContract.Routing {

    @Override
    public void startCuisinesActivity(String placeName, LatLng placeLatLng) {
        if (isContextAttached()) {
            val startingIntent = CuisinesActivity.startingIntent(getRelatedContext(), placeLatLng, placeName);
            MoviperPresentersDispatcher.getInstance().startActivity(ActivityStarter.newBuilder()
                    .withContext(getRelatedContext())
                    .withIntent(startingIntent)
                    .withPresenter(new CuisinesPresenter())
                    .build());
        }
    }

    @Override
    public void startReviewsActivity(Restaurant restaurant) {
        if (isContextAttached()) {
            val startingIntent = ReviewsActivity.startingIntent(getRelatedContext(), restaurant);
            MoviperPresentersDispatcher.getInstance().startActivity(ActivityStarter.newBuilder()
                    .withContext(getRelatedContext())
                    .withIntent(startingIntent)
                    .withPresenter(new ReviewsPresenter())
                    .build());
        }
    }
}

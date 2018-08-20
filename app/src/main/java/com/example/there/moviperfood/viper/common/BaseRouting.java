package com.example.there.moviperfood.viper.common;

import android.content.Context;

import com.example.there.moviperfood.data.food.cuisine.Cuisine;
import com.example.there.moviperfood.data.food.restaurant.Restaurant;
import com.example.there.moviperfood.viper.cuisines.CuisinesActivity;
import com.example.there.moviperfood.viper.cuisines.CuisinesPresenter;
import com.example.there.moviperfood.viper.restaurants.RestaurantsActivity;
import com.example.there.moviperfood.viper.restaurants.RestaurantsPresenter;
import com.example.there.moviperfood.viper.reviews.ReviewsActivity;
import com.example.there.moviperfood.viper.reviews.ReviewsPresenter;
import com.google.android.gms.maps.model.LatLng;
import com.mateuszkoslacz.moviper.presentersdispatcher.ActivityStarter;
import com.mateuszkoslacz.moviper.presentersdispatcher.MoviperPresentersDispatcher;

import lombok.val;

@SuppressWarnings({"ConstantConditions", "unchecked"})
public interface BaseRouting {
    interface StartsRestaurantsActivity {
        default void startRestaurantsActivity(boolean isContextAttached, Context relatedContext, Cuisine cuisine, LatLng latLng) {
            if (isContextAttached) {
                val startingIntent = RestaurantsActivity.startingIntent(relatedContext, cuisine, latLng);
                MoviperPresentersDispatcher.getInstance().startActivity(ActivityStarter.newBuilder()
                        .withContext(relatedContext)
                        .withIntent(startingIntent)
                        .withPresenter(new RestaurantsPresenter())
                        .build());
            }
        }
    }

    interface StartsCuisinesActivity {
        default void startCuisinesActivity(boolean isContextAttached, Context relatedContext, String placeName, LatLng placeLatLng) {
            if (isContextAttached) {
                val startingIntent = CuisinesActivity.startingIntent(relatedContext, placeLatLng, placeName);
                MoviperPresentersDispatcher.getInstance().startActivity(ActivityStarter.newBuilder()
                        .withContext(relatedContext)
                        .withIntent(startingIntent)
                        .withPresenter(new CuisinesPresenter())
                        .build());
            }
        }
    }

    interface StartsReviewsActivity {
        default void startReviewsActivity(boolean isContextAttached, Context relatedContext, Restaurant restaurant) {
            if (isContextAttached) {
                val startingIntent = ReviewsActivity.startingIntent(relatedContext, restaurant);
                MoviperPresentersDispatcher.getInstance().startActivity(ActivityStarter.newBuilder()
                        .withContext(relatedContext)
                        .withIntent(startingIntent)
                        .withPresenter(new ReviewsPresenter())
                        .build());
            }
        }
    }
}

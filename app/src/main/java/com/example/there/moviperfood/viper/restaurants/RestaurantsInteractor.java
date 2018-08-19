package com.example.there.moviperfood.viper.restaurants;

import com.example.there.moviperfood.data.food.cuisine.Cuisine;
import com.example.there.moviperfood.data.food.restaurant.Restaurant;
import com.example.there.moviperfood.viper.common.BaseApiInteractor;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class RestaurantsInteractor
        extends BaseApiInteractor
        implements RestaurantsContract.Interactor {

    @Override
    public Observable<List<Restaurant>> loadRestaurants(LatLng latLng, Cuisine cuisine) {
        return getFoodRepository().loadRestaurants(latLng, cuisine);
    }

    @Override
    public Completable saveRestaurant(Restaurant restaurant) {
        return getFoodRepository().insertRestaurant(restaurant);
    }
}

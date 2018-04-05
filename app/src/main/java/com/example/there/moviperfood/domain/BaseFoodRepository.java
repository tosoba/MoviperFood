package com.example.there.moviperfood.domain;

import com.example.there.moviperfood.data.cuisine.Cuisine;
import com.example.there.moviperfood.data.restaurant.Restaurant;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import io.reactivex.Observable;

public interface BaseFoodRepository {
    Observable<List<Cuisine>> loadCuisines(LatLng latLng);

    Observable<List<Restaurant>> loadRestaurants(LatLng latLng, Cuisine cuisine);
}

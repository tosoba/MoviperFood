package com.example.there.moviperfood.viper.restaurants.fragment.map;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annimon.stream.Stream;
import com.example.there.moviperfood.R;
import com.example.there.moviperfood.data.food.restaurant.Restaurant;
import com.example.there.moviperfood.viper.restaurants.fragment.RestaurantsFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import lombok.val;

public class RestaurantsMapFragment extends RestaurantsFragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initMap();
    }

    @Override
    public void setRestaurants(List<Restaurant> restaurants) {
        super.setRestaurants(restaurants);
        addRestaurantsToMap();
    }

    private GoogleMap map;

    private OnMapReadyCallback onMapReady = googleMap -> {
        map = googleMap;
        addRestaurantsToMap();
    };

    private boolean canRestaurantsBeAdded() {
        return viewModel != null && !viewModel.getRestaurants().isEmpty() && map != null;
    }

    private void addRestaurantsToMap() {
        if (canRestaurantsBeAdded()) {
            val boundsBuilder = new LatLngBounds.Builder();
            Stream.of(viewModel.getRestaurants()).forEach(restaurant -> {
                val position = restaurant.getLocation().getLatLng();
                map.addMarker(new MarkerOptions().position(position).title(restaurant.getName()));
                boundsBuilder.include(position);
            });
            map.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 150));
        }
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(onMapReady);
    }

    public static RestaurantsMapFragment newInstance(ArrayList<Restaurant> restaurants) {
        val fragment = new RestaurantsMapFragment();
        fragment.putArguments(restaurants);
        return fragment;
    }
}

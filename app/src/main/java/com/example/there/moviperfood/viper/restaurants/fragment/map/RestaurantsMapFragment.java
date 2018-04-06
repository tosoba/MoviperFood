package com.example.there.moviperfood.viper.restaurants.fragment.map;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.there.moviperfood.R;
import com.example.there.moviperfood.data.restaurant.Restaurant;
import com.example.there.moviperfood.viper.restaurants.fragment.RestaurantsFragment;

import java.util.ArrayList;

import lombok.val;

public class RestaurantsMapFragment extends RestaurantsFragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }


    public static RestaurantsMapFragment newInstance(ArrayList<Restaurant> restaurants) {
        val fragment = new RestaurantsMapFragment();
        fragment.putArguments(restaurants);
        return fragment;
    }
}

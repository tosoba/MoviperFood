package com.example.there.moviperfood.viper.restaurants.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.there.moviperfood.data.restaurant.Restaurant;

import java.util.ArrayList;
import java.util.List;

import lombok.val;

public abstract class RestaurantsFragment extends Fragment {
    protected ArrayList<Restaurant> restaurants;

    public void setRestaurants(List<Restaurant> restaurants) {
        if (this.restaurants != null || restaurants == null) return;
        this.restaurants = new ArrayList<>(restaurants);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initArguments();
        initFromSavedState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (restaurants != null) outState.putParcelableArrayList(KEY_RESTAURANTS, restaurants);
    }

    private void initArguments() {
        val args = getArguments();
        if (args != null) {
            if (args.containsKey(ARG_RESTAURANTS)) {
                setRestaurants(args.getParcelableArrayList(ARG_RESTAURANTS));
            }
        }
    }

    private void initFromSavedState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(KEY_RESTAURANTS)) restaurants = savedInstanceState.getParcelableArrayList(KEY_RESTAURANTS);
        }
    }

    protected RestaurantsFragmentInteractionListener fragmentInteractionListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RestaurantsFragmentInteractionListener) {
            fragmentInteractionListener = (RestaurantsFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentInteractionListener = null;
    }

    private static final String ARG_RESTAURANTS = "ARG_RESTAURANTS";

    protected void putArguments(ArrayList<Restaurant> restaurants) {
        val args = new Bundle();
        if (restaurants != null) {
            args.putParcelableArrayList(ARG_RESTAURANTS, restaurants);
            setArguments(args);
        }
    }

    private static final String KEY_RESTAURANTS = "KEY_RESTAURANTS";
}

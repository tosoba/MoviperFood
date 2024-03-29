package com.example.there.moviperfood.viper.restaurants.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.there.moviperfood.data.food.restaurant.Restaurant;

import java.util.ArrayList;
import java.util.List;

import lombok.val;

public abstract class RestaurantsFragment extends Fragment {
    protected RestaurantsFragmentViewModel viewModel = new RestaurantsFragmentViewModel();

    public void setRestaurants(List<Restaurant> restaurants) {
        if (!viewModel.getRestaurants().isEmpty()) return;
        viewModel.getRestaurants().addAll(restaurants);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFromArguments();
        initFromSavedState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (viewModel != null) outState.putParcelable(KEY_RESTAURANTS_VIEW_MODEL, viewModel);
    }

    private void initFromArguments() {
        val args = getArguments();
        if (args != null) {
            if (args.containsKey(ARG_RESTAURANTS)) {
                setRestaurants(args.getParcelableArrayList(ARG_RESTAURANTS));
            }
        }
    }

    private void initFromSavedState(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_RESTAURANTS_VIEW_MODEL)) {
            viewModel = savedInstanceState.getParcelable(KEY_RESTAURANTS_VIEW_MODEL);
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

    private static final String KEY_RESTAURANTS_VIEW_MODEL = "KEY_RESTAURANTS_VIEW_MODEL";
}

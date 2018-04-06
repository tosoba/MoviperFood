package com.example.there.moviperfood.viper.restaurants.fragment.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.there.moviperfood.R;
import com.example.there.moviperfood.data.restaurant.Restaurant;
import com.example.there.moviperfood.viper.restaurants.fragment.RestaurantsFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.val;

public class RestaurantsListFragment extends RestaurantsFragment {
    private RestaurantsListAdapter restaurantsListAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        val view = inflater.inflate(R.layout.fragment_list, container, false);
        initRestaurantsRecyclerView(view);
        return view;
    }

    @Override
    public void setRestaurants(List<Restaurant> restaurants) {
        super.setRestaurants(restaurants);
        if (restaurantsListAdapter != null) {
            restaurantsListAdapter.setRestaurants(restaurants);
        }
    }

    private OnRestaurantItemClickListener onRestaurantClickListener = restaurant -> {
        if (fragmentInteractionListener != null) fragmentInteractionListener.onRestaurantSelected(restaurant);
    };

    private void initRestaurantsRecyclerView(View fragmentView) {
        RecyclerView restaurantsRecyclerView = fragmentView.findViewById(R.id.restaurants_recycler_view);
        restaurantsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        if (restaurants == null) {
            restaurantsListAdapter = new RestaurantsListAdapter(Collections.emptyList(), onRestaurantClickListener);
        } else {
            restaurantsListAdapter = new RestaurantsListAdapter(restaurants, onRestaurantClickListener);
        }
        restaurantsRecyclerView.setAdapter(restaurantsListAdapter);

    }

    public static RestaurantsListFragment newInstance(ArrayList<Restaurant> restaurants) {
        val fragment = new RestaurantsListFragment();
        fragment.putArguments(restaurants);
        return fragment;
    }

}

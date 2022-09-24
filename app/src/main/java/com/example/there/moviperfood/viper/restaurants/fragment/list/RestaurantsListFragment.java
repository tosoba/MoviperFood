package com.example.there.moviperfood.viper.restaurants.fragment.list;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.there.moviperfood.R;
import com.example.there.moviperfood.data.food.restaurant.Restaurant;
import com.example.there.moviperfood.databinding.FragmentListBinding;
import com.example.there.moviperfood.viper.common.OnListItemClickListener;
import com.example.there.moviperfood.viper.restaurants.fragment.RestaurantsFragment;

import java.util.ArrayList;

import lombok.val;

public class RestaurantsListFragment extends RestaurantsFragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentListBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false);
        binding.setRestaurantsListView(new RestaurantsListView(
                viewModel,
                new RestaurantsListAdapter(viewModel.getRestaurants(), onRestaurantClickListener, restaurant -> {
                    if (fragmentInteractionListener != null)
                        fragmentInteractionListener.onShowOnMapClicked(restaurant);
                })));
        binding.restaurantsRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        return binding.getRoot();
    }

    private OnListItemClickListener<Restaurant> onRestaurantClickListener = item -> {
        if (fragmentInteractionListener != null)
            fragmentInteractionListener.onRestaurantSelected(item);
    };

    public static RestaurantsListFragment newInstance(ArrayList<Restaurant> restaurants) {
        val fragment = new RestaurantsListFragment();
        fragment.putArguments(restaurants);
        return fragment;
    }

}

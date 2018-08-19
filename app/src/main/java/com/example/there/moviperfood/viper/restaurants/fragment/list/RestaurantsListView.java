package com.example.there.moviperfood.viper.restaurants.fragment.list;

import com.example.there.moviperfood.viper.restaurants.fragment.RestaurantsFragmentViewModel;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RestaurantsListView {
    private RestaurantsFragmentViewModel viewModel;
    private RestaurantsListAdapter adapter;
}

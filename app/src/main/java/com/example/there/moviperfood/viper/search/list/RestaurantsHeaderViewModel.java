package com.example.there.moviperfood.viper.search.list;

import androidx.databinding.ObservableList;

import com.example.there.moviperfood.data.food.restaurant.Restaurant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RestaurantsHeaderViewModel {
    private ObservableList<Restaurant> restaurants;
}

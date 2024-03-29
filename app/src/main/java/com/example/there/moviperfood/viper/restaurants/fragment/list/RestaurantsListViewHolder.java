package com.example.there.moviperfood.viper.restaurants.fragment.list;

import androidx.recyclerview.widget.RecyclerView;

import com.example.there.moviperfood.data.food.restaurant.Restaurant;
import com.example.there.moviperfood.databinding.RestaurantListItemBinding;
import com.example.there.moviperfood.viper.common.OnListItemClickListener;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

class RestaurantsListViewHolder extends RecyclerView.ViewHolder {
    @Setter(AccessLevel.PRIVATE)
    @Getter
    private RestaurantListItemBinding binding;

    RestaurantsListViewHolder(RestaurantListItemBinding binding, OnListItemClickListener<Restaurant> listener) {
        super(binding.getRoot());
        this.binding = binding;

        binding.getRoot().setOnClickListener(view -> {
            val restaurant = binding.getRestaurant();
            if (restaurant != null) {
                listener.onClick(restaurant);
            }
        });
    }
}

package com.example.there.moviperfood.viper.restaurants.fragment.list;

import android.support.v7.widget.RecyclerView;

import com.example.there.moviperfood.databinding.RestaurantListItemBinding;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

class RestaurantsListViewHolder extends RecyclerView.ViewHolder {
    @Setter(AccessLevel.PRIVATE)
    @Getter
    private RestaurantListItemBinding binding;

    RestaurantsListViewHolder(RestaurantListItemBinding binding, OnRestaurantItemClickListener listener) {
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

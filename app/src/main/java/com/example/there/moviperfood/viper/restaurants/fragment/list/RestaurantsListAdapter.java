package com.example.there.moviperfood.viper.restaurants.fragment.list;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.there.moviperfood.R;
import com.example.there.moviperfood.data.restaurant.Restaurant;
import com.example.there.moviperfood.databinding.RestaurantListItemBinding;

import java.util.List;

import lombok.val;

public class RestaurantsListAdapter extends RecyclerView.Adapter<RestaurantsListViewHolder> {
    private List<Restaurant> restaurants;
    private OnRestaurantItemClickListener onClickListener;

    RestaurantsListAdapter(List<Restaurant> restaurants, OnRestaurantItemClickListener onClickListener) {
        this.restaurants = restaurants;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public RestaurantsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        val inflater = LayoutInflater.from(parent.getContext());
        RestaurantListItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.restaurant_list_item, parent, false);
        return new RestaurantsListViewHolder(binding, onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantsListViewHolder holder, int position) {
        val restaurant = restaurants.get(position);
        holder.getBinding().setRestaurant(restaurant);
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
        notifyDataSetChanged();
    }
}

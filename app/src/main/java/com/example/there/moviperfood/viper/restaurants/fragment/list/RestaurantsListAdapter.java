package com.example.there.moviperfood.viper.restaurants.fragment.list;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.there.moviperfood.R;
import com.example.there.moviperfood.data.food.restaurant.Restaurant;
import com.example.there.moviperfood.databinding.RestaurantListItemBinding;
import com.example.there.moviperfood.util.AdapterUtils;

import lombok.val;

public class RestaurantsListAdapter extends RecyclerView.Adapter<RestaurantsListViewHolder> {
    private ObservableList<Restaurant> restaurants;
    private OnRestaurantItemClickListener onClickListener;

    RestaurantsListAdapter(ObservableList<Restaurant> restaurants, OnRestaurantItemClickListener onClickListener) {
        this.restaurants = restaurants;
        this.onClickListener = onClickListener;
        AdapterUtils.bindAdapterToItems(this, restaurants);
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
}

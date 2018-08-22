package com.example.there.moviperfood.viper.restaurants.fragment.list;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.there.moviperfood.R;
import com.example.there.moviperfood.data.food.restaurant.Restaurant;
import com.example.there.moviperfood.databinding.RestaurantListItemBinding;
import com.example.there.moviperfood.util.AdapterUtils;
import com.example.there.moviperfood.viper.common.OnListItemClickListener;
import com.example.there.moviperfood.viper.restaurants.RestaurantsActivity;

import lombok.val;

public class RestaurantsListAdapter extends RecyclerView.Adapter<RestaurantsListViewHolder> {
    private ObservableList<Restaurant> restaurants;
    private final OnListItemClickListener<Restaurant> onClickListener;
    private final RestaurantsActivity.ShowsOnMap showsOnMap;

    RestaurantsListAdapter(ObservableList<Restaurant> restaurants,
                           OnListItemClickListener<Restaurant> onClickListener,
                           RestaurantsActivity.ShowsOnMap showsOnMap) {
        this.restaurants = restaurants;
        this.onClickListener = onClickListener;
        this.showsOnMap = showsOnMap;
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
        holder.getBinding().setShowOnMapListener((View.OnClickListener) v -> showsOnMap.onShowOnMapClicked(restaurant));
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }
}

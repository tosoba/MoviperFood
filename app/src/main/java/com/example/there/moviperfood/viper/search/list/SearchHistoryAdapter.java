package com.example.there.moviperfood.viper.search.list;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.there.moviperfood.R;
import com.example.there.moviperfood.data.food.restaurant.Restaurant;
import com.example.there.moviperfood.data.place.CachedPlace;
import com.example.there.moviperfood.databinding.PreviouslySearchedPlaceItemBinding;
import com.example.there.moviperfood.databinding.PreviouslySearchedPlacesHeaderBinding;
import com.example.there.moviperfood.databinding.PreviouslySearchedRestaurantItemBinding;
import com.example.there.moviperfood.databinding.PreviouslySearchedRestaurantsHeaderBinding;
import com.example.there.moviperfood.databinding.PreviouslySearchedRestaurantsListBinding;
import com.example.there.moviperfood.util.AdapterUtils;
import com.example.there.moviperfood.util.ObservableSortedList;

import java.security.InvalidParameterException;

import lombok.val;

public class SearchHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int RESTAURANTS_VIEW_TYPE = 0;
    private static final int PLACE_VIEW_TYPE = 1;
    private static final int PLACES_HEADER_VIEW_TYPE = 2;
    private static final int RESTAURANTS_HEADER_VIEW_TYPE = 3;

    private ObservableSortedList<Restaurant> restaurants;
    private ObservableSortedList<CachedPlace> places;

    private RestaurantsAdapter restaurantsAdapter;

    public void scrollRestaurantsToTop() {
        if (restaurantsAdapter != null) {
            restaurantsAdapter.scrollToTop();
        }
    }

    public SearchHistoryAdapter(ObservableSortedList<Restaurant> restaurants, ObservableSortedList<CachedPlace> places) {
        this.restaurants = restaurants;
        this.places = places;
        AdapterUtils.bindAdapterToItems(this, places, 3);
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return RESTAURANTS_HEADER_VIEW_TYPE;
            case 1:
                return RESTAURANTS_VIEW_TYPE;
            case 2:
                return PLACES_HEADER_VIEW_TYPE;
            default:
                return PLACE_VIEW_TYPE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        val inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case RESTAURANTS_VIEW_TYPE:
                PreviouslySearchedRestaurantsListBinding binding1 =
                        DataBindingUtil.inflate(inflater, R.layout.previously_searched_restaurants_list, parent, false);
                restaurantsAdapter = new RestaurantsAdapter(restaurants);
                return new RestaurantsViewHolder(binding1, restaurantsAdapter);
            case PLACE_VIEW_TYPE:
                PreviouslySearchedPlaceItemBinding binding2 =
                        DataBindingUtil.inflate(inflater, R.layout.previously_searched_place_item, parent, false);
                return new PlaceViewHolder(binding2);
            case PLACES_HEADER_VIEW_TYPE:
                PreviouslySearchedPlacesHeaderBinding binding3 =
                        DataBindingUtil.inflate(inflater, R.layout.previously_searched_places_header, parent, false);
                binding3.setViewModel(new PlacesHeaderViewModel(places));
                return new PlacesHeaderViewHolder(binding3);
            case RESTAURANTS_HEADER_VIEW_TYPE:
                PreviouslySearchedRestaurantsHeaderBinding binding4 =
                        DataBindingUtil.inflate(inflater, R.layout.previously_searched_restaurants_header, parent, false);
                binding4.setViewModel(new RestaurantsHeaderViewModel(restaurants));
                return new RestaurantsHeaderViewHolder(binding4);
        }

        throw new InvalidParameterException("Invalid viewType.");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PlaceViewHolder) {
            val hold = (PlaceViewHolder) holder;
            hold.binding.setPlace(places.get(position - 3));
        }
    }

    @Override
    public int getItemCount() {
        return places.size() + 3;
    }

    public static class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.RestaurantViewHolder> {

        private ObservableSortedList<Restaurant> restaurants;

        RestaurantsAdapter(ObservableSortedList<Restaurant> restaurants) {
            this.restaurants = restaurants;
            AdapterUtils.bindAdapterToItems(this, restaurants);
        }

        private RecyclerView recyclerView;

        void scrollToTop() {
            if (this.recyclerView != null) {
                this.recyclerView.scrollToPosition(0);
            }
        }

        @Override
        public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            this.recyclerView = recyclerView;
        }

        @NonNull
        @Override
        public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            val inflater = LayoutInflater.from(parent.getContext());
            PreviouslySearchedRestaurantItemBinding binding =
                    DataBindingUtil.inflate(inflater, R.layout.previously_searched_restaurant_item, parent, false);
            return new RestaurantViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
            holder.binding.setRestaurant(restaurants.get(position));
        }

        @Override
        public int getItemCount() {
            return restaurants.size();
        }

        static class RestaurantViewHolder extends RecyclerView.ViewHolder {

            private PreviouslySearchedRestaurantItemBinding binding;

            public PreviouslySearchedRestaurantItemBinding getBinding() {
                return binding;
            }

            RestaurantViewHolder(PreviouslySearchedRestaurantItemBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }

    private static class PlaceViewHolder extends RecyclerView.ViewHolder {

        private PreviouslySearchedPlaceItemBinding binding;

        PlaceViewHolder(PreviouslySearchedPlaceItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private static class RestaurantsViewHolder extends RecyclerView.ViewHolder {

        RestaurantsViewHolder(PreviouslySearchedRestaurantsListBinding binding, RestaurantsAdapter adapter) {
            super(binding.getRoot());
            binding.setAdapter(adapter);
            binding.previouslySearchedRestaurantsRecyclerView.setLayoutManager(new LinearLayoutManager(
                    binding.getRoot().getContext(),
                    LinearLayoutManager.HORIZONTAL,
                    false)
            );
        }
    }

    private static class PlacesHeaderViewHolder extends RecyclerView.ViewHolder {

        PlacesHeaderViewHolder(PreviouslySearchedPlacesHeaderBinding binding) {
            super(binding.getRoot());
        }
    }

    private static class RestaurantsHeaderViewHolder extends RecyclerView.ViewHolder {

        RestaurantsHeaderViewHolder(PreviouslySearchedRestaurantsHeaderBinding binding) {
            super(binding.getRoot());
        }
    }
}

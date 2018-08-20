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
import com.example.there.moviperfood.viper.common.OnListItemClickListener;

import java.security.InvalidParameterException;

import lombok.val;

public class SearchHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int RESTAURANTS_VIEW_TYPE = 0;
    private static final int PLACE_VIEW_TYPE = 1;
    private static final int PLACES_HEADER_VIEW_TYPE = 2;
    private static final int RESTAURANTS_HEADER_VIEW_TYPE = 3;

    private ObservableSortedList<Restaurant> restaurants;
    private ObservableSortedList<CachedPlace> places;
    private final OnListItemClickListener<Restaurant> onRestaurantSelectedListener;
    private final OnListItemClickListener<CachedPlace> onPlaceSelectedListener;

    private RestaurantsAdapter restaurantsAdapter;

    public void scrollRestaurantsToTop() {
        if (restaurantsAdapter != null) {
            restaurantsAdapter.scrollToTop();
        }
    }

    public SearchHistoryAdapter(ObservableSortedList<Restaurant> restaurants,
                                ObservableSortedList<CachedPlace> places,
                                OnListItemClickListener<Restaurant> onRestaurantSelectedListener,
                                OnListItemClickListener<CachedPlace> onPlaceSelectedListener) {
        this.restaurants = restaurants;
        this.places = places;
        this.onRestaurantSelectedListener = onRestaurantSelectedListener;
        this.onPlaceSelectedListener = onPlaceSelectedListener;
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
                PreviouslySearchedRestaurantsListBinding searchedRestaurantsListBinding =
                        DataBindingUtil.inflate(inflater, R.layout.previously_searched_restaurants_list, parent, false);
                restaurantsAdapter = new RestaurantsAdapter(restaurants, onRestaurantSelectedListener);
                return new RestaurantsViewHolder(searchedRestaurantsListBinding, restaurantsAdapter);
            case PLACE_VIEW_TYPE:
                PreviouslySearchedPlaceItemBinding searchedPlaceItemBinding =
                        DataBindingUtil.inflate(inflater, R.layout.previously_searched_place_item, parent, false);
                return new PlaceViewHolder(searchedPlaceItemBinding, onPlaceSelectedListener);
            case PLACES_HEADER_VIEW_TYPE:
                PreviouslySearchedPlacesHeaderBinding searchedPlacesHeaderBinding =
                        DataBindingUtil.inflate(inflater, R.layout.previously_searched_places_header, parent, false);
                searchedPlacesHeaderBinding.setViewModel(new PlacesHeaderViewModel(places));
                return new PlacesHeaderViewHolder(searchedPlacesHeaderBinding);
            case RESTAURANTS_HEADER_VIEW_TYPE:
                PreviouslySearchedRestaurantsHeaderBinding searchedRestaurantsHeaderBinding =
                        DataBindingUtil.inflate(inflater, R.layout.previously_searched_restaurants_header, parent, false);
                searchedRestaurantsHeaderBinding.setViewModel(new RestaurantsHeaderViewModel(restaurants));
                return new RestaurantsHeaderViewHolder(searchedRestaurantsHeaderBinding);
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
        private final OnListItemClickListener<Restaurant> onRestaurantSelectedListener;

        RestaurantsAdapter(ObservableSortedList<Restaurant> restaurants,
                           OnListItemClickListener<Restaurant> onRestaurantSelectedListener) {
            this.restaurants = restaurants;
            this.onRestaurantSelectedListener = onRestaurantSelectedListener;
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
            return new RestaurantViewHolder(binding, onRestaurantSelectedListener);
        }

        @Override
        public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
            val restaurant = restaurants.get(position);
            holder.binding.setRestaurant(restaurant);
            holder.binding.getRoot().setOnClickListener(v -> onRestaurantSelectedListener.onClick(restaurant));
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

            RestaurantViewHolder(PreviouslySearchedRestaurantItemBinding binding,
                                 OnListItemClickListener<Restaurant> onRestaurantSelectedListener) {
                super(binding.getRoot());
                this.binding = binding;

                binding.getRoot().setOnClickListener(v -> {
                    val restaurant = binding.getRestaurant();
                    if (restaurant != null) {
                        onRestaurantSelectedListener.onClick(restaurant);
                    }
                });
            }
        }
    }

    private static class PlaceViewHolder extends RecyclerView.ViewHolder {

        private PreviouslySearchedPlaceItemBinding binding;

        PlaceViewHolder(PreviouslySearchedPlaceItemBinding binding,
                        OnListItemClickListener<CachedPlace> onPlaceSelectedListener) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(v -> {
                val place = binding.getPlace();
                if (place != null) {
                    onPlaceSelectedListener.onClick(place);
                }
            });
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

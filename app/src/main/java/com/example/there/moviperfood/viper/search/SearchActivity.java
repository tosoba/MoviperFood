package com.example.there.moviperfood.viper.search;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;

import com.annimon.stream.Stream;
import com.example.there.moviperfood.R;
import com.example.there.moviperfood.data.food.restaurant.Restaurant;
import com.example.there.moviperfood.data.place.CachedPlace;
import com.example.there.moviperfood.databinding.ActivitySearchBinding;
import com.example.there.moviperfood.viper.search.list.SearchHistoryAdapter;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.mateuszkoslacz.moviper.base.view.activity.ViperActivity;

import java.util.Date;
import java.util.List;

import lombok.val;

public class SearchActivity
        extends ViperActivity<SearchContract.View, SearchContract.Presenter>
        implements SearchContract.View {

    private SearchViewModel searchViewModel = new SearchViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initSearchFragment();

        presenter.loadPreviouslySearchedPlaces();
        presenter.loadPreviouslySearchedRestaurants();
    }

    private SearchHistoryAdapter searchHistoryAdapter;

    private void initView() {
        ActivitySearchBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        searchHistoryAdapter = new SearchHistoryAdapter(searchViewModel.getRestaurants(), searchViewModel.getPlaces());
        binding.setSearchView(new SearchView(searchHistoryAdapter));
        binding.searchHistoryRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @NonNull
    @Override
    public SearchContract.Presenter createPresenter() {
        return new SearchPresenter();
    }

    private PlaceSelectionListener placeSelectionListener = new PlaceSelectionListener() {
        @Override
        public void onPlaceSelected(Place place) {
            presenter.startCuisinesActivity(place.getName().toString(), place.getLatLng());
            presenter.savePlace(new CachedPlace(
                    place.getId(),
                    place.getName().toString(),
                    place.getLatLng().latitude,
                    place.getLatLng().longitude,
                    new Date())
            );
        }

        @Override
        public void onError(Status status) {
        }
    };

    private void initSearchFragment() {
        PlaceAutocompleteFragment autocompleteFragment =
                (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.autocomplete_search_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(placeSelectionListener);
    }

    @Override
    public void updatePreviouslySearchedRestaurants(List<Restaurant> restaurants) {
        searchViewModel.getRestaurants().addAll(restaurants);
        searchHistoryAdapter.scrollRestaurantsToTop();
    }

    @Override
    public void updatePreviouslySearchedPlaces(List<CachedPlace> places) {
        if (places.size() < searchViewModel.getPlaces().size()) {
            val toRemove = Stream.of(searchViewModel.getPlaces())
                    .filter((displayedPlace) -> !places.contains(displayedPlace))
                    .toList();
            searchViewModel.getPlaces().removeAll(toRemove);
        } else {
            searchViewModel.getPlaces().addAll(places);
        }
    }
}

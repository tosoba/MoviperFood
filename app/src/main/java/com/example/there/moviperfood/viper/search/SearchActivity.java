package com.example.there.moviperfood.viper.search;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.there.moviperfood.R;
import com.example.there.moviperfood.data.cuisine.Cuisine;
import com.example.there.moviperfood.viper.search.cuisines.CuisinesAdapter;
import com.example.there.moviperfood.viper.search.cuisines.OnCuisineItemClickListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.mateuszkoslacz.moviper.base.view.activity.ViperActivity;
import com.example.there.moviperfood.databinding.ActivitySearchBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchActivity
        extends ViperActivity<SearchContract.View, SearchContract.Presenter>
        implements SearchContract.View {

    private CuisinesAdapter cuisinesAdapter;

    private static final String KEY_SEARCH_VIEW_MODEL = "KEY_SEARCH_VIEW_MODEL";
    private SearchViewModel searchViewModel = new SearchViewModel();

    private static final String KEY_LAST_PLACE_LAT_LNG = "KEY_LAST_PLACE_LAT_LNG";
    private LatLng lastPlaceLatLng;

    private static final String KEY_LAST_CUISINES = "KEY_LAST_CUISINES";
    private ArrayList<Cuisine> lastCuisines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySearchBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        binding.setViewModel(searchViewModel);

        initFromSavedState(savedInstanceState);
        initSearchFragment();
        initCuisinesRecyclerView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (lastPlaceLatLng != null) outState.putParcelable(KEY_LAST_PLACE_LAT_LNG, lastPlaceLatLng);
        if (searchViewModel != null) outState.putParcelable(KEY_SEARCH_VIEW_MODEL, searchViewModel);
        if (lastCuisines != null) outState.putParcelableArrayList(KEY_LAST_CUISINES, lastCuisines);
    }

    private void initFromSavedState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(KEY_LAST_PLACE_LAT_LNG)) lastPlaceLatLng = savedInstanceState.getParcelable(KEY_LAST_PLACE_LAT_LNG);
            if (savedInstanceState.containsKey(KEY_SEARCH_VIEW_MODEL)) searchViewModel = savedInstanceState.getParcelable(KEY_SEARCH_VIEW_MODEL);
            if (savedInstanceState.containsKey(KEY_LAST_CUISINES)) lastCuisines = savedInstanceState.getParcelableArrayList(KEY_LAST_CUISINES);

            initSearchFromSavedState();
        }
    }

    @SuppressWarnings("ConstantConditions")
    private void initSearchFromSavedState() {
        if (lastPlaceLatLng != null && searchViewModel.isLoading != null && searchViewModel.isLoading.get()) {
            presenter.loadCuisines(lastPlaceLatLng);
        }
    }

    @NonNull
    @Override
    public SearchContract.Presenter createPresenter() {
        return new SearchPresenter();
    }

    private PlaceSelectionListener placeSelectionListener = new PlaceSelectionListener() {
        @Override
        public void onPlaceSelected(Place place) {
            searchViewModel.isLoading.set(true);
            lastPlaceLatLng = place.getLatLng();
            presenter.loadCuisines(lastPlaceLatLng);
        }

        @Override
        public void onError(Status status) {

        }
    };

    private void initSearchFragment() {
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.autocomplete_search_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(placeSelectionListener);
    }

    private OnCuisineItemClickListener listener = new OnCuisineItemClickListener() {
        @Override
        public void onClick(Cuisine cuisine) {
            if (lastPlaceLatLng != null)
                presenter.startRestaurantsActivity(cuisine, lastPlaceLatLng);
                //presenter.loadRestaurants(lastPlaceLatLng, cuisine);
        }
    };

    private void initCuisinesRecyclerView() {
        RecyclerView cuisinesRecyclerView = findViewById(R.id.cuisines_recycler_view);
        cuisinesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        if (lastCuisines == null) {
            cuisinesAdapter = new CuisinesAdapter(Collections.emptyList(), listener);
        } else {
            cuisinesAdapter = new CuisinesAdapter(lastCuisines, listener);
        }
        cuisinesRecyclerView.setAdapter(cuisinesAdapter);
    }

    @Override
    public void updateCuisines(List<Cuisine> cuisines) {
        searchViewModel.isLoading.set(false);
        lastCuisines = new ArrayList<>(cuisines);
        cuisinesAdapter.setCuisines(cuisines);
    }
}

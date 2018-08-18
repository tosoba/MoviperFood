package com.example.there.moviperfood.viper.search;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.there.moviperfood.R;
import com.example.there.moviperfood.data.food.cuisine.Cuisine;
import com.example.there.moviperfood.databinding.ActivitySearchBinding;
import com.example.there.moviperfood.viper.search.cuisines.CuisinesAdapter;
import com.example.there.moviperfood.viper.search.cuisines.OnCuisineItemClickListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.mateuszkoslacz.moviper.base.view.activity.ViperActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.val;

public class SearchActivity
        extends ViperActivity<SearchContract.View, SearchContract.Presenter>
        implements SearchContract.View {

    private CuisinesAdapter cuisinesAdapter;

    private static final String KEY_SEARCH_VIEW_MODEL = "KEY_SEARCH_VIEW_MODEL";
    private SearchViewModel searchViewModel = new SearchViewModel();

    private static final String KEY_LAST_PLACE_LAT_LNG = "KEY_LAST_PLACE_LAT_LNG";
    private LatLng lastPlaceLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initFromSavedState(savedInstanceState);
        initView();
        initSearchFragment();
        initCuisinesRecyclerView();
    }

    private void initView() {
        ActivitySearchBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        binding.setViewModel(searchViewModel);
        setSupportActionBar(binding.mainToolbar);
        if (!searchViewModel.lastCuisines.get().isEmpty()) addBackNavigationToToolbar();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (lastPlaceLatLng != null)
            outState.putParcelable(KEY_LAST_PLACE_LAT_LNG, lastPlaceLatLng);
        if (searchViewModel != null)
            outState.putParcelable(KEY_SEARCH_VIEW_MODEL, searchViewModel);
    }

    private void initFromSavedState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(KEY_LAST_PLACE_LAT_LNG))
                lastPlaceLatLng = savedInstanceState.getParcelable(KEY_LAST_PLACE_LAT_LNG);
            if (savedInstanceState.containsKey(KEY_SEARCH_VIEW_MODEL)) {
                searchViewModel = savedInstanceState.getParcelable(KEY_SEARCH_VIEW_MODEL);
            }

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
        PlaceAutocompleteFragment autocompleteFragment =
                (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.autocomplete_search_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(placeSelectionListener);
    }

    private OnCuisineItemClickListener listener = new OnCuisineItemClickListener() {
        @Override
        public void onClick(Cuisine cuisine) {
            if (lastPlaceLatLng != null)
                presenter.startRestaurantsActivity(cuisine, lastPlaceLatLng);
        }
    };

    private void initCuisinesRecyclerView() {
        RecyclerView cuisinesRecyclerView = findViewById(R.id.cuisines_recycler_view);
        val layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        cuisinesRecyclerView.setLayoutManager(layoutManager);
        cuisinesAdapter = new CuisinesAdapter(searchViewModel.lastCuisines.get(), listener);
        cuisinesRecyclerView.setAdapter(cuisinesAdapter);
        cuisinesRecyclerView.addItemDecoration(new DividerItemDecoration(cuisinesRecyclerView.getContext(), layoutManager.getOrientation()));
    }

    @Override
    public void updateCuisines(List<Cuisine> cuisines) {
        searchViewModel.isLoading.set(false);
        addBackNavigationToToolbar();
        searchViewModel.lastCuisines.set(new ArrayList<>(cuisines));
        cuisinesAdapter.setCuisines(cuisines);
    }

    private void addBackNavigationToToolbar() {
        Toolbar mainToolbar = findViewById(R.id.main_toolbar);
        mainToolbar.setNavigationIcon(R.drawable.arrow_back_borderless);
        mainToolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void removeBackNavigationFromToolbar() {
        Toolbar mainToolbar = findViewById(R.id.main_toolbar);
        mainToolbar.setNavigationIcon(null);
    }

    @Override
    public void onBackPressed() {
        val lastCuisines = searchViewModel.lastCuisines.get();
        if (lastCuisines != null && !lastCuisines.isEmpty()) {
            searchViewModel.lastCuisines.set(Collections.emptyList());
            cuisinesAdapter.setCuisines(Collections.emptyList());
            removeBackNavigationFromToolbar();
        } else {
            super.onBackPressed();
        }
    }
}

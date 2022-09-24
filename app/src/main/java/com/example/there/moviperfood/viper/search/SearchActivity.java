package com.example.there.moviperfood.viper.search;

import android.Manifest;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageButton;

import com.afollestad.materialdialogs.MaterialDialog;
import com.annimon.stream.Stream;
import com.example.there.moviperfood.R;
import com.example.there.moviperfood.data.food.restaurant.Restaurant;
import com.example.there.moviperfood.data.place.CachedPlace;
import com.example.there.moviperfood.databinding.ActivitySearchBinding;
import com.example.there.moviperfood.lifecycle.ConnectivityComponent;
import com.example.there.moviperfood.util.LocationUtils;
import com.example.there.moviperfood.util.MeasurementUtils;
import com.example.there.moviperfood.util.ViewUtils;
import com.example.there.moviperfood.viper.search.list.SearchHistoryAdapter;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.mateuszkoslacz.moviper.base.view.activity.ViperActivity;

import java.util.Date;

import io.nlopez.smartlocation.SmartLocation;
import lombok.val;

public class SearchActivity
        extends ViperActivity<SearchContract.View, SearchContract.Presenter>
        implements SearchContract.View {

    private SearchViewModel searchViewModel = new SearchViewModel();
    private ConnectivityComponent connectivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initSearchFragment();

        connectivityComponent = new ConnectivityComponent(this, true, findViewById(R.id.search_root_layout), null);
        getLifecycle().addObserver(connectivityComponent);

        setupObservers();

        presenter.loadPreviouslySearchedPlaces();
        presenter.loadPreviouslySearchedRestaurants();
    }

    private void setupObservers() {
        presenter.getPlaces().observe(this, cachedPlaces -> {
            if (cachedPlaces != null) {
                if (cachedPlaces.size() < searchViewModel.getPlaces().size()) {
                    val toRemove = Stream.of(searchViewModel.getPlaces())
                            .filter((displayedPlace) -> !cachedPlaces.contains(displayedPlace))
                            .toList();
                    searchViewModel.getPlaces().removeAll(toRemove);
                } else {
                    searchViewModel.getPlaces().addAll(cachedPlaces);
                }
            }
        });

        presenter.getRestaurants().observe(this, restaurants -> {
            if (restaurants != null) {
                searchViewModel.getRestaurants().addAll(restaurants);
                searchHistoryAdapter.scrollRestaurantsToTop();
            }
        });
    }

    private SearchHistoryAdapter searchHistoryAdapter;

    private void initView() {
        ActivitySearchBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        searchHistoryAdapter = new SearchHistoryAdapter(
                searchViewModel.getRestaurants(),
                searchViewModel.getPlaces(),
                (Restaurant item) -> connectivityComponent.checkConnectivityStatusAndRun(() -> presenter.startReviewsActivity(item)),
                (CachedPlace item) -> connectivityComponent.checkConnectivityStatusAndRun(() -> presenter.startCuisinesActivity(item.getName(), item.getLatLng()))
        );
        binding.setSearchView(new SearchView(
                searchHistoryAdapter,
                v -> handleOnNearbyClick()
        ));
        binding.searchHistoryRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        val buttonViewTreeObserver = binding.nearMeBtn.getViewTreeObserver();
        buttonViewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                binding.nearMeBtn.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int btnHeight = binding.nearMeBtn.getMeasuredHeight();
                val recyclerViewMarginBottom = btnHeight + MeasurementUtils.convertDpToPixel(10, SearchActivity.this);
                ViewUtils.setMargins(binding.searchHistoryRecyclerView, 0, 0, 0, (int) recyclerViewMarginBottom);
            }
        });
    }

    private void handleOnNearbyClick() {
        Dexter.withActivity(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    private void showLocationSnackbar(String message) {
                        val snackbar = Snackbar.make(findViewById(R.id.search_root_layout), message, Snackbar.LENGTH_LONG)
                                .setAction(getString(R.string.settings), v -> {
                                    val settingsIntent = new Intent(Settings.ACTION_SETTINGS);
                                    startActivity(settingsIntent);
                                })
                                .setActionTextColor(ContextCompat.getColor(SearchActivity.this, R.color.colorAccent));
                        snackbar.setDuration(BaseTransientBottomBar.LENGTH_LONG);
                        snackbar.show();
                    }

                    private void showEnableLocationDialog() {
                        new AlertDialog.Builder(SearchActivity.this)
                                .setMessage(getString(R.string.location_not_enabled))
                                .setPositiveButton(getString(R.string.settings), (dialog, which) -> {
                                    val settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    SearchActivity.this.startActivity(settingsIntent);
                                })
                                .show();
                    }

                    private void onLocationEnabled() {
                        val locationControl = SmartLocation.with(SearchActivity.this).location().oneFix();

                        val progressDialog = new MaterialDialog.Builder(SearchActivity.this)
                                .title(R.string.fetching_location)
                                .negativeText("Cancel")
                                .onNegative((dialog, which) -> {
                                    locationControl.stop();
                                    dialog.dismiss();
                                })
                                .content(R.string.please_wait)
                                .progress(true, 0)
                                .show();

                        locationControl.start(location -> {
                            connectivityComponent.checkConnectivityStatusAndRun(() -> presenter.startCuisinesActivity(
                                    getString(R.string.your_location),
                                    new LatLng(location.getLatitude(), location.getLongitude())
                            ));
                            progressDialog.dismiss();
                        });
                    }

                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        if (LocationUtils.isLocationEnabled(SearchActivity.this)) {
                            onLocationEnabled();
                        } else {
                            showEnableLocationDialog();
                        }
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        showLocationSnackbar(getString(R.string.location_permission_needed));
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .check();
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

        val view = autocompleteFragment.getView();
        if (view != null) {
            val fragmentBackgroundColor = ContextCompat.getColor(this, R.color.semiTransparentWhite);

            EditText autoCompleteEditText = view.findViewById(R.id.place_autocomplete_search_input);
            autoCompleteEditText.setBackgroundColor(fragmentBackgroundColor);
            autoCompleteEditText.setHintTextColor(Color.BLACK);
            autoCompleteEditText.setHint("Nearby restaurants search...");
            autoCompleteEditText.setTextSize(18);

            ImageButton searchButton = view.findViewById(R.id.place_autocomplete_search_button);
            searchButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.search));
            searchButton.setBackgroundColor(fragmentBackgroundColor);

            ImageButton clearButton = view.findViewById(R.id.place_autocomplete_clear_button);
            clearButton.setBackgroundColor(fragmentBackgroundColor);
        }
    }
}

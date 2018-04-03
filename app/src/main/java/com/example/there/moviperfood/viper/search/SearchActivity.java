package com.example.there.moviperfood.viper.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.there.moviperfood.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.mateuszkoslacz.moviper.base.view.activity.ViperActivity;

import dagger.android.AndroidInjection;

public class SearchActivity
        extends ViperActivity<SearchContract.View, SearchContract.Presenter>
        implements SearchContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initSearchFragment();
    }

    @NonNull
    @Override
    public SearchContract.Presenter createPresenter() {
        return new SearchPresenter();
    }

    private PlaceSelectionListener placeSelectionListener = new PlaceSelectionListener() {
        @Override
        public void onPlaceSelected(Place place) {

        }

        @Override
        public void onError(Status status) {

        }
    };

    private void initSearchFragment() {
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.autocomplete_search_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(placeSelectionListener);
    }
}

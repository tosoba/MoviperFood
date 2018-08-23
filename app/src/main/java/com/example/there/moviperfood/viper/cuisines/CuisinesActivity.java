package com.example.there.moviperfood.viper.cuisines;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.there.moviperfood.R;
import com.example.there.moviperfood.data.food.cuisine.Cuisine;
import com.example.there.moviperfood.databinding.ActivityCuisinesBinding;
import com.example.there.moviperfood.lifecycle.ConnectivityComponent;
import com.example.there.moviperfood.util.ActivityUtils;
import com.example.there.moviperfood.viper.common.OnListItemClickListener;
import com.example.there.moviperfood.viper.cuisines.list.CuisinesAdapter;
import com.google.android.gms.maps.model.LatLng;
import com.mateuszkoslacz.moviper.base.view.activity.ViperActivity;
import com.mateuszkoslacz.moviper.presentersdispatcher.MoviperPresentersDispatcher;

import java.util.List;
import java.util.Objects;

import lombok.val;

public class CuisinesActivity
        extends ViperActivity<CuisinesContract.View, CuisinesContract.Presenter>
        implements CuisinesContract.View {

    private static final String KEY_CUISINES_VIEW_MODEL = "KEY_CUISINES_VIEW_MODEL";
    private CuisinesViewModel cuisinesViewModel = new CuisinesViewModel();

    private LatLng placeLatLng;
    private String placeName;

    private ConnectivityComponent connectivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initExtras();
        initFromSavedState(savedInstanceState);
        initView();
        ActivityUtils.setHomeButtonEnabled(this, R.drawable.arrow_back_borderless);

        connectivityComponent = new ConnectivityComponent(
                this,
                !cuisinesViewModel.getCuisines().isEmpty(),
                findViewById(R.id.cuisines_root_layout),
                this::loadCuisines);
        getLifecycle().addObserver(connectivityComponent);

        if (savedInstanceState == null) {
            loadCuisines();
        }
    }

    private CuisinesAdapter adapter;

    private void initView() {
        adapter = new CuisinesAdapter(placeName, cuisinesViewModel.getCuisines(), onCuisineSelectedListener);
        ActivityCuisinesBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_cuisines);
        binding.setCuisinesView(new CuisinesView(
                cuisinesViewModel,
                adapter,
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)));
        binding.cuisinesRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        val filterText = cuisinesViewModel.getFilterText().get();
        if (!Objects.requireNonNull(filterText).isEmpty()) {
            adapter.getFilter().filter(filterText);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initExtras() {
        val intent = getIntent();
        placeLatLng = intent.getParcelableExtra(EXTRA_PLACE_LAT_LNG);
        placeName = intent.getStringExtra(EXTRA_PLACE_NAME);
    }

    @NonNull
    @Override
    public CuisinesContract.Presenter createPresenter() {
        return (CuisinesContract.Presenter) MoviperPresentersDispatcher.getInstance().getPresenterForView(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (cuisinesViewModel != null) {
            cuisinesViewModel.getCuisines().addAll(adapter.getRemovedItems());
            outState.putParcelable(KEY_CUISINES_VIEW_MODEL, cuisinesViewModel);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.getFilter().filter(cuisinesViewModel.getFilterText().get());
        }
    }

    @SuppressWarnings("ConstantConditions")
    private void initFromSavedState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(KEY_CUISINES_VIEW_MODEL)) {
                cuisinesViewModel = savedInstanceState.getParcelable(KEY_CUISINES_VIEW_MODEL);
            }

            if (cuisinesViewModel.getCuisines().isEmpty()) {
                loadCuisines();
            }
        }
    }

    private void loadCuisines() {
        cuisinesViewModel.getIsLoading().set(true);
        presenter.loadCuisines(placeLatLng);
    }

    private OnListItemClickListener<Cuisine> onCuisineSelectedListener = (Cuisine item) -> {
        if (connectivityComponent.getLastConnectionStatus()) {
            presenter.startRestaurantsActivity(item, placeLatLng);
        } else {
            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void updateCuisines(List<Cuisine> cuisines) {
        cuisinesViewModel.getIsLoading().set(false);
        cuisinesViewModel.getCuisines().addAll(cuisines);
    }

    @Override
    public void onNoRestaurantsFound() {
        presenter.deleteMostRecentlyAddedPlace();
        Toast.makeText(this, getString(R.string.no_restaurants_found), Toast.LENGTH_LONG).show();
        finish();
    }

    private static final String EXTRA_PLACE_LAT_LNG = "EXTRA_PLACE_LAT_LNG";
    private static final String EXTRA_PLACE_NAME = "EXTRA_PLACE_NAME";

    public static Intent startingIntent(Context context, LatLng placeLatLng, String placeName) {
        val intent = new Intent(context, CuisinesActivity.class);
        intent.putExtra(EXTRA_PLACE_LAT_LNG, placeLatLng);
        intent.putExtra(EXTRA_PLACE_NAME, placeName);
        return intent;
    }
}

package com.example.there.moviperfood.viper.cuisines;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.there.moviperfood.R;
import com.example.there.moviperfood.data.food.cuisine.Cuisine;
import com.example.there.moviperfood.databinding.ActivityCuisinesBinding;
import com.example.there.moviperfood.util.ActivityUtils;
import com.example.there.moviperfood.viper.search.cuisines.CuisinesAdapter;
import com.example.there.moviperfood.viper.search.cuisines.OnCuisineItemClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.mateuszkoslacz.moviper.base.view.activity.ViperActivity;
import com.mateuszkoslacz.moviper.presentersdispatcher.MoviperPresentersDispatcher;

import java.util.ArrayList;
import java.util.List;

import lombok.val;

public class CuisinesActivity
        extends ViperActivity<CuisinesContract.View, CuisinesContract.Presenter>
        implements CuisinesContract.View {

    private CuisinesAdapter cuisinesAdapter;

    private static final String KEY_CUISINES_VIEW_MODEL = "KEY_CUISINES_VIEW_MODEL";
    private CuisinesViewModel cuisinesViewModel = new CuisinesViewModel();

    private LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        ActivityUtils.setHomeButtonEnabled(this, R.drawable.arrow_back_borderless);
        initExtras();
        initFromSavedState(savedInstanceState);
        initCuisinesRecyclerView();

        if (savedInstanceState == null) {
            cuisinesViewModel.isLoading.set(true);
            presenter.loadCuisines(latLng);
        }
    }

    private void initView() {
        ActivityCuisinesBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_cuisines);
        binding.setViewModel(cuisinesViewModel);
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
        latLng = getIntent().getParcelableExtra(EXTRA_LAT_LNG);
    }

    @NonNull
    @Override
    public CuisinesContract.Presenter createPresenter() {
        return (CuisinesContract.Presenter) MoviperPresentersDispatcher.getInstance().getPresenterForView(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (cuisinesViewModel != null)
            outState.putParcelable(KEY_CUISINES_VIEW_MODEL, cuisinesViewModel);
    }

    private void initFromSavedState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(KEY_CUISINES_VIEW_MODEL)) {
                cuisinesViewModel = savedInstanceState.getParcelable(KEY_CUISINES_VIEW_MODEL);
            }

            initSearchFromSavedState();
        }
    }

    @SuppressWarnings("ConstantConditions")
    private void initSearchFromSavedState() {
        if (cuisinesViewModel.isLoading != null && cuisinesViewModel.isLoading.get()) {
            presenter.loadCuisines(latLng);
        }
    }

    private OnCuisineItemClickListener listener = new OnCuisineItemClickListener() {
        @Override
        public void onClick(Cuisine cuisine) {
            presenter.startRestaurantsActivity(cuisine, latLng);
        }
    };

    private void initCuisinesRecyclerView() {
        RecyclerView cuisinesRecyclerView = findViewById(R.id.cuisines_recycler_view);
        val layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        cuisinesRecyclerView.setLayoutManager(layoutManager);
        cuisinesAdapter = new CuisinesAdapter(cuisinesViewModel.lastCuisines.get(), listener);
        cuisinesRecyclerView.setAdapter(cuisinesAdapter);
        cuisinesRecyclerView.addItemDecoration(new DividerItemDecoration(cuisinesRecyclerView.getContext(), layoutManager.getOrientation()));
    }

    @Override
    public void updateCuisines(List<Cuisine> cuisines) {
        cuisinesViewModel.isLoading.set(false);
        cuisinesViewModel.lastCuisines.set(new ArrayList<>(cuisines));
        cuisinesAdapter.setCuisines(cuisines);
    }

    private static final String EXTRA_LAT_LNG = "EXTRA_LAT_LNG";

    public static Intent startingIntent(Context context, LatLng latLng) {
        val intent = new Intent(context, CuisinesActivity.class);
        intent.putExtra(EXTRA_LAT_LNG, latLng);
        return intent;
    }
}

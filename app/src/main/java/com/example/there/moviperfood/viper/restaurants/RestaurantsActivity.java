package com.example.there.moviperfood.viper.restaurants;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.example.there.moviperfood.R;
import com.example.there.moviperfood.data.food.cuisine.Cuisine;
import com.example.there.moviperfood.data.food.restaurant.Restaurant;
import com.example.there.moviperfood.databinding.ActivityRestaurantsBinding;
import com.example.there.moviperfood.lifecycle.ConnectivityComponent;
import com.example.there.moviperfood.util.ActivityUtils;
import com.example.there.moviperfood.viper.restaurants.fragment.RestaurantsCurrentFragment;
import com.example.there.moviperfood.viper.restaurants.fragment.RestaurantsFragment;
import com.example.there.moviperfood.viper.restaurants.fragment.RestaurantsFragmentInteractionListener;
import com.example.there.moviperfood.viper.restaurants.fragment.list.RestaurantsListFragment;
import com.example.there.moviperfood.viper.restaurants.fragment.map.RestaurantsMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.mateuszkoslacz.moviper.base.view.activity.ViperActivity;
import com.mateuszkoslacz.moviper.presentersdispatcher.MoviperPresentersDispatcher;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.val;

public class RestaurantsActivity
        extends ViperActivity<RestaurantsContract.View, RestaurantsContract.Presenter>
        implements RestaurantsContract.View, RestaurantsFragmentInteractionListener {

    private static final String KEY_CURRENT_FRAGMENT = "KEY_CURRENT_FRAGMENT";
    private RestaurantsCurrentFragment currentFragment = RestaurantsCurrentFragment.LIST;

    private Cuisine cuisine;
    private LatLng latLng;

    private static final String KEY_RESTAURANTS = "KEY_RESTAURANTS";
    private ArrayList<Restaurant> restaurants;

    private ConnectivityComponent connectivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initExtras();
        initView();

        connectivityComponent = new ConnectivityComponent(
                this,
                restaurants != null && !restaurants.isEmpty(),
                findViewById(R.id.restaurants_root_layout),
                () -> presenter.loadRestaurants(latLng, cuisine),
                true);
        getLifecycle().addObserver(connectivityComponent);

        setupObservers();

        initFromSavedState(savedInstanceState);
        showFragment();
    }

    private void initView() {
        ActivityRestaurantsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_restaurants);
        binding.setRestaurantsView(new RestaurantsView(onNavigationItemSelectedListener));
        ActivityUtils.setHomeButtonEnabled(this, R.drawable.arrow_back_borderless);
        setTitle(cuisine.getCuisineName());
    }

    private void setupObservers() {
        presenter.getRestaurants().observe(this, restaurants -> {
            if (restaurants != null) {
                this.restaurants = new ArrayList<>(restaurants);
                passRestaurantsToFragment(restaurants);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_CURRENT_FRAGMENT, currentFragment);
        if (restaurants != null) outState.putParcelableArrayList(KEY_RESTAURANTS, restaurants);
    }

    private void initFromSavedState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            currentFragment = (RestaurantsCurrentFragment) savedInstanceState.getSerializable(KEY_CURRENT_FRAGMENT);
            if (savedInstanceState.containsKey(KEY_RESTAURANTS)) {
                restaurants = savedInstanceState.getParcelableArrayList(KEY_RESTAURANTS);
            } else {
                presenter.loadRestaurants(latLng, cuisine);
            }
        } else {
            presenter.loadRestaurants(latLng, cuisine);
        }
    }

    private void initExtras() {
        cuisine = getIntent().getParcelableExtra(EXTRA_CUISINE);
        latLng = getIntent().getParcelableExtra(EXTRA_LAT_LNG);
    }

    private void passRestaurantsToFragment(List<Restaurant> restaurants) {
        val showingFragment = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_RESTAURANTS);
        if (showingFragment != null) {
            ((RestaurantsFragment) showingFragment).setRestaurants(restaurants);
        }
    }

    private static final String TAG_FRAGMENT_RESTAURANTS = "RESTAURANTS_FRAGMENT_TAG";

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = item -> {
        Fragment selectedFragment = null;
        switch (item.getItemId()) {
            case R.id.navigation_list:
                if (currentFragment == RestaurantsCurrentFragment.LIST) return false;
                selectedFragment = RestaurantsListFragment.newInstance(restaurants);
                currentFragment = RestaurantsCurrentFragment.LIST;
                break;
            case R.id.navigation_map:
                if (currentFragment == RestaurantsCurrentFragment.MAP) return false;
                selectedFragment = RestaurantsMapFragment.newInstance(restaurants);
                currentFragment = RestaurantsCurrentFragment.MAP;
                break;
        }

        val transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_layout, selectedFragment, TAG_FRAGMENT_RESTAURANTS);
        transaction.commit();

        return true;
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showFragment() {
        val transaction = getSupportFragmentManager().beginTransaction();
        val showingFragment = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_RESTAURANTS);
        switch (currentFragment) {
            case LIST:
                if (showingFragment instanceof RestaurantsListFragment) return;
                transaction.replace(R.id.fragment_container_layout, RestaurantsListFragment.newInstance(restaurants), TAG_FRAGMENT_RESTAURANTS);
                break;
            case MAP:
                if (showingFragment instanceof RestaurantsMapFragment) return;
                transaction.replace(R.id.fragment_container_layout, RestaurantsMapFragment.newInstance(restaurants), TAG_FRAGMENT_RESTAURANTS);
                break;
        }
        transaction.commit();
    }

    @NonNull
    @Override
    public RestaurantsContract.Presenter createPresenter() {
        return (RestaurantsContract.Presenter) MoviperPresentersDispatcher.getInstance().getPresenterForView(this);
    }

    @Override
    public void onRestaurantSelected(Restaurant restaurant) {
        connectivityComponent.checkConnectivityStatusAndRun(() -> presenter.startReviewsActivity(restaurant));
        restaurant.setLastSearched(new Date());
        presenter.saveRestaurant(restaurant);
    }

    @Override
    public void onShowOnMapClicked(Restaurant restaurant) {
        presenter.startMapActivity(restaurant);
    }

    public interface ShowsOnMap {
        void onShowOnMapClicked(Restaurant restaurant);
    }

    private static final String EXTRA_CUISINE = "EXTRA_CUISINE";
    private static final String EXTRA_LAT_LNG = "EXTRA_LAT_LNG";

    public static Intent startingIntent(Context context, Cuisine cuisine, LatLng latLng) {
        val intent = new Intent(context, RestaurantsActivity.class);
        intent.putExtra(EXTRA_LAT_LNG, latLng);
        intent.putExtra(EXTRA_CUISINE, cuisine);
        return intent;
    }
}

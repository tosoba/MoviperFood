package com.example.there.moviperfood.viper.restaurants;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;

import com.example.there.moviperfood.R;
import com.example.there.moviperfood.data.cuisine.Cuisine;
import com.example.there.moviperfood.data.restaurant.Restaurant;
import com.example.there.moviperfood.viper.restaurants.fragment.RestaurantsCurrentFragment;
import com.example.there.moviperfood.viper.restaurants.fragment.RestaurantsFragment;
import com.example.there.moviperfood.viper.restaurants.fragment.RestaurantsFragmentInteractionListener;
import com.example.there.moviperfood.viper.restaurants.fragment.list.RestaurantsListFragment;
import com.example.there.moviperfood.viper.restaurants.fragment.map.RestaurantsMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.mateuszkoslacz.moviper.base.view.activity.ViperActivity;
import com.mateuszkoslacz.moviper.presentersdispatcher.MoviperPresentersDispatcher;

import java.util.ArrayList;
import java.util.List;

import lombok.val;

public class RestaurantsActivity
        extends ViperActivity<RestaurantsContract.View, RestaurantsContract.Presenter>
        implements RestaurantsContract.View, RestaurantsFragmentInteractionListener {

    private static final String KEY_CURRENT_FRAGMENT = "KEY_CURRENT_FRAGMENT";
    private RestaurantsCurrentFragment currentFragment = RestaurantsCurrentFragment.LIST;

    // EXTRAS
    private Cuisine cuisine;
    private LatLng latLng;

    private static final String KEY_RESTAURANTS = "KEY_RESTAURANTS";
    private ArrayList<Restaurant> restaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);

        initBottomNavigation();
        initExtras();
        setTitle(cuisine.getCuisineName());
        initFromSavedState(savedInstanceState);
        showFragment();
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

    @Override
    public void updateRestaurants(List<Restaurant> restaurants) {
        this.restaurants = new ArrayList<>(restaurants);
        passRestaurantsToFragment(restaurants);
    }

    private void passRestaurantsToFragment(List<Restaurant> restaurants) {
        val showingFragment = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_RESTAURANTS);
        if (showingFragment != null) {
            ((RestaurantsFragment) showingFragment).setRestaurants(restaurants);
        }
    }

    private static final String TAG_FRAGMENT_RESTAURANTS = "RESTAURANTS_FRAGMENT_TAG";

    private BottomNavigationView.OnNavigationItemSelectedListener navigationListener = item -> {
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

    private void initBottomNavigation() {
        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(navigationListener);
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
        presenter.startRestaurantActivity(restaurant);
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

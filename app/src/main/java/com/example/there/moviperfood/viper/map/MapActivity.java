package com.example.there.moviperfood.viper.map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.there.moviperfood.R;
import com.example.there.moviperfood.data.food.restaurant.Restaurant;
import com.example.there.moviperfood.util.ActivityUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;

import lombok.val;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ActivityUtils.setHomeButtonEnabled(this, R.drawable.arrow_back_borderless);

        initExtras();
        initMap();
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


    private Restaurant restaurant;

    private void initExtras() {
        restaurant = getIntent().getParcelableExtra(EXTRA_RESTAURANT);
    }

    private GoogleMap map;

    private OnMapReadyCallback onMapReady = googleMap -> {
        map = googleMap;
        addRestaurantToMap();
    };

    private void addRestaurantToMap() {
        val restaurantLatLng = restaurant.getLocation().getLatLng();
        map.addMarker(new MarkerOptions().title(restaurant.getName())
                .position(restaurantLatLng));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(restaurantLatLng, 15f));
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(onMapReady);
    }

    private static String EXTRA_RESTAURANT = "EXTRA_RESTAURANT";

    public static Intent startingIntent(Context context, Restaurant restaurant) {
        val intent = new Intent(context, MapActivity.class);
        intent.putExtra(EXTRA_RESTAURANT, restaurant);
        return intent;
    }
}

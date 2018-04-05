package com.example.there.moviperfood.viper.restaurants;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.example.there.moviperfood.R;
import com.example.there.moviperfood.data.cuisine.Cuisine;
import com.google.android.gms.maps.model.LatLng;
import com.mateuszkoslacz.moviper.base.view.activity.ViperActivity;
import com.mateuszkoslacz.moviper.iface.presenter.ViperPresenter;
import com.mateuszkoslacz.moviper.presentersdispatcher.MoviperPresentersDispatcher;

import lombok.val;

public class RestaurantsActivity
        extends ViperActivity<RestaurantsContract.View, RestaurantsContract.Presenter>
        implements RestaurantsContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);

        Cuisine cuisine = getIntent().getParcelableExtra(EXTRA_CUISINE);
        LatLng latLng = getIntent().getParcelableExtra(EXTRA_LAT_LNG);
        presenter.loadRestaurants(latLng, cuisine);
    }

    @NonNull
    @Override
    public RestaurantsContract.Presenter createPresenter() {
        return (RestaurantsContract.Presenter) MoviperPresentersDispatcher.getInstance().getPresenterForView(this);
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

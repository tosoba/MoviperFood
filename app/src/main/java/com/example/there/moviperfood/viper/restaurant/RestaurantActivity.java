package com.example.there.moviperfood.viper.restaurant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.example.there.moviperfood.R;
import com.example.there.moviperfood.data.restaurant.Restaurant;
import com.mateuszkoslacz.moviper.base.view.activity.ViperActivity;
import com.mateuszkoslacz.moviper.presentersdispatcher.MoviperPresentersDispatcher;

import lombok.val;

public class RestaurantActivity
        extends ViperActivity<RestaurantContract.View, RestaurantContract.Presenter>
        implements RestaurantContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        presenter.loadReviews(getIntent().getParcelableExtra(EXTRA_RESTAURANT));
    }

    @NonNull
    @Override
    public RestaurantContract.Presenter createPresenter() {
        return (RestaurantContract.Presenter) MoviperPresentersDispatcher.getInstance().getPresenterForView(this);
    }

    private static final String EXTRA_RESTAURANT = "EXTRA_RESTAURANT";

    public static Intent startingIntent(Context context, Restaurant restaurant) {
        val intent = new Intent(context, RestaurantActivity.class);
        intent.putExtra(EXTRA_RESTAURANT, restaurant);
        return intent;
    }
}

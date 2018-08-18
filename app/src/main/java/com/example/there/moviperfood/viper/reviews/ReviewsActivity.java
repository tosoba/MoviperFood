package com.example.there.moviperfood.viper.reviews;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.there.moviperfood.R;
import com.example.there.moviperfood.data.food.restaurant.Restaurant;
import com.example.there.moviperfood.data.food.review.Review;
import com.example.there.moviperfood.databinding.ActivityReviewsBinding;
import com.example.there.moviperfood.viper.reviews.list.ReviewsListAdapter;
import com.mateuszkoslacz.moviper.base.view.activity.ViperActivity;
import com.mateuszkoslacz.moviper.presentersdispatcher.MoviperPresentersDispatcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.val;

public class ReviewsActivity
        extends ViperActivity<ReviewsContract.View, ReviewsContract.Presenter>
        implements ReviewsContract.View {

    private Restaurant restaurant;

    private static final String KEY_REVIEWS = "KEY_REVIEWS";
    private ArrayList<Review> reviews;

    private ReviewsListAdapter reviewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initExtras();
        initBinding();
        initTitle();

        initFromSavedState(savedInstanceState);
        initToolbar();
        initReviewsRecyclerView();
    }

    private void initExtras() {
        restaurant = getIntent().getParcelableExtra(EXTRA_RESTAURANT);
    }

    private void initBinding() {
        ActivityReviewsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_reviews);
        binding.setRestaurant(restaurant);
    }

    private void initTitle() {
        CollapsingToolbarLayout toolbarLayout = findViewById(R.id.restaurant_toolbar_layout);
        toolbarLayout.setTitle(restaurant.getName());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (reviews != null) outState.putParcelableArrayList(KEY_REVIEWS, reviews);
    }

    private void initFromSavedState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(KEY_REVIEWS)) {
                reviews = savedInstanceState.getParcelableArrayList(KEY_REVIEWS);
            } else {
                presenter.loadReviews(restaurant);
            }
        } else {
            presenter.loadReviews(restaurant);
        }
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.restaurant_toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_back);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    private void initReviewsRecyclerView() {
        RecyclerView reviewsRecyclerView = findViewById(R.id.restaurant_reviews_recycler_view);
        val layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        reviewsRecyclerView.setLayoutManager(layoutManager);
        if (reviews == null) {
            reviewsAdapter = new ReviewsListAdapter(Collections.emptyList());
        } else {
            reviewsAdapter = new ReviewsListAdapter(reviews);
        }
        reviewsRecyclerView.setAdapter(reviewsAdapter);
        reviewsRecyclerView.addItemDecoration(new DividerItemDecoration(reviewsRecyclerView.getContext(), layoutManager.getOrientation()));
    }

    @Override
    public void updateReviews(List<Review> reviews) {
        this.reviews = new ArrayList<>(reviews);
        if (reviewsAdapter != null) reviewsAdapter.setReviews(reviews);
    }

    @NonNull
    @Override
    public ReviewsContract.Presenter createPresenter() {
        return (ReviewsContract.Presenter) MoviperPresentersDispatcher.getInstance().getPresenterForView(this);
    }

    private static final String EXTRA_RESTAURANT = "EXTRA_RESTAURANT";

    public static Intent startingIntent(Context context, Restaurant restaurant) {
        val intent = new Intent(context, ReviewsActivity.class);
        intent.putExtra(EXTRA_RESTAURANT, restaurant);
        return intent;
    }
}

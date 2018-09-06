package com.example.there.moviperfood.viper.reviews;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.example.there.moviperfood.R;
import com.example.there.moviperfood.data.food.restaurant.Restaurant;
import com.example.there.moviperfood.data.food.review.Review;
import com.example.there.moviperfood.databinding.ActivityReviewsBinding;
import com.example.there.moviperfood.lifecycle.ConnectivityComponent;
import com.example.there.moviperfood.viper.reviews.list.ReviewsListAdapter;
import com.mateuszkoslacz.moviper.base.view.activity.ViperActivity;
import com.mateuszkoslacz.moviper.presentersdispatcher.MoviperPresentersDispatcher;

import java.util.List;

import lombok.val;

public class ReviewsActivity
        extends ViperActivity<ReviewsContract.View, ReviewsContract.Presenter>
        implements ReviewsContract.View {

    private Restaurant restaurant;

    private static final String KEY_REVIEWS_VIEW_MODEL = "KEY_REVIEWS_VIEW_MODEL";
    private ReviewsViewModel reviewsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViewModel();
        setupObservers();
        initFromSavedState(savedInstanceState);
        initView();

        ConnectivityComponent connectivityComponent = new ConnectivityComponent(
                this,
                !reviewsViewModel.getReviews().isEmpty(),
                findViewById(R.id.reviews_root_layout),
                this::loadReviews);
        getLifecycle().addObserver(connectivityComponent);
    }

    private void initViewModel() {
        restaurant = getIntent().getParcelableExtra(EXTRA_RESTAURANT);
        reviewsViewModel = new ReviewsViewModel(restaurant, new ObservableArrayList<>(), new ObservableField<>(false));
    }

    private void setupObservers() {
        presenter.getReviews().observe(this, reviews -> {
            if (reviews != null) {
                if (reviews.size() > 0) {
                    reviewsViewModel.getIsLoading().set(false);
                    reviewsViewModel.getReviews().addAll(reviews);
                } else {
                    onNoReviewsFound();
                }
            }
        });
    }

    private void initView() {
        ActivityReviewsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_reviews);
        binding.setReviewsView(new ReviewsView(
                reviewsViewModel,
                new ReviewsListAdapter(reviewsViewModel.getReviews()),
                ContextCompat.getDrawable(this, R.drawable.arrow_back),
                v -> onBackPressed(),
                v -> presenter.startMapActivity(restaurant)
        ));
        binding.restaurantReviewsRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_REVIEWS_VIEW_MODEL, reviewsViewModel);
    }

    @SuppressWarnings("ConstantConditions")
    private void initFromSavedState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            reviewsViewModel = savedInstanceState.getParcelable(KEY_REVIEWS_VIEW_MODEL);
            if (reviewsViewModel.getReviews().isEmpty()) {
                loadReviews();
            }
        } else {
            loadReviews();
        }
    }

    private void loadReviews() {
        reviewsViewModel.getIsLoading().set(true);
        presenter.loadReviews(restaurant);
    }

    private void onNoReviewsFound() {
        Toast.makeText(this, getString(R.string.no_reviews_found), Toast.LENGTH_LONG).show();
        finish();
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

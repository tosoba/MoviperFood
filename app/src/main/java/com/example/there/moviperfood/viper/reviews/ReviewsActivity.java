package com.example.there.moviperfood.viper.reviews;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.example.there.moviperfood.R;
import com.example.there.moviperfood.data.food.restaurant.Restaurant;
import com.example.there.moviperfood.data.food.review.Review;
import com.example.there.moviperfood.databinding.ActivityReviewsBinding;
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
        initFromSavedState(savedInstanceState);
        initView();
    }

    private void initViewModel() {
        restaurant = getIntent().getParcelableExtra(EXTRA_RESTAURANT);
        reviewsViewModel = new ReviewsViewModel(restaurant, new ObservableArrayList<>(), new ObservableField<>(false));
    }

    private void initView() {
        ActivityReviewsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_reviews);
        binding.setReviewsView(new ReviewsView(
                reviewsViewModel,
                new ReviewsListAdapter(reviewsViewModel.getReviews()),
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL),
                ContextCompat.getDrawable(this, R.drawable.arrow_back),
                v -> onBackPressed()
        ));
        binding.restaurantReviewsRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_REVIEWS_VIEW_MODEL, reviewsViewModel);
    }

    private void initFromSavedState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            reviewsViewModel = savedInstanceState.getParcelable(KEY_REVIEWS_VIEW_MODEL);
        } else {
            reviewsViewModel.getIsLoading().set(true);
            presenter.loadReviews(restaurant);
        }
    }

    @Override
    public void updateReviews(List<Review> reviews) {
        reviewsViewModel.getIsLoading().set(false);
        reviewsViewModel.getReviews().addAll(reviews);
    }

    @Override
    public void onNoReviewsFound() {
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

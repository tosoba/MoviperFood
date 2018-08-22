package com.example.there.moviperfood.viper.reviews;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.example.there.moviperfood.viper.reviews.list.ReviewsListAdapter;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReviewsView {
    private ReviewsViewModel viewModel;
    private ReviewsListAdapter adapter;
    private Drawable navigationIcon;
    private View.OnClickListener navigationOnClickListener;
    private View.OnClickListener showOnMapOnClickListener;
}

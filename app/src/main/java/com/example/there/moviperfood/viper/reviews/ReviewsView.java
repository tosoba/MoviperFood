package com.example.there.moviperfood.viper.reviews;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.there.moviperfood.viper.reviews.list.ReviewsListAdapter;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReviewsView {
    private ReviewsViewModel viewModel;
    private ReviewsListAdapter adapter;
    private RecyclerView.ItemDecoration itemDecoration;
    private Drawable navigationIcon;
    private View.OnClickListener navigationOnClickListener;
}

package com.example.there.moviperfood.viper.reviews.list;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.there.moviperfood.R;
import com.example.there.moviperfood.data.food.review.Review;
import com.example.there.moviperfood.databinding.ReviewListItemBinding;
import com.example.there.moviperfood.util.AdapterUtils;

import lombok.val;

public class ReviewsListAdapter extends RecyclerView.Adapter<ReviewsListViewHolder> {
    private ObservableList<Review> reviews;

    public ReviewsListAdapter(ObservableList<Review> reviews) {
        this.reviews = reviews;
        AdapterUtils.bindAdapterToItems(this, reviews);
    }

    @NonNull
    @Override
    public ReviewsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        val inflater = LayoutInflater.from(parent.getContext());
        ReviewListItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.review_list_item, parent, false);
        return new ReviewsListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsListViewHolder holder, int position) {
        val review = reviews.get(position);
        holder.getBinding().setReview(review);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }
}

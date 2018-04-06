package com.example.there.moviperfood.viper.reviews.list;

import android.support.v7.widget.RecyclerView;

import com.example.there.moviperfood.databinding.ReviewListItemBinding;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

class ReviewsListViewHolder extends RecyclerView.ViewHolder {
    @Setter(AccessLevel.PRIVATE)
    @Getter
    private ReviewListItemBinding binding;

    ReviewsListViewHolder(ReviewListItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}

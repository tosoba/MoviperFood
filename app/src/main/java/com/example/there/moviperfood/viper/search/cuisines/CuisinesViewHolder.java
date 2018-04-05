package com.example.there.moviperfood.viper.search.cuisines;

import android.support.v7.widget.RecyclerView;

import com.example.there.moviperfood.databinding.CuisineListItemBinding;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

class CuisinesViewHolder extends RecyclerView.ViewHolder {

    @Setter(AccessLevel.PRIVATE)
    @Getter
    private CuisineListItemBinding binding;

    CuisinesViewHolder(CuisineListItemBinding binding, OnCuisineItemClickListener listener) {
        super(binding.getRoot());
        this.binding = binding;

        binding.getRoot().setOnClickListener(view -> {
            val cuisine = binding.getCuisine();
            if (cuisine != null) {
                listener.onClick(cuisine);
            }
        });
    }
}

package com.example.there.moviperfood.viper.cuisines.list;

import androidx.recyclerview.widget.RecyclerView;

import com.example.there.moviperfood.data.food.cuisine.Cuisine;
import com.example.there.moviperfood.databinding.CuisineListItemBinding;
import com.example.there.moviperfood.viper.common.OnListItemClickListener;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

class CuisinesViewHolder extends RecyclerView.ViewHolder {

    @Setter(AccessLevel.PRIVATE)
    @Getter
    private CuisineListItemBinding binding;

    CuisinesViewHolder(CuisineListItemBinding binding, OnListItemClickListener<Cuisine> listener) {
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

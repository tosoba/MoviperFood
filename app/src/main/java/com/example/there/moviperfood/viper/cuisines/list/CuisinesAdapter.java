package com.example.there.moviperfood.viper.cuisines.list;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.there.moviperfood.R;
import com.example.there.moviperfood.data.food.cuisine.Cuisine;
import com.example.there.moviperfood.databinding.CuisineListItemBinding;

import java.util.List;

import lombok.val;

public class CuisinesAdapter extends RecyclerView.Adapter<CuisinesViewHolder> {

    private List<Cuisine> cuisines;
    private OnCuisineItemClickListener onClickListener;

    public CuisinesAdapter(List<Cuisine> cuisines, OnCuisineItemClickListener onClickListener) {
        this.cuisines = cuisines;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public CuisinesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        val inflater = LayoutInflater.from(parent.getContext());
        CuisineListItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.cuisine_list_item, parent, false);
        return new CuisinesViewHolder(binding, onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CuisinesViewHolder holder, int position) {
        val cuisine = cuisines.get(position);
        holder.getBinding().setCuisine(cuisine);
    }

    @Override
    public int getItemCount() {
        return cuisines.size();
    }

    public void setCuisines(List<Cuisine> cuisines) {
        this.cuisines = cuisines;
        notifyDataSetChanged();
    }
}

package com.example.there.moviperfood.viper.cuisines.list;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.annimon.stream.Stream;
import com.example.there.moviperfood.R;
import com.example.there.moviperfood.data.food.cuisine.Cuisine;
import com.example.there.moviperfood.databinding.CuisineListItemBinding;
import com.example.there.moviperfood.databinding.CuisinesListHeaderBinding;
import com.example.there.moviperfood.util.AdapterUtils;
import com.example.there.moviperfood.viper.common.OnListItemClickListener;

import java.util.HashSet;

import lombok.val;

public class CuisinesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private ObservableList<Cuisine> cuisines;
    private final OnListItemClickListener<Cuisine> onClickListener;
    private final String placeName;

    public CuisinesAdapter(String placeName, ObservableList<Cuisine> cuisines, OnListItemClickListener<Cuisine> onClickListener) {
        this.placeName = placeName;
        this.cuisines = cuisines;
        this.onClickListener = onClickListener;
        AdapterUtils.bindAdapterToItems(this, cuisines, 1);
    }

    private static final int HEADER_VIEW_TYPE = 0;
    private static final int CUISINE_VIEW_TYPE = 1;

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return HEADER_VIEW_TYPE;
        else return CUISINE_VIEW_TYPE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        val inflater = LayoutInflater.from(parent.getContext());
        if (viewType == CUISINE_VIEW_TYPE) {
            CuisineListItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.cuisine_list_item, parent, false);
            return new CuisinesViewHolder(binding, onClickListener);
        } else {
            CuisinesListHeaderBinding binding = DataBindingUtil.inflate(inflater, R.layout.cuisines_list_header, parent, false);
            binding.setPlaceName(placeName);
            return new CuisinesHeaderViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CuisinesViewHolder) {
            val hold = (CuisinesViewHolder) holder;
            val cuisine = cuisines.get(position - 1);
            hold.getBinding().setCuisine(cuisine);
        }
    }

    @Override
    public int getItemCount() {
        return cuisines.size() + 1;
    }

    private HashSet<Cuisine> removedItems = new HashSet<>();

    public HashSet<Cuisine> getRemovedItems() {
        return removedItems;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                val constraintStr = constraint.toString();
                HashSet<Cuisine> values = new HashSet<>();

                if (constraint.length() == 0) {
                    values.addAll(cuisines);
                    values.addAll(removedItems);
                } else {
                    val fromCuisines = Stream.of(cuisines)
                            .filter(c -> c.getCuisineName().toLowerCase().contains(constraintStr.toLowerCase()))
                            .toList();
                    val fromRemoved = Stream.of(removedItems)
                            .filter(c -> c.getCuisineName().toLowerCase().contains(constraintStr.toLowerCase()))
                            .toList();

                    values.addAll(fromCuisines);
                    values.addAll(fromRemoved);
                }

                results.values = values;

                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                val resultsSet = (HashSet<Cuisine>) results.values;
                if (resultsSet != null) {
                    val toRemove = Stream.of(cuisines).filter(c -> !resultsSet.contains(c)).toList();
                    val toAdd = Stream.of(removedItems).filter(resultsSet::contains).toList();

                    cuisines.removeAll(toRemove);
                    removedItems.addAll(toRemove);

                    cuisines.addAll(toAdd);
                    removedItems.removeAll(toAdd);
                }
            }
        };
    }

    private static class CuisinesHeaderViewHolder extends RecyclerView.ViewHolder {

        CuisinesHeaderViewHolder(CuisinesListHeaderBinding binding) {
            super(binding.getRoot());
        }
    }
}

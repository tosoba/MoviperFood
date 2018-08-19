package com.example.there.moviperfood.viper.cuisines;

import android.support.v7.widget.RecyclerView;

import com.example.there.moviperfood.viper.cuisines.list.CuisinesAdapter;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CuisinesView {
    private CuisinesViewModel viewModel;
    private CuisinesAdapter adapter;
    private RecyclerView.ItemDecoration itemDecoration;
}

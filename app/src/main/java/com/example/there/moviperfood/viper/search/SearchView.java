package com.example.there.moviperfood.viper.search;

import android.support.v7.widget.RecyclerView;

import com.example.there.moviperfood.viper.search.list.SearchHistoryAdapter;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SearchView {
    private SearchHistoryAdapter adapter;
    private RecyclerView.ItemDecoration itemDecoration;
}

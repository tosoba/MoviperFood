package com.example.there.moviperfood.viper.search;

import android.view.View;

import com.example.there.moviperfood.viper.search.list.SearchHistoryAdapter;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SearchView {
    private SearchHistoryAdapter adapter;
    private View.OnClickListener onNearMeClickListener;
}

package com.example.there.moviperfood.viper.cuisines;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;

import com.example.there.moviperfood.viper.cuisines.list.CuisinesAdapter;

import lombok.Getter;

@Getter
public class CuisinesView {
    private CuisinesViewModel viewModel;
    private CuisinesAdapter adapter;
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            adapter.getFilter().filter(s);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    CuisinesView(CuisinesViewModel viewModel, CuisinesAdapter adapter) {
        this.viewModel = viewModel;
        this.adapter = adapter;
    }
}

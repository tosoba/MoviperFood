package com.example.there.moviperfood.binding;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RecyclerViewBindings {
    @BindingAdapter("itemDecoration")
    public static void bindItemDecoration(RecyclerView recyclerView, RecyclerView.ItemDecoration itemDecoration) {
        recyclerView.addItemDecoration(itemDecoration);
    }
}

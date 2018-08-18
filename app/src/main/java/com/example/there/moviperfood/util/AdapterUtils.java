package com.example.there.moviperfood.util;

import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdapterUtils {
    public static <T> void bindAdapterToItems(RecyclerView.Adapter adapter, ObservableList<T> items, int offset) {
        items.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<T>>() {
            @Override
            public void onChanged(ObservableList<T> sender) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(ObservableList<T> sender, int positionStart, int itemCount) {
                adapter.notifyItemRangeChanged(positionStart + offset, itemCount);
            }

            @Override
            public void onItemRangeInserted(ObservableList<T> sender, int positionStart, int itemCount) {
                adapter.notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(ObservableList<T> sender, int fromPosition, int toPosition, int itemCount) {
                adapter.notifyItemMoved(fromPosition + offset, toPosition + offset);
            }

            @Override
            public void onItemRangeRemoved(ObservableList<T> sender, int positionStart, int itemCount) {
                adapter.notifyItemRangeRemoved(positionStart + offset, itemCount);
            }
        });
    }

    public static <T> void bindAdapterToItems(RecyclerView.Adapter adapter, ObservableList<T> items) {
        bindAdapterToItems(adapter, items, 0);
    }
}

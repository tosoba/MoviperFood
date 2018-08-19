package com.example.there.moviperfood.binding;

import android.databinding.BindingAdapter;
import android.support.v7.widget.Toolbar;
import android.view.View;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ToolbarBindings {
    @BindingAdapter("navigationOnClickListener")
    public static void bindNavigationOnClickListener(Toolbar toolbar, View.OnClickListener listener) {
        toolbar.setNavigationOnClickListener(listener);
    }
}

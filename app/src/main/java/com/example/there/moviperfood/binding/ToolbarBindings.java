package com.example.there.moviperfood.binding;

import androidx.databinding.BindingAdapter;
import androidx.appcompat.widget.Toolbar;
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

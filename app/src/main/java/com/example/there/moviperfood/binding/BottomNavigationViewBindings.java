package com.example.there.moviperfood.binding;

import android.databinding.BindingAdapter;
import android.support.design.widget.BottomNavigationView;

public class BottomNavigationViewBindings {
    @BindingAdapter("onNavigationItemSelectedListener")
    public static void bindOnNavigationItemSelectedListener(BottomNavigationView bottomNavigationView, BottomNavigationView.OnNavigationItemSelectedListener listener) {
        bottomNavigationView.setOnNavigationItemSelectedListener(listener);
    }
}

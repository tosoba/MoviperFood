package com.example.there.moviperfood.binding;

import androidx.databinding.BindingAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationViewBindings {
    @BindingAdapter("onNavigationItemSelectedListener")
    public static void bindOnNavigationItemSelectedListener(BottomNavigationView bottomNavigationView, BottomNavigationView.OnNavigationItemSelectedListener listener) {
        bottomNavigationView.setOnNavigationItemSelectedListener(listener);
    }
}

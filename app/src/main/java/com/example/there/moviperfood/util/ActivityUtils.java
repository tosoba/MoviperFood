package com.example.there.moviperfood.util;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.val;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ActivityUtils {
    public static void setHomeButtonEnabled(AppCompatActivity activity, @DrawableRes int iconResourceId) {
        val actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(iconResourceId);
        }
    }
}

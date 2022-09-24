package com.example.there.moviperfood.binding;

import androidx.databinding.BindingAdapter;
import android.graphics.Color;
import android.graphics.drawable.LayerDrawable;
import androidx.core.graphics.drawable.DrawableCompat;
import android.widget.RatingBar;

public class RatingBarBindings {
    @BindingAdapter("fullySelectedStarColor")
    public static void bindStarColors(RatingBar ratingBar, String colorHex) {
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        DrawableCompat.setTint(stars.getDrawable(2), Color.parseColor("#" + colorHex));
        DrawableCompat.setTint(stars.getDrawable(1), Color.GRAY);
        DrawableCompat.setTint(stars.getDrawable(0), Color.LTGRAY);
    }
}

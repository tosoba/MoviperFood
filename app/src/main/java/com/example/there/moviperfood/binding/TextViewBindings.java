package com.example.there.moviperfood.binding;

import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.widget.TextView;

public class TextViewBindings {
    private TextViewBindings() {
    }

    @BindingAdapter("textColorString")
    public static void bindTextColorString(TextView textView, String colorHex) {
        textView.setTextColor(Color.parseColor("#" + colorHex));
    }
}

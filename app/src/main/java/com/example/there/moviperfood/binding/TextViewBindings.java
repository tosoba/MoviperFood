package com.example.there.moviperfood.binding;

import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.widget.TextView;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TextViewBindings {

    @BindingAdapter("textColorString")
    public static void bindTextColorString(TextView textView, String colorHex) {
        textView.setTextColor(Color.parseColor("#" + colorHex));
    }
}

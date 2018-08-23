package com.example.there.moviperfood.binding;

import android.databinding.BindingAdapter;
import android.text.TextWatcher;
import android.widget.EditText;

public class EditTextBindings {
    @BindingAdapter("textWatcher")
    public static void bindTextWatcher(EditText editText, TextWatcher textWatcher) {
        editText.addTextChangedListener(textWatcher);
    }
}

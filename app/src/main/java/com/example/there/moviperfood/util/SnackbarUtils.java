package com.example.there.moviperfood.util;

import com.google.android.material.snackbar.Snackbar;
import android.view.View;
import android.widget.FrameLayout;

public class SnackbarUtils {
    public static void showSnackbarWithBottomMargin(Snackbar snackbar, int marginBottom) {
        final View snackBarView = snackbar.getView();
        final FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackBarView.getLayoutParams();

        params.setMargins(params.leftMargin , params.topMargin, params.rightMargin, params.bottomMargin + marginBottom);

        snackBarView.setLayoutParams(params);
        snackbar.show();
    }
}

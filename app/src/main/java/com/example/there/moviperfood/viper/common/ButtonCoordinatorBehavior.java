package com.example.there.moviperfood.viper.common;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class ButtonCoordinatorBehavior extends CoordinatorLayout.Behavior<Button> {

    public ButtonCoordinatorBehavior(Context context, AttributeSet attrs) {
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull Button child, @NonNull View dependency) {
        return dependency instanceof Snackbar.SnackbarLayout;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull Button child, @NonNull View dependency) {
        float translationY = getFabTranslationYForSnackbar(parent, child);
        float percentComplete = -translationY / dependency.getHeight();
        float scaleFactor = 1 - percentComplete;

        child.setScaleX(scaleFactor);
        child.setScaleY(scaleFactor);
        return false;
    }

    private float getFabTranslationYForSnackbar(CoordinatorLayout parent, Button fab) {
        float minOffset = 0;
        final List<View> dependencies = parent.getDependencies(fab);
        for (int i = 0, z = dependencies.size(); i < z; i++) {
            final View view = dependencies.get(i);
            if (view instanceof Snackbar.SnackbarLayout && parent.doViewsOverlap(fab, view)) {
                minOffset = Math.min(minOffset, ViewCompat.getTranslationY(view) - view.getHeight());
            }
        }

        return minOffset;
    }
}

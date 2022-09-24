package com.example.there.moviperfood.lifecycle;

import android.app.Activity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import android.content.Intent;
import android.provider.Settings;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.there.moviperfood.R;
import com.example.there.moviperfood.util.MeasurementUtils;
import com.example.there.moviperfood.util.SnackbarUtils;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import lombok.val;

public class ConnectivityComponent implements LifecycleObserver {
    private final Activity activity;
    private boolean isDataLoaded;
    private final View parentView;
    private final ReloadsData reloadsData;
    private final boolean shouldShowSnackbarWithBottomMargin;

    public ConnectivityComponent(Activity activity, boolean isDataLoaded, View parentView, ReloadsData reloadsData) {
        this(activity, isDataLoaded, parentView, reloadsData, false);
    }

    public ConnectivityComponent(Activity activity, boolean isDataLoaded, View parentView, ReloadsData reloadsData, boolean shouldShowSnackbarWithBottomMargin) {
        this.activity = activity;
        this.isDataLoaded = isDataLoaded;
        this.parentView = parentView;
        this.reloadsData = reloadsData;
        this.shouldShowSnackbarWithBottomMargin = shouldShowSnackbarWithBottomMargin;
    }

    private Disposable internetDisposable;
    private boolean connectionInterrupted = false;
    private boolean lastConnectionStatus;

    public boolean getLastConnectionStatus() {
        return lastConnectionStatus;
    }

    private Snackbar snackbar;
    private boolean isSnackbarShowing = false;

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void observeInternetConnectivity() {
        internetDisposable = ReactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isConnectedToInternet -> {
                            lastConnectionStatus = isConnectedToInternet;
                            handleConnectionStatus(isConnectedToInternet);
                        }
                );
    }

    private void handleConnectionStatus(boolean isConnectedToInternet) {
        if (!isConnectedToInternet) {
            connectionInterrupted = true;
            if (!isSnackbarShowing) {
                showNoConnectionDialog();
            }
        } else {
            if (connectionInterrupted) {
                connectionInterrupted = false;
                if (!isDataLoaded && reloadsData != null) {
                    reloadsData.reloadData();
                }
            }

            isSnackbarShowing = false;
            if (snackbar != null) snackbar.dismiss();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void clear() {
        if (snackbar != null)
            snackbar.dismiss();
        snackbar = null;
        safelyDispose(internetDisposable);
    }

    private void safelyDispose(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    private void showNoConnectionDialog() {
        val color = ContextCompat.getColor(activity, R.color.colorAccent);
        snackbar = Snackbar
                .make(parentView, "No internet connection.", Snackbar.LENGTH_LONG)
                .setAction("SETTINGS", v -> {
                    val settingsIntent = new Intent(Settings.ACTION_SETTINGS);
                    activity.startActivity(settingsIntent);
                })
                .setCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        if (event == DISMISS_EVENT_SWIPE) {
                            showNoConnectionDialog();
                        }
                    }
                })
                .setActionTextColor(color);

        TextView textView = snackbar.getView().findViewById(R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.setDuration(BaseTransientBottomBar.LENGTH_INDEFINITE);

        if (!shouldShowSnackbarWithBottomMargin) {
            snackbar.show();
        } else {
            SnackbarUtils.showSnackbarWithBottomMargin(snackbar, (int) MeasurementUtils.convertDpToPixel(48.0f, activity));
        }
    }

    public void checkConnectivityStatusAndRun(RunsWhenStatus onConnected, RunsWhenStatus onDisconnected) {
        if (lastConnectionStatus) {
            onConnected.run();
        } else {
            onDisconnected.run();
        }
    }

    public void checkConnectivityStatusAndRun(RunsWhenStatus onConnected) {
        if (lastConnectionStatus) {
            onConnected.run();
        } else {
            Toast.makeText(activity, activity.getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }

    public interface RunsWhenStatus {
        void run();
    }

    public interface ReloadsData {
        void reloadData();
    }
}

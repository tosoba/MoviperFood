package com.example.there.moviperfood.util;

import android.content.Context;

import io.nlopez.smartlocation.SmartLocation;

public class LocationUtils {
    public static boolean isLocationEnabled(Context context) {
        return SmartLocation.with(context).location().state().locationServicesEnabled();
    }
}

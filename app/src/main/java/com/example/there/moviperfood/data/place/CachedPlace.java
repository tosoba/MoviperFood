package com.example.there.moviperfood.data.place;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

import lombok.Getter;

@Entity(tableName = "places")
@Getter
public class CachedPlace {
    @PrimaryKey
    @NonNull
    private final String name;

    private final double lat;

    private final double lng;

    private final Date lastSearched;

    public CachedPlace(@NonNull String name, double lat, double lng, @NonNull Date lastSearched) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.lastSearched = lastSearched;
    }
}

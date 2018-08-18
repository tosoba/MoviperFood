package com.example.there.moviperfood.data.place;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.example.there.moviperfood.data.common.DateConverter;

@Database(
        entities = {CachedPlace.class},
        version = 1,
        exportSchema = false
)
@TypeConverters({DateConverter.class})
public abstract class PlacesDb extends RoomDatabase {
    public abstract PlaceDao placeDao();
}

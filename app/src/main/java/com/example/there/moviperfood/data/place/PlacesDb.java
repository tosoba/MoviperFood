package com.example.there.moviperfood.data.place;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

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

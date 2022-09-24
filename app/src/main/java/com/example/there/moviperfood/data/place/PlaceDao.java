package com.example.there.moviperfood.data.place;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.there.moviperfood.data.common.BaseDao;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface PlaceDao extends BaseDao<CachedPlace> {
    @Query("SELECT * FROM places")
    Flowable<List<CachedPlace>> getPlaces();

    @Query("DELETE FROM places WHERE lastSearched=(SELECT MAX(lastSearched) FROM places)")
    void deleteMostRecentlyAddedPlace();
}

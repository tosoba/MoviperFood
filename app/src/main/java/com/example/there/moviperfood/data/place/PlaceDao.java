package com.example.there.moviperfood.data.place;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.example.there.moviperfood.data.common.BaseDao;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface PlaceDao extends BaseDao<CachedPlace> {
    @Query("SELECT * FROM places")
    Flowable<List<CachedPlace>> getPlaces();
}

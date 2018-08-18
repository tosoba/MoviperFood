package com.example.there.moviperfood.data.common;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

@Dao
public interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(T item);

    @Delete
    void delete(T item);
}

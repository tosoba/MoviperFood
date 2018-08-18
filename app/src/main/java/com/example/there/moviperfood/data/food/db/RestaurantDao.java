package com.example.there.moviperfood.data.food.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.example.there.moviperfood.data.common.BaseDao;
import com.example.there.moviperfood.data.food.restaurant.Restaurant;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface RestaurantDao extends BaseDao<Restaurant> {

    @Query("SELECT * FROM restaurants")
    Flowable<List<Restaurant>> getRestaurants();
}

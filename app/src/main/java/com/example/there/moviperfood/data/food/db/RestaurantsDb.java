package com.example.there.moviperfood.data.food.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.there.moviperfood.data.common.DateConverter;
import com.example.there.moviperfood.data.common.StringArrayConverter;
import com.example.there.moviperfood.data.food.db.converter.RestaurantLocationConverter;
import com.example.there.moviperfood.data.food.db.converter.RestaurantUserRatingConverter;
import com.example.there.moviperfood.data.food.restaurant.Restaurant;

@Database(
        entities = {Restaurant.class},
        version = 1,
        exportSchema = false
)
@TypeConverters({
        RestaurantLocationConverter.class,
        RestaurantUserRatingConverter.class,
        StringArrayConverter.class,
        DateConverter.class
})
public abstract class RestaurantsDb extends RoomDatabase {
    public abstract RestaurantDao restaurantDao();
}

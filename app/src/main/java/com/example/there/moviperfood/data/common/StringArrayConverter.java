package com.example.there.moviperfood.data.common;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class StringArrayConverter {
    @TypeConverter
    public String[] toArray(String json) {
        Type stringArrayType = new TypeToken<String[]>() {
        }.getType();
        return new Gson().fromJson(json, stringArrayType);
    }

    @TypeConverter
    public String toJson(String[] array) {
        Gson gson = new Gson();
        return gson.toJson(array);
    }
}

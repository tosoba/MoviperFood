package com.example.there.moviperfood.data.common;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class DateConverter {
    @TypeConverter
    public Date toDate(long value) {
        return new Date(value);
    }

    @TypeConverter
    public long fromDate(Date date) {
        return date.getTime();
    }
}

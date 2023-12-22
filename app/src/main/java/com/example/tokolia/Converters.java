package com.example.tokolia;

import androidx.room.TypeConverter;

import java.util.Date;

public class Converters {

    @TypeConverter
    public Long convertDateToLong(Date date){
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public Date convertLongToDate(Long value){
        return value == null ? null  : new Date(value);
    }

}

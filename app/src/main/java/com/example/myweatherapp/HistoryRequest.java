package com.example.myweatherapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
@Entity(indices = {@Index(value = {"city_name", "temperature"})})
public class HistoryRequest {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    @ColumnInfo(name = "city_name")
    String city_name;

    @ColumnInfo(name = "temperature")
    String temperature;


}

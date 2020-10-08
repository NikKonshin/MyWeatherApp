package com.example.myweatherapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {HistoryRequest.class}, version = 1)
public abstract class EducationDatabase extends RoomDatabase {
    public abstract HistoryDao getHistoryDao();
}

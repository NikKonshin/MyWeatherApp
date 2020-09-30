package com.example.myweatherapp;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import androidx.room.Room;

public class App extends Application {

    private static App instance;


    private EducationDatabase db;

    public static App getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        db = Room.databaseBuilder(
                getApplicationContext(),
                EducationDatabase.class,
                "education_database").
                allowMainThreadQueries().
                build();

    }

    public HistoryDao getHistoryDao(){
        return db.getHistoryDao();
    }


}

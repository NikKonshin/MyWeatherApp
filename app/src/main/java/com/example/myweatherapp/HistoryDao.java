package com.example.myweatherapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertHistory(HistoryRequest historyRequest);

    @Update
    void updateHistory(HistoryRequest historyRequest);

    @Query("DELETE FROM historyRequest")
    void deleteHistory();

    @Query("SELECT COUNT() FROM historyRequest")
    long qetCountHistoryRequest();

    @Query("SELECT * FROM historyRequest")
    List<HistoryRequest> getAllHistoryRequest();


}

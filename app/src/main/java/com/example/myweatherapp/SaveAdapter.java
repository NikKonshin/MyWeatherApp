package com.example.myweatherapp;

import android.widget.Adapter;

import androidx.recyclerview.widget.RecyclerView;

public class SaveAdapter  {
    private static SaveAdapter INSTANCE = null;
    private RecyclerView.Adapter<HistoryAdapter.ViewHolder> adapter;

    private SaveAdapter(){

    }

    public void saveAdapter (RecyclerView.Adapter<HistoryAdapter.ViewHolder> save){
        adapter = save;
    }
    public RecyclerView.Adapter<HistoryAdapter.ViewHolder> getAdapter(){
        return adapter;
    }


    public static SaveAdapter getInstanceAdapter(){
        if(INSTANCE == null){
            synchronized (SaveAdapter.class){
                if (INSTANCE== null){
                    INSTANCE = new SaveAdapter();
                }
            }
        }
        return INSTANCE;
    }
}

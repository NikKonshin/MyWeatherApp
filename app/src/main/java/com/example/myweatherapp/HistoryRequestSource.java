package com.example.myweatherapp;

import java.util.List;

public class HistoryRequestSource {

    private final HistoryDao historyDao;

    private List<HistoryRequest> history;

    public HistoryRequestSource(HistoryDao historyDao) {
        this.historyDao = historyDao;
    }

    public List<HistoryRequest> getAllHistory(){
        if(history == null){
        loadAllHistory();
        }
        return history;
    }

    public void loadAllHistory(){
        history = historyDao.getAllHistoryRequest();
    }

    public void addHistory(HistoryRequest historyRequest){
        historyDao.insertHistory(historyRequest);
    }

    public long getCountHistoryRequest(){
        return historyDao.qetCountHistoryRequest();
    }

    public void deleteAllHistory(){
        historyDao.deleteHistory();
    }
}

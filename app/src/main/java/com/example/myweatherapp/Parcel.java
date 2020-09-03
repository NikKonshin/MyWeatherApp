package com.example.myweatherapp;

import java.io.Serializable;

public class Parcel implements Serializable {
    String cityName;
    int weatherIndex;


    public int getWeatherIndex() {
        return weatherIndex;
    }

    public String getCityName() {
        return cityName;
    }

    public Parcel(String cityName, int weatherIndex ) {
        this.cityName = cityName;
        this.weatherIndex = weatherIndex;

    }
}

package com.example.myweatherapp;

import java.io.Serializable;

public class Parcel implements Serializable {
   private String cityName;
   private String weatherIndex;
   private String pressure;
   private String humidity;
   private String windSpeed;

    public String getPressure() {
        return pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getWeatherIndex() {
        return weatherIndex;
    }

    public String getCityName() {
        return cityName;
    }

    public Parcel(String cityName, String weatherIndex, String pressure, String humidity, String windSpeed) {
        this.cityName = cityName;
        this.weatherIndex = weatherIndex;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
    }

    public Parcel(String cityName, String weatherIndex ) {
        this.cityName = cityName;
        this.weatherIndex = weatherIndex;

    }
}

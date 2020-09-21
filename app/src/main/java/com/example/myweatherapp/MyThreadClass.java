package com.example.myweatherapp;

import android.os.Handler;
import android.os.HandlerThread;

import androidx.annotation.NonNull;

import com.example.myweatherapp.model.WeatherRequest;

import java.io.IOException;

public class MyThreadClass extends HandlerThread {
    private String city;
    private String weather;
    private String pressure;
    private String humidity;
    private String windSpeed;
    private WeatherRequest weatherRequest;
    private Handler mWorkerHandler;

    public String getCity() {
        return city;
    }
    public String getWeather() {
        return weather;
    }
    public String getPressure() {
        return pressure;
    }
    public String getHumidity() {
        return humidity;
    }
    public String getWindSpeed() {
        return windSpeed;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public MyThreadClass(@NonNull String name) {
        super(name);
    }

    public void getWeatherData(){
        mWorkerHandler.post(new Runnable() {
            @Override
            public void run() {

                String cityName = city;

                startAPI(cityName);

                city = String.format("%s", weatherRequest.getName());
                weather = String.format("%s", (int) weatherRequest.getMain().getTemp() - 273);
                pressure = String.format("%s", (int) (weatherRequest.getMain().getPressure() / 1.332894736842105));
                humidity = String.format("%s", weatherRequest.getMain().getHumidity());
                windSpeed = String.format("%s", (int) weatherRequest.getWind().getSpeed());

            }
        });



    }
    public void startFragmentWithData(Runnable task){
        mWorkerHandler.post(task);
    }


    void startAPI(String city) {
        final WorkWithApi workWithApi = new WorkWithApi();
        try {
            weatherRequest = workWithApi.getWeather(city);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            workWithApi.closeConnection();
        }
    }

    public void prepareHandler(){
        mWorkerHandler = new Handler(getLooper());
    }
}

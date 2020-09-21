package com.example.myweatherapp;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.myweatherapp.model.WeatherRequest;

import java.io.IOException;

public class MyWorkManager extends Worker implements Constants {
   private Data output;
   private WeatherRequest weatherRequest;
   private MainActivity mainActivity;


    public MyWorkManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        String cityName = getInputData().getString(CITY_NAME);

        getWeth(cityName);


        {

            String city = String.format("%s", weatherRequest.getName());
            String weather = String.format("%s", (int) weatherRequest.getMain().getTemp() - 273);
            String pressure = String.format("%s", (int) (weatherRequest.getMain().getPressure() / 1.332894736842105));
            String humidity = String.format("%s", weatherRequest.getMain().getHumidity());
            String windSpeed = String.format("%s", (int) weatherRequest.getWind().getSpeed());


            Data output = new Data.Builder()
                    .putString(CITY_NAME, city)
                    .putString(WEATHER, weather)
                    .putString(PRESSURE, pressure)
                    .putString(HUMIDITY, humidity)
                    .putString(WIND_SPEED, windSpeed)
                    .build();

            return Result.success(output);
        }
    }
    void getWeth(String city){
        final WorkWithApi workWithApi = new WorkWithApi();
        try {
            weatherRequest = workWithApi.getWeather(city);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            workWithApi.closeConnection();
        }
    }
}

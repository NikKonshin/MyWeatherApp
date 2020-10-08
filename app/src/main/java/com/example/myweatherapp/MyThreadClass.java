package com.example.myweatherapp;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.myweatherapp.model.Weather;
import com.example.myweatherapp.model.WeatherRequest;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;

import retrofit2.Response;

public class MyThreadClass extends HandlerThread {
    private String city;
    private String weather;
    private String pressure;
    private String humidity;
    private String windSpeed;
    int messageId = 1000;

    public WeatherRequest getWeatherRequest() {
        return weatherRequest;
    }

    private WeatherRequest weatherRequest;
    private Handler mWorkerHandler;
    private WorkWithApi workWithApi;
    private LatLng latLng;



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
    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public MyThreadClass(@NonNull String name) {
        super(name);
    }

    public void getWeatherData(final Context context){
        mWorkerHandler.post(new Runnable() {
            @Override
            public void run() {

                String cityName = city;

                workWithApi = new WorkWithApi();
                workWithApi.initRetrofit();
                try {
                    weatherRequest = workWithApi.requestRetrofit(cityName);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                city = String.format("%s", weatherRequest.getName());
                weather = String.format("%s", (int) weatherRequest.getMain().getTemp() - 273);
                pressure = String.format("%s", (int) (weatherRequest.getMain().getPressure() / 1.332894736842105));
                humidity = String.format("%s", weatherRequest.getMain().getHumidity());
                windSpeed = String.format("%s", (int) weatherRequest.getWind().getSpeed());
                Log.d("156698",String.format("%s",(int) weatherRequest.getWind().getSpeed()));
                Weather[] weatherArr = weatherRequest.getWeather();
                Log.d("156698",String.format("%s",weatherArr[0].getMain() ));

                if(weatherArr[0].getMain().equals("Rain")) {
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "2")
                            .setSmallIcon(R.drawable.ic_warning_black_24dp)
                            .setContentTitle("MyWeatherApp")
                            .setContentText("На улице дождь");
                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(messageId++, builder.build());
                }
           }
        });

    }

    public void getWeatherDataLatLng(final Context context){
        if(latLng != null) {
        mWorkerHandler.post(new Runnable() {
            @Override
            public void run() {

                workWithApi = new WorkWithApi();
                workWithApi.initRetrofit();
                try {

                        weatherRequest = workWithApi.requestRetrofitLatLng(latLng.latitude, latLng.longitude);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                city = String.format("%s", weatherRequest.getName());
                weather = String.format("%s", (int) weatherRequest.getMain().getTemp() - 273);
                pressure = String.format("%s", (int) (weatherRequest.getMain().getPressure() / 1.332894736842105));
                humidity = String.format("%s", weatherRequest.getMain().getHumidity());
                windSpeed = String.format("%s", (int) weatherRequest.getWind().getSpeed());

                Weather[] weatherArr = weatherRequest.getWeather();
                Log.d("156698",String.format("%s",weatherArr[0].getMain() ));

                if(weatherArr[0].getMain().equals("Rain")) {
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "2")
                            .setSmallIcon(R.drawable.ic_warning_black_24dp)
                            .setContentTitle("MyWeatherApp")
                            .setContentText("На улице дождь");
                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(messageId++, builder.build());


                }

            }
        });
        }

    }
    public void startFragmentWithData(Runnable task){
        mWorkerHandler.post(task);
    }

    public void prepareHandler(){
        mWorkerHandler = new Handler(getLooper());
    }



}


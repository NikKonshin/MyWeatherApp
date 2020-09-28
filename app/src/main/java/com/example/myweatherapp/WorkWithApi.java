package com.example.myweatherapp;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myweatherapp.model.Weather;
import com.example.myweatherapp.model.WeatherRequest;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WorkWithApi implements Constants {

    private HttpsURLConnection urlConnection = null;
    private OpenWeather openWeather;
    private Response<WeatherRequest> weatherRequestResponse;


    public void initRetrofit(){
        Retrofit retrofit;
        retrofit = new Retrofit.Builder().baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        openWeather = retrofit.create(OpenWeather.class);
        System.out.println("initRetrofit() Сработал");
    }

    public WeatherRequest requestRetrofit(String city) throws IOException {
        Call<WeatherRequest> weatherRequestCall = openWeather.loadWeather(city, BuildConfig.WEATHER_API_KEY );
            weatherRequestResponse = weatherRequestCall.execute();

            if (weatherRequestResponse.isSuccessful()){
               return weatherRequestResponse.body();
            }

        return null;
    }



}






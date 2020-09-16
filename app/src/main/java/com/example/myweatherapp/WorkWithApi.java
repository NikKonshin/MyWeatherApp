package com.example.myweatherapp;

import android.os.Handler;
import android.util.Log;
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

public class WorkWithApi implements Constants {

    private HttpsURLConnection urlConnection = null;

    public WeatherRequest getWeather(String city) throws IOException {
        String url = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s,RU&appid=%s",
                city, BuildConfig.WEATHER_API_KEY);


        final URL uri = new URL(url);

        urlConnection = (HttpsURLConnection) uri.openConnection();
        urlConnection.setRequestMethod(GET);
        urlConnection.setReadTimeout(MAX_TIMEOUT);
        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String result = getLines(in);
        Gson gson = new Gson();
        final WeatherRequest weatherRequest = gson.fromJson(result, WeatherRequest.class);
        return weatherRequest;
    }

    private static String getLines(BufferedReader in) {
        return in.lines().collect(Collectors.joining("\n"));
    }

    void closeConnection() {
        if (urlConnection != null) {
            urlConnection.disconnect();
        }
    }
}






package com.example.myweatherapp;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myweatherapp.model.WeatherRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WeatherFragment extends Fragment implements Constants {


    public static WeatherFragment create(Parcel parcel) {
        WeatherFragment wf = new WeatherFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(PARCEL, parcel);
        wf.setArguments(bundle);
        return wf;
    }

    public Parcel getParcel() {
        Parcel parcel = (Parcel) getArguments().getSerializable(PARCEL);
        return parcel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final TextView cityText = view.findViewById(R.id.text_city_in_weather_fragment);
        final TextView weatherText = view.findViewById(R.id.text_weather_in_weather_fragment);

        final TextView pressure = view.findViewById(R.id.text_pressure_in_weather_fragment);
        final TextView humidity = view.findViewById(R.id.text_humidity_in_weather_fragment);
        final TextView windSpeed = view.findViewById(R.id.text_windSpeed_in_weather_fragment);

        final Parcel parcel = getParcel();

//        final WorkWithApi workWithApi = new WorkWithApi();
//        final Handler handler = new Handler();

        cityText.setText(parcel.getCityName());
        weatherText.setText(parcel.getWeatherIndex());
        pressure.setText(parcel.getPressure());
        humidity.setText(parcel.getHumidity());
        windSpeed.setText(parcel.getWindSpeed());

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    final WeatherRequest weatherRequest = workWithApi.getWeather(parcel.getCityName());
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                                cityText.setText(String.format("%s",weatherRequest.getName()));
//                                weatherText.setText(String.format("%s °C", (int)weatherRequest.getMain().getTemp() - 273) );
//                                pressure.setText(String.format("%s мм рт. ст.", (int)(weatherRequest.getMain().getPressure()/1.332894736842105)));
//                                humidity.setText(String.format("%s", weatherRequest.getMain().getHumidity()) + " %");
//                                windSpeed.setText(String.format("%s м/с", (int)weatherRequest.getWind().getSpeed()));
//
//                        }
//                    });
//                } catch (IOException e) {
//                    e.printStackTrace();
//
//                } finally {
//                    workWithApi.closeConnection();
//                }
//
//            }
//
//        }).start();
    }
}

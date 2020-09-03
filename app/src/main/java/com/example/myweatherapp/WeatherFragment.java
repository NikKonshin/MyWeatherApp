package com.example.myweatherapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;

public class WeatherFragment extends Fragment implements Constants{

    public static WeatherFragment create(Parcel parcel){
        WeatherFragment wf = new WeatherFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(PARCEL,parcel);
        wf.setArguments(bundle);
        return wf;
    }

    public Parcel getParcel(){
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

        TextView cityText = view.findViewById(R.id.text_city_in_weather_fragment);
        TextView weatherText = view.findViewById(R.id.text_weather_in_weather_fragment);
        RecyclerView recyclerDate = view.findViewById(R.id.recycler_date_in_weather_fragment);
        RecyclerView recyclerWeather = view.findViewById(R.id.recycler_weather_in_weather_fragment);
        if(savedInstanceState == null) {
            String[] dateArray = {"31.08.2020", "01.09.2020", "02.09.2020"};
            DateAdapter dateAdapter = new DateAdapter(dateArray);
            recyclerDate.setAdapter(dateAdapter);
            dateAdapter.setDate(dateArray);

            Parcel parcel = getParcel();

            cityText.setText(parcel.getCityName());
            weatherText.setText(weatherArray(parcel.getWeatherIndex()));
        }


    }
    private String weatherArray(int index){
        String[] weather = getResources().getStringArray(R.array.weatherArray);
        String currentWeather = "";
        for (int i = 0; i <weather.length ; i++) {
            if(i == index){
                currentWeather = weather[i];
            }
        }
        return currentWeather;
    }
}

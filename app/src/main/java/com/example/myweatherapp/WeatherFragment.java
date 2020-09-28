package com.example.myweatherapp;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myweatherapp.model.WeatherRequest;
import com.squareup.picasso.Picasso;

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
        final View imageView = view.findViewById(R.id.image_weather);
        final Parcel parcel = getParcel();

        if(Integer.parseInt(parcel.getWeatherIndex()) < 15){

            Picasso.get().load("https://i.pinimg.com/originals/47/cc/2d/47cc2d183ab7510afa1f2fce246e9c17.jpg")
                    .into((ImageView) imageView);
        } else if (Integer.parseInt(parcel.getWeatherIndex()) > 15){
            Picasso.get().load("https://cdn.pixabay.com/photo/2016/11/14/19/44/sun-1824469_1280.jpg")
                    .into((ImageView) imageView);
        }


        cityText.setText(String.format("%s",parcel.getCityName()));
        weatherText.setText(String.format("%s °C",parcel.getWeatherIndex()));
        pressure.setText(String.format("%s мм рт. ст.",parcel.getPressure()));
        humidity.setText(String.format("%s ",parcel.getHumidity() + " %"));
        windSpeed.setText(String.format("%s м/с",parcel.getWindSpeed()));







    }
}

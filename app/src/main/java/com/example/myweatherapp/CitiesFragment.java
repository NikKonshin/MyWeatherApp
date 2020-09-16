package com.example.myweatherapp;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myweatherapp.model.WeatherRequest;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CitiesFragment extends Fragment implements Constants {
    private boolean isLandscape;
    private Parcel parcel;
    private String[] citiesArray;

    private final HistoryAdapter historyAdapter = new HistoryAdapter();
    private final SaveAdapter saveAdapter = SaveAdapter.getInstanceAdapter();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cities, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinator_layout);
        initList(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;

        if (savedInstanceState != null) {

            if (savedInstanceState.getSerializable(CURRENT_CITY) instanceof Parcel) {

                parcel = (Parcel) savedInstanceState.getSerializable(CURRENT_CITY);

            } else {
                parcel = new Parcel(getResources().getStringArray(R.array.citiesArray)[FIRST_ELEMENT],
                        getResources().getStringArray(R.array.citiesArray)[FIRST_ELEMENT]);
            }
        } else {
            parcel = new Parcel(getResources().getStringArray(R.array.citiesArray)[FIRST_ELEMENT],
                    getResources().getStringArray(R.array.citiesArray)[FIRST_ELEMENT]);
        }

        if (isLandscape) {
            showWeather(parcel);
        }
    }


    private void initList(View view, Bundle savedInstanceState) {

        final RecyclerView recyclerCity = view.findViewById(R.id.recyclerview_cities);
        citiesArray = getResources().getStringArray(R.array.citiesArray);
        final CitiesAdapter citiesAdapter = new CitiesAdapter(citiesArray);
        recyclerCity.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerCity.setAdapter(citiesAdapter);
//        if (savedInstanceState == null){
        citiesAdapter.setCities(citiesArray);
        citiesAdapter.setOnCityClickListener(new CitiesAdapter.OnCityClickListener() {
            @Override
            public void onClicked(View view, final int position) {
                final WorkWithApi workWithApi = new WorkWithApi();
                final Handler handler = new Handler();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final WeatherRequest weatherRequest = workWithApi.getWeather(getResources().getStringArray(R.array.citiesArray)[position]);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {

                                    String city = String.format("%s", weatherRequest.getName());
                                    String weather = String.format("%s °C", (int) weatherRequest.getMain().getTemp() - 273);
                                    String pressure = String.format("%s мм рт. ст.", (int) (weatherRequest.getMain().getPressure() / 1.332894736842105));
                                    String humidity = String.format("%s", weatherRequest.getMain().getHumidity()) + " %";
                                    String windSpeed = String.format("%s м/с", (int) weatherRequest.getWind().getSpeed());


                                    historyAdapter.addItem(city, (int) weatherRequest.getMain().getTemp() - 273);
                                    saveAdapter.saveAdapter(historyAdapter);

                                    parcel = new Parcel(city, weather, pressure, humidity, windSpeed);
                                    if ((int) weatherRequest.getMain().getTemp() - 273 <= 11) {
                                        ((MainActivity) getActivity()).onClickDialogBuilder(recyclerCity);
                                    }
                                    showWeather(parcel);
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();

                        } finally {
                            workWithApi.closeConnection();
                        }

                    }

                }).start();

            }
        });

    }

    private void showWeather(final Parcel parcel) {
        if (isLandscape) {
            WeatherFragment weatherFragment = (WeatherFragment) getFragmentManager().findFragmentById(R.id.weather_fragment);
            if (weatherFragment == null || weatherFragment.getParcel().getWeatherIndex() != parcel.getWeatherIndex()) {
                weatherFragment = WeatherFragment.create(parcel);
                weatherFragment.setTargetFragment(this, REQUEST_CODE);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.weather_land, weatherFragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();

            }
        } else {
            WeatherFragment weatherFragment = (WeatherFragment) getFragmentManager().findFragmentById(R.id.weather_fragment);
            if (weatherFragment == null || !weatherFragment.getParcel().getWeatherIndex().equals(parcel.getWeatherIndex())) {
                weatherFragment = WeatherFragment.create(parcel);
                weatherFragment.setTargetFragment(this, REQUEST_CODE);
                FragmentTransaction ft = getFragmentManager().beginTransaction();

                ft.replace(R.id.fragment_container, weatherFragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        }
    }
}


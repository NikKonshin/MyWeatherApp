package com.example.myweatherapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CitiesFragment extends Fragment implements Constants {
    private boolean isLandscape;
    private Parcel parcel;
    private String[] citiesArray;
    //private final HistoryAdapter historyAdapter = new HistoryAdapter();
    private HistoryAdapter historyAdapter;
    private HistoryRequestSource historyRequestSource;
    private final SaveAdapter saveAdapter = SaveAdapter.getInstanceAdapter();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cities, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);

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


    private void initList(View view) {

        final RecyclerView recyclerCity = view.findViewById(R.id.recyclerview_cities);
        citiesArray = getResources().getStringArray(R.array.citiesArray);
        final CitiesAdapter citiesAdapter = new CitiesAdapter(citiesArray);
        recyclerCity.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerCity.setAdapter(citiesAdapter);
        citiesAdapter.setCities(citiesArray);
        citiesAdapter.setOnCityClickListener(new CitiesAdapter.OnCityClickListener() {

            @Override
            public void onClicked(View view, final int position) {

                final Handler handler = new Handler();
                final MyThreadClass myThreadClass = new MyThreadClass("MyThreadClass");
                HistoryDao historyDao = App.getInstance().getHistoryDao();
                historyRequestSource = new HistoryRequestSource(historyDao);

                Runnable task = new Runnable() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (myThreadClass.getWeather() != null) {
                                    HistoryRequest historyRequest = new HistoryRequest();

                                    historyRequest.city_name = myThreadClass.getCity();
                                    historyRequest.temperature = myThreadClass.getWeather();
                                    historyRequestSource.addHistory(historyRequest);

                                    historyAdapter = new HistoryAdapter(historyRequestSource);
//                                    historyAdapter.addItem(myThreadClass.getCity(), Integer.parseInt(myThreadClass.getWeather()));
                                    saveAdapter.saveAdapter(historyAdapter);
                                    if (Integer.parseInt(myThreadClass.getWeather()) <= 11) {
                                        ((MainActivity) getActivity()).onClickDialogBuilder(recyclerCity,myThreadClass.getCity());
                                    }
                                }
                                parcel = new Parcel(myThreadClass.getCity(), myThreadClass.getWeather(), myThreadClass.getPressure(),
                                        myThreadClass.getHumidity(), myThreadClass.getWindSpeed());
                                myThreadClass.quit();
                                showWeather(parcel);
                            }
                        });

                    }
                };

                myThreadClass.start();
                myThreadClass.prepareHandler();
                myThreadClass.setCity(getResources().getStringArray(R.array.citiesArray)[position]);
                myThreadClass.getWeatherData();
                myThreadClass.startFragmentWithData(task);

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



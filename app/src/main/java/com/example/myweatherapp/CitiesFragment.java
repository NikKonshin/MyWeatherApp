package com.example.myweatherapp;

import android.graphics.Color;
import android.os.Bundle;
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

import com.google.android.material.snackbar.Snackbar;

public class CitiesFragment extends Fragment implements Constants {
    boolean isLandscape;
    Parcel parcel;
    private String[] citiesArray;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cities,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinator_layout);
        initList(view,savedInstanceState);

    }



    private void initList(View view, Bundle savedInstanceState){

        RecyclerView recyclerCity = view.findViewById(R.id.recyclerview_cities);
        citiesArray = getResources().getStringArray(R.array.citiesArray);
        final CitiesAdapter citiesAdapter = new CitiesAdapter(citiesArray);
        recyclerCity.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        recyclerCity.setAdapter(citiesAdapter);
        if (savedInstanceState == null){
            citiesAdapter.setCities(citiesArray);
            citiesAdapter.setOnCityClickListener(new CitiesAdapter.OnCityClickListener() {
                @Override
                public void onClicked(View view, int position) {
                    parcel = new Parcel(getResources().getStringArray(R.array.citiesArray)[position], position);
                    Snackbar snackbar = Snackbar.make(view, String.format("Перейти %s ?",parcel.getCityName() ),Snackbar.LENGTH_LONG)
                            .setAction("Ok", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showWeather(parcel);
                                }
                            });
                    snackbar.setActionTextColor(Color.YELLOW);
                    snackbar.show();



                }


            });
            }
        }

     private void showWeather(Parcel parcel) {
        WeatherFragment weatherFragment = (WeatherFragment) getFragmentManager().findFragmentById(R.id.weather_fragment);
        if (weatherFragment == null || weatherFragment.getParcel().getWeatherIndex() != parcel.getWeatherIndex()){
            weatherFragment = WeatherFragment.create(parcel);
            weatherFragment.setTargetFragment(this,REQUEST_CODE);

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, weatherFragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();
        }
     }
}


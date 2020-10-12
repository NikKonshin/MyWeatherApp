package com.example.myweatherapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myweatherapp.model.WeatherRequest;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

public class CitiesFragment extends Fragment implements Constants {
    private boolean isLandscape;
    private Parcel parcel;
    private String[] citiesArray;
    private HistoryAdapter historyAdapter;
    private HistoryRequestSource historyRequestSource;
    private final SaveAdapter saveAdapter = SaveAdapter.getInstanceAdapter();

    private final int PERMISSION_REQUEST_CODE = 100;
    private List<Address> addresses;
    private Location location;
    private LatLng cityLocation;
    final String[] city = new String[1];
    private String TAG = "CITY123456";
    private WeatherRequest weatherRequest;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cities, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestPemissions();
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
        Button buttonMyGeo = view.findViewById(R.id.button_my_geo);

        buttonMyGeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                myThreadClass.setLatLng(cityLocation);
                myThreadClass.getWeatherDataLatLng(getContext());
                myThreadClass.startFragmentWithData(task);
//                myThreadClass.showNotification(getContext());





                Log.d(TAG,String.format("%s, %s",addresses,"onClick"));
            }
        });

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
                myThreadClass.getWeatherData(getContext());
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


    public void setLocation(Location location) {
        this.location = location;
       // getAddress(location);

    }

    private void getAddress(final Location location){
        final Geocoder geocoder = new Geocoder(getContext());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void requestPemissions() {

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            requestLocation();
        } else {
            requestLocationPermissions();
        }
    }
    private void requestLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;
        LocationManager locationManager = (LocationManager)
                getContext().getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);

        String provider = locationManager.NETWORK_PROVIDER;
        if (provider != null) {
            locationManager.requestLocationUpdates(provider, 0, 10,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            cityLocation = new LatLng(location.getLatitude(),location.getLongitude());
                            setLocation(location);
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {
                        }

                        @Override
                        public void onProviderEnabled(String provider) {
                        }

                        @Override
                        public void onProviderDisabled(String provider) {
                        }
                    });
        }
    }

    private void requestLocationPermissions() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                || !ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    PERMISSION_REQUEST_CODE);
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length == 2 &&
                    (grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED)) {

                requestLocation();
            }
        }
    }




}



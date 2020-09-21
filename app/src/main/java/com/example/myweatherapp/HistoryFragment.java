package com.example.myweatherapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryFragment extends Fragment implements Constants{

    public static HistoryFragment create(Parcel parcel) {
        HistoryFragment hf = new HistoryFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(PARCEL, parcel);
        hf.setArguments(bundle);
        return hf;
    }

    public Parcel getParcel() {
        Parcel parcel = (Parcel) getArguments().getSerializable(PARCEL);
        return parcel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinator_layout);
        initList(view,savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }



    private void initList(View view, Bundle savedInstanceState){

        RecyclerView recyclerWeather = view.findViewById(R.id.recyclerview_history);
        final SaveAdapter saveAdapter = SaveAdapter.getInstanceAdapter();
        final HistoryAdapter weatherAdapter = (HistoryAdapter) saveAdapter.getAdapter();;
        recyclerWeather.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        recyclerWeather.setAdapter(weatherAdapter);
//        if (savedInstanceState == null){





//            }
    }

//    private void showWeather(Parcel parcel) {
//            HistoryFragment historyFragment = (HistoryFragment) getFragmentManager().findFragmentById(R.id.weather_fragment);
//            if (weatherFragment == null || weatherFragment.getParcel().getWeatherIndex() != parcel.getWeatherIndex()) {
//                weatherFragment = WeatherFragment.create(parcel);
//                weatherFragment.setTargetFragment(this, REQUEST_CODE);
//
//                FragmentTransaction ft = getFragmentManager().beginTransaction();
//                ft.replace(R.id.fragment_container, weatherFragment);
//                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//                ft.addToBackStack(null);
//                ft.commit();
//            }
//        }

}

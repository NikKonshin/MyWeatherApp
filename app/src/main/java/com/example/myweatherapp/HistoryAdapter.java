package com.example.myweatherapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{
   private List<String> citiesArray = new ArrayList<>();
   private List<Integer> weatherArray = new ArrayList<>();

//    public HistoryAdapter(List<String> citiesArray, List<Integer> weatherArray) {
//        this.citiesArray = citiesArray;
//        this.weatherArray = weatherArray;
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(citiesArray.get(position), weatherArray.get(position));
    }

    @Override
    public int getItemCount() {
        if(citiesArray == null){
            return 0;
        }
        return citiesArray.size();
    }

     void addItem(String city, Integer weather){
        if(city != null) {
            citiesArray.add(city);
            notifyItemInserted(citiesArray.size() - 1);
            weatherArray.add(weather);
            notifyItemInserted(weatherArray.size() - 1);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView cityView;
        TextView weatherView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cityView = itemView.findViewById(R.id.city_history);
            weatherView = itemView.findViewById(R.id.weather_history);
        }

        void bind(final String city, final Integer weather){
            cityView.setText(city);
            weatherView.setText(String.format("%s °C", (weather)));
        }
        public TextView getCityView() {
            return cityView;
        }

        public TextView getWeatherView() {
            return weatherView;
        }
    }
}

package com.example.myweatherapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.CityViewHolder> implements Constants {

    private String[] cities;
    private OnCityClickListener onCityClickListener;



    public CitiesAdapter(String[] cities){
        this.cities = cities;
    }

    public void setCities(String[] cities) {
        this.cities = cities;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CityViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_city,parent,false));
    }

    public void setOnCityClickListener(OnCityClickListener onCityClickListener) {
        this.onCityClickListener = onCityClickListener;
    }
    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        holder.bind(cities[position],position, onCityClickListener);
    }


    @Override
    public int getItemCount() {
    if (cities == null){
        return 0;
    }
    return cities.length;
    }

    static class CityViewHolder extends RecyclerView.ViewHolder {
            MaterialButton buttonCity;

            public CityViewHolder(@NonNull View itemView) {
                super(itemView);
                buttonCity = itemView.findViewById(R.id.button_for_the_city);

            }

            void bind(final String city, final int position , final OnCityClickListener onCityClickListener){
                buttonCity.setText(city);
                buttonCity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int adapterPosition = getAdapterPosition();
                        if(adapterPosition == RecyclerView.NO_POSITION){
                            return;
                        }
                        if (onCityClickListener != null){
                            onCityClickListener.onClicked(v, adapterPosition);
                        }
                    }
                });
            }
        }


    interface OnCityClickListener {
        void onClicked(View view, final int position);
    }
}

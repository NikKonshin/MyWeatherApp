package com.example.myweatherapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.DateViewHolder> {

    private String[] date;
    private OnDateClickListener onDateClickListener;



    public DateAdapter(String[] date){
        this.date = date;
    }

    public void setDate(String[] date) {
        this.date = date;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DateAdapter.DateViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dates,parent,false));
    }

    public void setOnDateClickListener(DateAdapter.OnDateClickListener onDateClickListener) {
        this.onDateClickListener = onDateClickListener;
    }
    @Override
    public void onBindViewHolder(@NonNull DateAdapter.DateViewHolder holder, int position) {
        holder.bind(date[position],position,  onDateClickListener);
    }


    @Override
    public int getItemCount() {
        if (date == null){
            return 0;
        }
        return date.length;
    }

    static class DateViewHolder extends RecyclerView.ViewHolder {
        TextView dateText;

        public DateViewHolder(@NonNull View itemView) {
            super(itemView);
            dateText = itemView.findViewById(R.id.text_data_item);

        }

        void bind(final String date, final int position , final DateAdapter.OnDateClickListener onDateClickListener){
            dateText.setText(date);
            dateText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onDateClickListener != null){
                        onDateClickListener.onClicked(date, position);
                    }
                }
            });
        }
    }


    interface OnDateClickListener {
        void onClicked(String date, final int position);
    }
}


package com.example.myweatherapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

import androidx.core.app.NotificationCompat;

public class NetworkReceiver extends BroadcastReceiver {
    private int messageId = 10000;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!checkNetwork(context)) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "2")
                    .setSmallIcon(R.drawable.ic_info_black_24dp)
                    .setContentTitle("MyWeatherMap")
                    .setContentText("Connection is Full");
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(messageId++, builder.build());
        }
        }

     private boolean checkNetwork(Context context){
         ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
         NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
         return (networkInfo != null && networkInfo.isConnected());
    }
}

package com.example.myweatherapp;

import android.app.ApplicationErrorReport;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import androidx.core.app.NotificationCompat;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private int messageId = 1000;
    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getAction();


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"2").setSmallIcon(R.drawable.ic_info_black_24dp).setContentTitle("Warring");

        if(message == "android.intent.action.BATTERY_LOW"){
            builder.setContentText("Battery low");
        }
        if (message == "android.intent.action.AIRPLANE_MODE"){
            builder.setContentText("Fail Connection");
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(messageId++, builder.build());
    }
}

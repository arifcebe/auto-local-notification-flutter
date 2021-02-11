package com.arifcebe.auto_local_notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmManagerReceiver  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        /// TODO show notification when time has come
        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            builder = new NotificationCompat.Builder(context,"Azan");
            builder.setPriority(NotificationManager.IMPORTANCE_HIGH);
        } else {
            builder = new NotificationCompat.Builder(context);    
        }
        Log.d("receive alarm","receive alarm show notif");
        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Alarm")
                .setContentText("Waktunya sholat")
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(5,builder.build());
    }
}

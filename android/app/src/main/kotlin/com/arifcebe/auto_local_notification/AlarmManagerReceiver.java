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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AlarmManagerReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        /// TODO show notification when time has come
        String type = intent.getStringExtra("type");
        assert type != null;
        if (type.equals("repeating")) {
            // TODO just repeat
            NotificationCompat.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                builder = new NotificationCompat.Builder(context, "Azan");
                builder.setPriority(NotificationManager.IMPORTANCE_HIGH);
            } else {
                builder = new NotificationCompat.Builder(context);
            }
            long showTime = intent.getLongExtra("setShowNotif", 0);
            long currentTime = System.currentTimeMillis();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.UK);
            String formattedTime = simpleDateFormat.format(new Date(showTime));
            String currentFormattedTime = simpleDateFormat.format(new Date(currentTime));
            Log.d("receive alarm", "receive alarm show notif " + formattedTime + " " + currentFormattedTime);
            builder.setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setWhen(currentTime)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Alarm")
                    .setContentText("Waktunya sholat " + formattedTime + " " + currentFormattedTime)
                    .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setOngoing(false);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(5, builder.build());
        } else {
            // TODO exact repeating
            NotificationCompat.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                builder = new NotificationCompat.Builder(context, "Azan");
                builder.setPriority(NotificationManager.IMPORTANCE_HIGH);
            } else {
                builder = new NotificationCompat.Builder(context);
            }
            long showTime = intent.getLongExtra("setShowNotif", 0);
            long currentTime = System.currentTimeMillis();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.UK);
            String formattedTime = simpleDateFormat.format(new Date(showTime));
            String currentFormattedTime = simpleDateFormat.format(new Date(currentTime));
            Log.d("receive alarm exact", "receive alarm show notif " + formattedTime + " " + currentFormattedTime);
            builder.setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setWhen(currentTime)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Alarm Exact")
                    .setContentText("Exact || Waktunya sholat " + formattedTime + " " + currentFormattedTime)
                    .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setOngoing(false);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(6, builder.build());
        }
    }
}

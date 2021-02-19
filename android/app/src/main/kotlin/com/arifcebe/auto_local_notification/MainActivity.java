package com.arifcebe.auto_local_notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodChannel;

public class MainActivity extends FlutterActivity {

    static final String CHANNEL_NAME = "auto_notif/repeat_time";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        MethodChannel methodChannel = new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL_NAME);
        methodChannel.setMethodCallHandler((call, result) -> {
            if (call.method.equals("startAlarm")) {
                /// TODO running function for start alarm manager
                setAlarmManager();
                result.success(true);
            } else if (call.method.equals("startExactAlarm")) {
                String time = call.argument("timeToShow");
                AlarmService alarmService = new AlarmService(this);
                alarmService.setExactAlarm(time);
            }
        });
    }

    private void setAlarmManager() {
        /// TODO create notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel =
                    new NotificationChannel("Azan", "show notif",
                            NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("Notification for Aminin");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent;
        PendingIntent pendingIntent;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 36);

        long setShowNotif = System.currentTimeMillis() + (1000 * 60);
        intent = new Intent(this, AlarmManagerReceiver.class);
        intent.putExtra("setShowNotif", setShowNotif);
        intent.putExtra("type", "repeating");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.UK);
        String formattedTime = simpleDateFormat.format(new Date(setShowNotif));
        Log.d("method_channel", "run set alarm " + formattedTime);
        pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, setShowNotif, 1000 * 3600, pendingIntent);
    }

    private void setExactAlarm() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel =
                    new NotificationChannel("Azan 2", "Azan Exact Time", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("Exact Notification for Aminin");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }


        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmManagerReceiver.class);
        intent.putExtra("type","exact");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,2, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, 32423, pendingIntent);
    }
}

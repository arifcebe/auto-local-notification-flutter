package com.arifcebe.auto_local_notification;

import android.app.AlarmManager;
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
        MethodChannel methodChannel = new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(),CHANNEL_NAME);
        methodChannel.setMethodCallHandler((call, result) -> {
            if (call.method.equals("startAlarm")){
                /// TODO running function for start alarm manager
                setAlarmManager();
                result.success(true);
            }
        });
    }

    private void setAlarmManager(){

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
        calendar.set(Calendar.HOUR_OF_DAY,11);
        calendar.set(Calendar.MINUTE,36);

        long setShowNotif = System.currentTimeMillis() + (1000 * 60);
        intent = new Intent(this,AlarmManagerReceiver.class);
        intent.putExtra("setShowNotif", setShowNotif);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.UK);
        String formattedTime = simpleDateFormat.format(new Date(setShowNotif));
        Log.d("method_channel","run set alarm " + formattedTime);
        pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, setShowNotif,1000 * 60,pendingIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, setShowNotif, pendingIntent);
        }
//        alarmManager.setAndAllowWhileIdle(AlarmManager.RTC, System.currentTimeMillis(), 1000 * 60, pendingIntent);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, setShowNotif, pendingIntent);
//        }
    }
}

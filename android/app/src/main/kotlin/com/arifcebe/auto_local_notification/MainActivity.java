package com.arifcebe.auto_local_notification;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Calendar;

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
                Log.d("method_channel","run set alarm");
                setAlarmManager();
                result.success(true);
                enableAutoStart();
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

        intent = new Intent(this,AlarmManagerReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),1000 * 60,pendingIntent);
    }
    private void enableAutoStart() {
        Log.d("check devices","devicesnya "+Build.BRAND);
        if (Build.BRAND.equalsIgnoreCase("Xiaomi")) {
            Log.d("xiaomi devices","AutoStart need authenticate");
            new AlertDialog.Builder(MainActivity.this).setTitle("Enable AutoStart")
                    .setMessage(
                            "Please allow AppName to always run in the background,else our services can't be accessed.")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent();
                            intent.setComponent(new ComponentName("com.miui.securitycenter",
                                    "com.miui.permcenter.autostart.AutoStartManagementActivity"));
                            startActivity(intent);
                        }
                    }).show();
        }else if (Build.BRAND.equalsIgnoreCase("Redmi")) {
            Log.d("xiaomi devices","AutoStart need authenticate");
            new AlertDialog.Builder(MainActivity.this).setTitle("Enable AutoStart")
                    .setMessage(
                            "Please allow AppName to always run in the background,else our services can't be accessed.")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent();
                            intent.setComponent(new ComponentName("com.miui.securitycenter",
                                    "com.miui.permcenter.autostart.AutoStartManagementActivity"));
                            startActivity(intent);
                        }
                    }).show();
        }else if (Build.MANUFACTURER.equalsIgnoreCase("oppo")) {
            new AlertDialog.Builder(MainActivity.this).setTitle("Enable AutoStart")
                    .setMessage(
                            "Please allow AppName to always run in the background,else our services can't be accessed.")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                Intent intent = new Intent();
                                intent.setClassName("com.coloros.safecenter",
                                        "com.coloros.safecenter.permission.startup.StartupAppListActivity");
                                startActivity(intent);
                            } catch (Exception e) {
                                try {
                                    Intent intent = new Intent();
                                    intent.setClassName("com.oppo.safe",
                                            "com.oppo.safe.permission.startup.StartupAppListActivity");
                                    startActivity(intent);
                                } catch (Exception ex) {
                                    try {
                                        Intent intent = new Intent();
                                        intent.setClassName("com.coloros.safecenter",
                                                "com.coloros.safecenter.startupapp.StartupAppListActivity");
                                        startActivity(intent);
                                    } catch (Exception exx) {

                                    }
                                }
                            }
                        }
                    }).show();
        } else if (Build.MANUFACTURER.contains("vivo")) {
            new AlertDialog.Builder(MainActivity.this).setTitle("Enable AutoStart")
                    .setMessage(
                            "Please allow AppName to always run in the background.Our app runs in background else our services can't be accesed.")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                Intent intent = new Intent();
                                intent.setComponent(new ComponentName("com.iqoo.secure",
                                        "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity"));
                                startActivity(intent);
                            } catch (Exception e) {
                                try {
                                    Intent intent = new Intent();
                                    intent.setComponent(new ComponentName("com.vivo.permissionmanager",
                                            "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"));
                                    startActivity(intent);
                                } catch (Exception ex) {
                                    try {
                                        Intent intent = new Intent();
                                        intent.setClassName("com.iqoo.secure",
                                                "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager");
                                        startActivity(intent);
                                    } catch (Exception exx) {
                                        ex.printStackTrace();
                                    }
                                }
                            }
                        }
                    }).show();
        }
    }

}

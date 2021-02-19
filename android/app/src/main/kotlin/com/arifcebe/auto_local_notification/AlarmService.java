package com.arifcebe.auto_local_notification;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AlarmService {
    final private Context context;

    public AlarmService(Context context) {
        this.context = context;
    }

    public void setExactAlarm(String timeToShow) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel =
                    new NotificationChannel("Azan 2", "Azan Exact Time", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("Exact Notification for Aminin");
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.UK);
        Date date;
        try {
            date = simpleDateFormat.parse(timeToShow);
            Calendar calendar = Calendar.getInstance();
            assert date != null;
            calendar.setTime(date);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, AlarmManagerReceiver.class);
            intent.putExtra("type","exact");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,2, intent, 0);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

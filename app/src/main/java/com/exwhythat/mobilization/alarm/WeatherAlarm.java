package com.exwhythat.mobilization.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.exwhythat.mobilization.util.SettingPrefs;

/**
 * Class, which simplifies weather update scheduling
 */

public class WeatherAlarm {

    public static void setAlarm(Context context) {
        int intervalSeconds = SettingPrefs.getSettingsUpdateInterval(context);
        setAlarm(context, intervalSeconds);
    }

    public static void setAlarm(Context context, int intervalSeconds) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, WeatherAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        // Cancel previous alarm
        alarmManager.cancel(pendingIntent);

        alarmManager.setInexactRepeating(
                AlarmManager.ELAPSED_REALTIME, // Do not wake device, let it sleep
                SystemClock.elapsedRealtime() + intervalSeconds * 1000, // When to fire first time
                intervalSeconds * 1000, // How often
                pendingIntent); // What to execute
    }
}

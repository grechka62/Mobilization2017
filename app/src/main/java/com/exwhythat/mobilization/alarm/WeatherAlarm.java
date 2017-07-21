package com.exwhythat.mobilization.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.IntDef;

import com.exwhythat.mobilization.util.SettingPrefs;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Class, which simplifies weather update scheduling
 */

public class WeatherAlarm {

    @IntDef({UpdateInterval.SECONDS_ONE, UpdateInterval.SECONDS_TEN, UpdateInterval.MINUTES_ONE,
            UpdateInterval.MINUTES_TEN, UpdateInterval.MINUTES_THIRTY, UpdateInterval.HOURS_ONE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface UpdateInterval {
        int SECONDS_ONE = 1;
        int SECONDS_TEN = 10;
        int MINUTES_ONE = 60;
        int MINUTES_TEN = 60 * 10;
        int MINUTES_THIRTY = 60 * 30;
        int HOURS_ONE = 60 * 60;
    }

    public static void setAlarm(Context context) {
        @UpdateInterval int intervalSeconds = SettingPrefs.getSettingsUpdateInterval(context);
        setAlarm(context, intervalSeconds);
    }

    public static void setAlarm(Context context, @UpdateInterval int intervalSeconds) {
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

package com.exwhythat.mobilization.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.exwhythat.mobilization.BuildConfig;

import timber.log.Timber;

import static com.exwhythat.mobilization.alarm.WeatherAlarm.*;

/**
 * Helper class for setting preferences
 */

public class SettingPrefs {

    private static final String PREF_NAME_SETTINGS = "WEATHER_APP_SETTINGS_PREF";
    private static final String KEY_SETTINGS_UPDATE_INTERVAL = "KEY_SETTINGS_UPDATE_INTERVAL";

    private static final int UPDATE_INTERVAL_DEFAULT_DEBUG = UpdateInterval.SECONDS_TEN;
    private static final int UPDATE_INTERVAL_DEFAULT_RELEASE = UpdateInterval.MINUTES_THIRTY;

    public static SharedPreferences getSettingsPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME_SETTINGS, Context.MODE_PRIVATE);
    }

    public static void putSettingsUpdateInterval(Context context, @UpdateInterval int interval) {
        getSettingsPrefs(context).edit()
                .putInt(KEY_SETTINGS_UPDATE_INTERVAL, interval)
                .apply();
        Timber.d("New update interval has been written into SharedPreferences: " + interval);
    }

    public static int getSettingsUpdateInterval(Context context) {
        int defaultSeconds = /*BuildConfig.DEBUG ? UPDATE_INTERVAL_DEFAULT_DEBUG :*/ UPDATE_INTERVAL_DEFAULT_RELEASE;
        return getSettingsPrefs(context).getInt(KEY_SETTINGS_UPDATE_INTERVAL, defaultSeconds);
    }
}

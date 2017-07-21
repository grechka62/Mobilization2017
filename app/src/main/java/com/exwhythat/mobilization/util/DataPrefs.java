package com.exwhythat.mobilization.util;

import android.content.Context;
import android.content.SharedPreferences;

import timber.log.Timber;

/**
 * Helper class for data preferences
 */

public class DataPrefs {

    private static final String PREF_NAME_DATA = "WEATHER_APP_DATA_PREF";
    private static final String KEY_WEATHER_DATA = "KEY_WEATHER_DATA";

    public static SharedPreferences getDataPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME_DATA, Context.MODE_PRIVATE);
    }

    public static void putWeatherData(Context context, String jsonData) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME_DATA, Context.MODE_PRIVATE);
        prefs.edit()
                .putString(KEY_WEATHER_DATA, jsonData)
                .apply();
        Timber.d("New weather data has been written into SharedPreferences: " + jsonData);
    }

    public static String getWeatherDataAsJsonString(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME_DATA, Context.MODE_PRIVATE);
        return prefs.getString(KEY_WEATHER_DATA, "");
    }
}

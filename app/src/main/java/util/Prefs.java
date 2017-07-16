package util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by exwhythat on 16.07.17.
 */

public class Prefs {

    private static final String PREF_NAME = "WEATHER_APP_DATA_PREF";
    private static final String KEY_WEATHER_DATA = "KEY_WEATHER_DATA";

    public static final String KEY_SETTING_UPDATE_INTERVAL = "KEY_WEATHER_DATA";

    public static SharedPreferences getWeatherDataPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static void putWeatherData(Context context, String jsonData) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit()
                .putString(KEY_WEATHER_DATA, jsonData)
                .apply();
    }

    public static String getWeatherDataAsJsonString(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_WEATHER_DATA, "");
    }

    public static SharedPreferences getDefaultPrefs(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void putSettingsUpdateInterval(Context context, int interval) {
        SharedPreferences prefs = getDefaultPrefs(context);
        prefs.edit()
                .putInt(KEY_SETTING_UPDATE_INTERVAL, interval)
                .apply();
    }

    public static int getSettingsUpdateInterval(Context context) {
        return getDefaultPrefs(context).getInt(KEY_SETTING_UPDATE_INTERVAL, -1);
    }
}

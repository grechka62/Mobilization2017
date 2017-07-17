package util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by exwhythat on 16.07.17.
 */

public class Prefs {

    private static final String PREF_NAME_DATA = "WEATHER_APP_DATA_PREF";
    private static final String KEY_WEATHER_DATA = "KEY_WEATHER_DATA";

    public static final String PREF_NAME_SETTINGS = "WEATHER_APP_SETTINGS_PREF";
    public static final String KEY_SETTINGS_UPDATE_INTERVAL = "KEY_SETTINGS_UPDATE_INTERVAL";

    public static SharedPreferences getDataPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME_DATA, Context.MODE_PRIVATE);
    }

    public static void putWeatherData(Context context, String jsonData) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME_DATA, Context.MODE_PRIVATE);
        prefs.edit()
                .putString(KEY_WEATHER_DATA, jsonData)
                .apply();
    }

    public static String getWeatherDataAsJsonString(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME_DATA, Context.MODE_PRIVATE);
        return prefs.getString(KEY_WEATHER_DATA, "");
    }

    public static SharedPreferences getSettingsPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME_SETTINGS, Context.MODE_PRIVATE);
    }

    public static void putSettingsUpdateInterval(Context context, int interval) {
        getSettingsPrefs(context).edit()
                .putInt(KEY_SETTINGS_UPDATE_INTERVAL, interval)
                .apply();
    }

    public static int getSettingsUpdateInterval(Context context) {
        return getSettingsPrefs(context).getInt(KEY_SETTINGS_UPDATE_INTERVAL, -1);
    }
}

package util;

import android.content.Context;
import android.content.SharedPreferences;

import com.exwhythat.mobilization.BuildConfig;

/**
 * Helper class for setting preferences
 */

public class SettingPrefs {

    public static final String PREF_NAME_SETTINGS = "WEATHER_APP_SETTINGS_PREF";
    public static final String KEY_SETTINGS_UPDATE_INTERVAL = "KEY_SETTINGS_UPDATE_INTERVAL";

    public static final String EXTRA_UPDATE_INTERVAL_SECONDS = "com.exwhythat.mobilization.service.EXTRA_UPDATE_INTERVAL_SECONDS";

    private static final int INTERVAL_SECONDS_DEFAULT = 30 * 60;

    private static final int INTERVAL_SECONDS_DEBUG = 10;

    public static SharedPreferences getSettingsPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME_SETTINGS, Context.MODE_PRIVATE);
    }

    public static void putSettingsUpdateInterval(Context context, int interval) {
        getSettingsPrefs(context).edit()
                .putInt(KEY_SETTINGS_UPDATE_INTERVAL, interval)
                .apply();
    }

    public static int getSettingsUpdateInterval(Context context) {
        int defaultSeconds;
        if (BuildConfig.DEBUG) {
            defaultSeconds = INTERVAL_SECONDS_DEBUG;
        } else {
            defaultSeconds = INTERVAL_SECONDS_DEFAULT;
        }
        return getSettingsPrefs(context).getInt(KEY_SETTINGS_UPDATE_INTERVAL, defaultSeconds);
    }
}

package com.exwhythat.mobilization.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.exwhythat.mobilization.model.CityInfo;

import timber.log.Timber;

/**
 * Created by Grechka on 28.07.2017.
 */

public class CityPrefs {
    private static final String PREF_NAME_CITY = "WEATHER_APP_CITY_PREF";
    private static final String KEY_CITY = "KEY_CITY";

    private static final String KEY_CITY_TITLE = "KEY_CITY_TITLE";
    private static final String KEY_CITY_LATITUDE = "KEY_CITY_LATITUDE";
    private static final String KEY_CITY_LONGITUDE = "KEY_CITY_LONGITUDE";

    private static SharedPreferences getCityPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME_CITY, Context.MODE_PRIVATE);
    }

    public static void putCity(Context context, CityInfo cityInfo) {
        getCityPrefs(context).edit()
                .putString(KEY_CITY_TITLE, cityInfo.getName())
                .putFloat(KEY_CITY_LATITUDE, (float) cityInfo.getLocation().getLat())
                .putFloat(KEY_CITY_LONGITUDE, (float) cityInfo.getLocation().getLng())
                .apply();
        Timber.d("New city information has been written into SharedPreferences: " + cityInfo);
    }

    public static CityInfo getCity(Context context) {
        SharedPreferences prefs = getCityPrefs(context);
        String cityName = prefs.getString(KEY_CITY_TITLE, "Moscow");
        double latitude = (double) prefs.getFloat(KEY_CITY_LATITUDE, (float) 55.75222);
        double longitude = (double) prefs.getFloat(KEY_CITY_LONGITUDE, (float) 37.615555);
        return new CityInfo(cityName, latitude, longitude);
    }
}

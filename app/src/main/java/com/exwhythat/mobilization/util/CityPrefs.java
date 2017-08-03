package com.exwhythat.mobilization.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.exwhythat.mobilization.model.City;

import timber.log.Timber;

/**
 * Created by Grechka on 28.07.2017.
 */

public class CityPrefs {
    private static final String PREF_NAME_CITY = "WEATHER_APP_CITY_PREF";

    private static final String KEY_CITY_TITLE = "KEY_CITY_TITLE";
    private static final String VALUE_CITY_TITLE = "Moscow";

    private static final String KEY_CITY_LATITUDE = "KEY_CITY_LATITUDE";
    private static final double VALUE_CITY_LATITUDE = 55.75222;

    private static final String KEY_CITY_LONGITUDE = "KEY_CITY_LONGITUDE";
    private static final double VALUE_CITY_LONGITUDE = 37.615555;

    private static SharedPreferences getCityPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME_CITY, Context.MODE_PRIVATE);
    }

    public static void putCity(Context context, City city) {
        getCityPrefs(context).edit()
                .putString(KEY_CITY_TITLE, city.getName())
                .putFloat(KEY_CITY_LATITUDE, (float) city.getLocation().getLat())
                .putFloat(KEY_CITY_LONGITUDE, (float) city.getLocation().getLng())
                .apply();
        Timber.d("New city information has been written into SharedPreferences: " + city);
    }

    public static City getCity(Context context) {
        SharedPreferences prefs = getCityPrefs(context);
        String cityName = prefs.getString(KEY_CITY_TITLE, VALUE_CITY_TITLE);
        double latitude = (double) prefs.getFloat(KEY_CITY_LATITUDE, (float) VALUE_CITY_LATITUDE);
        double longitude = (double) prefs.getFloat(KEY_CITY_LONGITUDE, (float) VALUE_CITY_LONGITUDE);
        return new City(cityName, latitude, longitude);
    }
}

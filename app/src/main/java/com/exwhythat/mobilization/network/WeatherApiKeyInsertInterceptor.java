package com.exwhythat.mobilization.network;

import android.support.annotation.NonNull;

import org.w3c.dom.ProcessingInstruction;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by exwhythat on 15.07.17.
 */

public class ApiKeyInsertInterceptor implements Interceptor {

    private static final String WEATHER_API_KEY_KEY = "APPID";
    private static final String WEATHER_API_KEY_VALUE = "4ac1ae796a583e819faf2f2bec3f0aed";

    private static final String CITY_API_KEY_KEY = "key";
    private static final String CITY_API_KEY_VALUE = "AIzaSyB6YnIYnkrKpFkKOA026xwtpK2KOqC260c";

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        HttpUrl originalHttpUrl = originalRequest.url();

        HttpUrl urlWithApiKey = originalHttpUrl.newBuilder()
                .addQueryParameter(WEATHER_API_KEY_KEY, WEATHER_API_KEY_VALUE)
                .build();

        Request.Builder requestBuilder = originalRequest.newBuilder()
                .url(urlWithApiKey);

        Request requestWithApiKey = requestBuilder.build();
        return chain.proceed(requestWithApiKey);
    }
}
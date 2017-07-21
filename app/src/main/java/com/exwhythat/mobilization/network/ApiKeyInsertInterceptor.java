package com.exwhythat.mobilization.network;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by exwhythat on 15.07.17.
 */

public class ApiKeyInsertInterceptor implements Interceptor {

    private static final String TRANSLATOR_API_KEY_KEY = "APPID";
    private static final String TRANSLATOR_API_KEY_VALUE = "4ac1ae796a583e819faf2f2bec3f0aed";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        HttpUrl originalHttpUrl = originalRequest.url();

        HttpUrl urlWithApiKey = originalHttpUrl.newBuilder()
                .addQueryParameter(TRANSLATOR_API_KEY_KEY, TRANSLATOR_API_KEY_VALUE)
                .build();

        Request.Builder requestBuilder = originalRequest.newBuilder()
                .url(urlWithApiKey);

        Request requestWithApiKey = requestBuilder.build();
        return chain.proceed(requestWithApiKey);
    }
}
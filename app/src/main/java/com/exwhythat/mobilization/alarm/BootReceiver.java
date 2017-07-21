package com.exwhythat.mobilization.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Turns on weather update alarm after device reboot
 */

public class BootReceiver extends BroadcastReceiver {

    private static final String ANDROID_INTENT_ACTION_BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ANDROID_INTENT_ACTION_BOOT_COMPLETED)) {
            WeatherAlarm.setAlarm(context);
        }
    }
}
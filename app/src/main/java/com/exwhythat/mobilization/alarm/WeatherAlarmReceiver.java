package com.exwhythat.mobilization.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Creaed by exwhythat on 18.07.17.t
 */

public class WeatherAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent newIntent = new Intent(context, WeatherService.class);
        context.startService(newIntent);
    }
}

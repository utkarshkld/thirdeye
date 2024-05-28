package com.example.thirdeye;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            // Reschedule the midnight API call
            MainActivity.scheduleMidnightApiCall(context);
            Log.d("BootReceiver", "Midnight API call rescheduled after reboot");
        }
    }
}

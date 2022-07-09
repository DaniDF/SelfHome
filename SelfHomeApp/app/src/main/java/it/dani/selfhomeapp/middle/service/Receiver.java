package it.dani.selfhomeapp.middle.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class Receiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 0xAB;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction() != null && (
                intent.getAction().equals("a") ||
                intent.getAction().equals(Intent.ACTION_REBOOT) ||
                intent.getAction().equals(Intent.ACTION_LOCKED_BOOT_COMPLETED) ||
                intent.getAction().equals(Intent.ACTION_SCREEN_ON) ||
                intent.getAction().equals(Intent.ACTION_DATE_CHANGED)))
        {
            Intent serviceIntent = new Intent(context,SelfhomeCheckService.class);

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                context.startForegroundService(serviceIntent);
            }
            else
            {
                context.startService(serviceIntent);
            }
        }
    }
}

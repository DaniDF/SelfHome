package it.dani.selfhomeapp.middle.view.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;

import it.dani.selfhomeapp.R;

public class NotificationUtils {

    public static final String ALERT_CHANNEL_ID = "Alert_ID";
    public static final String NOTIFICATION_CHANNEL_ID = "Notification_ID";

    public static void createNotificationAlertChannel(@NonNull Context context)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String notificationName = context.getResources().getString(R.string.notification_alert_name);
            NotificationChannel notificationChannel = new NotificationChannel(ALERT_CHANNEL_ID,notificationName, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription(context.getResources().getString(R.string.notification_alert_description));
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
    }

    public static void createNotificationNotificationChannel(@NonNull Context context)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String notificationName = context.getResources().getString(R.string.notification_notification_name);
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,notificationName, NotificationManager.IMPORTANCE_LOW);
            notificationChannel.setDescription(context.getResources().getString(R.string.notification_notification_description));
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
    }
}

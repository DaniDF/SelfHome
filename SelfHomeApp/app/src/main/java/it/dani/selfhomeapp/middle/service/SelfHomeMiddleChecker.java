package it.dani.selfhomeapp.middle.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import it.dani.selfhomeapp.R;
import it.dani.selfhomeapp.middle.controller.FindServer;
import it.dani.selfhomeapp.middle.view.SlashScreenActivity;
import it.dani.selfhomeapp.middle.view.notification.NotificationUtils;

public class SelfHomeMiddleChecker{

    static void findSelfhomeAndNotify(@NonNull Context context)
    {
        try {
            FindServer findServer = new FindServer();
            findServer.start();
            findServer.join();

            if(findServer.getAddress() != null)
            {
                NotificationUtils.createNotificationNotificationChannel(context);

                Intent openAppSelfhomeIntent = new Intent(context, SlashScreenActivity.class);
                openAppSelfhomeIntent.putExtra("SERVER",findServer);
                TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
                taskStackBuilder.addNextIntent(openAppSelfhomeIntent);

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, NotificationUtils.NOTIFICATION_CHANNEL_ID)
                        .setContentTitle(context.getResources().getString(R.string.notification_find_server_title))
                        .setContentText(context.getResources().getString(R.string.notification_find_server_text))
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setPriority(NotificationManager.IMPORTANCE_LOW)
                        .setContentIntent(taskStackBuilder.getPendingIntent(0x02, PendingIntent.FLAG_UPDATE_CURRENT))
                        .setAutoCancel(true);
                NotificationManagerCompat manager = NotificationManagerCompat.from(context);
                manager.notify(0x10, notificationBuilder.build());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static class SelfhomeWorker extends Worker {
        private final Context context;

        public SelfhomeWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
            super(context, workerParams);
            this.context = context;
        }

        @NonNull
        @Override
        public Result doWork() {
            findSelfhomeAndNotify(this.context);
            return Result.success();
        }
    }
}

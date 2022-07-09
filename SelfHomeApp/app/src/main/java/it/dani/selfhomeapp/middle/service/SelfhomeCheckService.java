package it.dani.selfhomeapp.middle.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.Configuration;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import java.util.concurrent.TimeUnit;

public class SelfhomeCheckService extends Service {

    public SelfhomeCheckService()
    {
        Configuration.Builder configurationBuilder = new Configuration.Builder();
        configurationBuilder.setJobSchedulerJobIdRange(100,10000);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Executor executor = new Executor(this);
        executor.start();

        return super.onStartCommand(intent,flags,startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private static class Executor extends Thread {

        private final Context context;

        public Executor(@NonNull Context context)
        {
            this.context = context;
        }

        @Override
        public void run()
        {
            /*
            try {
                while (true) {
                    NetworkUtils netUtils = new NetworkUtils(this.context);
                    Context context = this.context;
                    netUtils.addNetworkTypeChangeListener(NetworkCapabilities.TRANSPORT_WIFI, new ConnectivityManager.NetworkCallback() {
                        @Override
                        public void onAvailable(@NonNull Network network) {
                            super.onAvailable(network);
                            SelfHomeMiddleChecker.findSelfhomeAndNotify(context);
                        }
                    });

                    NotificationUtils.createNotificationNotificationChannel(this.context);

                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this.context, NotificationUtils.NOTIFICATION_CHANNEL_ID)
                            .setContentTitle("Checking server")
                            .setContentText("Checking...")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setPriority(NotificationManager.IMPORTANCE_LOW)
                            .setAutoCancel(true);
                    NotificationManagerCompat manager = NotificationManagerCompat.from(this.context);
                    manager.notify(0x10, notificationBuilder.build());

                    Thread.sleep(15 * 60 * 1000);

                    Log.e("SERVICEEEEE", "ACTIVEEEEEEEEEEEEE");

                    SelfHomeMiddleChecker.findSelfhomeAndNotify(this.context);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            */

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                WorkManager workManager = WorkManager.getInstance(this.context.getApplicationContext());
                WorkRequest workRequest = new PeriodicWorkRequest.Builder(SelfHomeMiddleChecker.SelfhomeWorker.class, 15, TimeUnit.SECONDS).build();
                workManager.enqueue(workRequest);
            }
        }
    }
}
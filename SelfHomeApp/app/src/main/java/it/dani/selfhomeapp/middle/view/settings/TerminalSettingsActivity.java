package it.dani.selfhomeapp.middle.view.settings;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import it.dani.selfhomeapp.R;
import it.dani.selfhomeapp.middle.controller.Controller;
import it.dani.selfhomeapp.middle.icontroller.IDeviceController;
import it.dani.selfhomeapp.middle.view.MainActivity;
import it.dani.selfhomeapp.middle.view.Utils;
import it.dani.selfhomeapp.middle.view.notification.NotificationUtils;

public class TerminalSettingsActivity extends AppCompatActivity {

    private IDeviceController deviceController;
    private final Map<String,String> msgs = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terminal_settings);

        View terminalLayout = findViewById(R.id.terminalLayout);

        Toolbar toolbar = findViewById(R.id.app_toolbar);
        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = this.getIntent();
        if(intent.getStringExtra("IP") != null) this.msgs.put("IP",intent.getStringExtra("IP"));
        if(intent.getStringExtra("PORT") != null) this.msgs.put("PORT",intent.getStringExtra("PORT"));

        EditText terminalCode = findViewById(R.id.terminalCode);

        try {
            this.deviceController = new Controller(this.msgs.get("IP"),Integer.parseInt(this.msgs.get("PORT")));

            Button terminalExecute = findViewById(R.id.terminalExecute);
            terminalExecute.setOnClickListener(x -> {
                NotificationUtils.createNotificationAlertChannel(this);

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,NotificationUtils.ALERT_CHANNEL_ID)
                        .setContentTitle(this.getResources().getString(R.string.notification_terminal_title))
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setPriority(NotificationManager.IMPORTANCE_MIN)
                        .setAutoCancel(true);
                NotificationManagerCompat manager = NotificationManagerCompat.from(this);

                this.deviceController.freeText(terminalCode.getText().toString(), x2 -> {
                            notificationBuilder.setContentText(this.getResources().getString(R.string.notification_terminal_text) + " " + x2);
                            manager.notify(0x10, notificationBuilder.build());
                            Snackbar.make(terminalLayout,x2,Snackbar.LENGTH_LONG).show();
                        },
                        e -> {
                            notificationBuilder.setContentText(this.getResources().getString(R.string.notification_terminal_text) + " " +
                                    this.getResources().getString(R.string.deviceSwitchGetStateErr));
                            manager.notify(0x10, notificationBuilder.build());
                            Snackbar.make(terminalLayout,R.string.deviceSwitchGetStateErr,Snackbar.LENGTH_LONG).show();
                        });
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

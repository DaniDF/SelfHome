package it.dani.selfhomeapp.middle.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import it.dani.selfhomeapp.R;
import it.dani.selfhomeapp.middle.controller.Controller;
import it.dani.selfhomeapp.middle.controller.FindServer;
import it.dani.selfhomeapp.middle.icontroller.IDeviceController;
import it.dani.selfhomeapp.middle.service.Receiver;
import it.dani.selfhomeapp.middle.service.SelfhomeCheckService;
import it.dani.selfhomeapp.middle.view.dialogs.NetworkServerErrorDialog;
import it.dani.selfhomeapp.middle.view.dialogs.NoServerErrorDialog;
import it.dani.selfhomeapp.middle.view.settings.LanguageSettingsActivity;
import it.dani.selfhomeapp.middle.view.settings.ScreenSettingsActivity;
import it.dani.selfhomeapp.middle.view.settings.SecuritySettingsActivity;
import it.dani.selfhomeapp.middle.view.settings.TerminalSettingsActivity;

public class MainActivity extends AppCompatActivity {

    private IDeviceController deviceController;
    private SensorUtils sensorUtils;

    private final Map<String,String> msgs = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.devices);

        //TODO temp
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            this.startForegroundService(new Intent(this, SelfhomeCheckService.class));
        }
        //

        Toolbar toolbar = findViewById(R.id.app_toolbar);
        this.setSupportActionBar(toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this,R.drawable.more_vert));

        this.sensorUtils = new SensorUtils();
        this.sensorUtils.accelerometerSetup(this);

        NetworkUtils netUtils = new NetworkUtils(this);
        boolean wifiConnected = netUtils.isConnectedVia(NetworkCapabilities.TRANSPORT_WIFI);

        if(!wifiConnected)
        {
            Log.e("SelfHome_home","No wifi connection");
            NetworkServerErrorDialog networkServerErrorDialog = new NetworkServerErrorDialog(() -> {
                Utils.createNewIntent(this, Settings.ACTION_WIFI_SETTINGS);
                this.createAllView();
                return null;
            },() -> {
                Log.i("SelfHome_home","User closed the app");
                finish();
                return null;
            });
            networkServerErrorDialog.show(this.getSupportFragmentManager(),"NETWORK_ERROR");
        }
        else this.createAllView();
    }

    private void createAllView()
    {
        View mainView = findViewById(R.id.mainView);

        try {
            FindServer findServer;

            if(this.getIntent().getSerializableExtra("SERVER") != null)
            {
                findServer = (FindServer) this.getIntent().getSerializableExtra("SERVER");
                Snackbar.make(mainView,R.string.connect_server_from_notification,Snackbar.LENGTH_SHORT).show();
            }
            else
            {
                findServer = new FindServer();
                findServer.start();
                findServer.join();
            }

            if (findServer.getAddress() != null) {
                Log.i("SelfHome_settings", "Server find at " + findServer);

                this.msgs.put("IP", findServer.getAddress());
                this.msgs.put("PORT", "" + findServer.getPort());

                Button groupMenu = findViewById(R.id.createNewGroupMenu);
                groupMenu.setOnClickListener((x) -> Utils.createNewIntent(this, GroupActivity.class, this.msgs));

                SwipeRefreshLayout deviceRefreshView = findViewById(R.id.deviceRefreshView);
                deviceRefreshView.setOnRefreshListener(() -> Utils.reload(this,MainActivity.class));

                try {
                    this.deviceController = new Controller(findServer.getAddress(), findServer.getPort());
                    this.deviceController.getDevices(x -> runOnUiThread(new DisplayerThread<>(this::createButton, x)), e -> Snackbar.make(mainView, R.string.noDeviceFoundErr, Snackbar.LENGTH_LONG).show());

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("SelfHome_settings", "Could not find server - any response");

                NoServerErrorDialog errorDialog = new NoServerErrorDialog(() -> {Utils.reload(this,MainActivity.class); return null;},() -> {Log.i("Reload","Ignore"); return null;});
                errorDialog.show(getSupportFragmentManager(), "MULTICAST_ERROR");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized void createButton(@NotNull Set<String> item)
    {
        LinearLayout linLayout = findViewById(R.id.devices);

        for(String d : item)
        {
            Log.d("SelfHome_button","BUTTON: " + d);
            Button button = new Button(new ContextThemeWrapper(this,R.style.deviceButton), null, R.style.deviceButton);
            button.setText(d);

            button.setOnClickListener((x) -> {
                this.deviceController.toggleDevice(d, (a) -> {
                    if(!a) {
                        Snackbar.make(linLayout,R.string.deviceSwitchSetStateErr,Snackbar.LENGTH_LONG).show();
                    }
                    else {
                        this.deviceController.getDeviceState(d,(b) -> {
                            int message;
                            if(b != 0) message = R.string.deviceSwitchSetStateOff;
                            else message = R.string.deviceSwitchSetStateOn;

                            Snackbar.make(linLayout,message,Snackbar.LENGTH_LONG).show();
                        });
                    }
                },e -> Snackbar.make(linLayout,R.string.deviceSwitchGetStateErr,Snackbar.LENGTH_LONG).show());
            });

            button.setOnLongClickListener(x -> {
                this.deviceController.getDeviceState(d, a -> {
                    Log.v("SelfHome_button_device_get", d + ": " + a);

                    int message;
                    if(a != 0) message = R.string.deviceGetStateOn;
                    else message = R.string.deviceGetStateOff;

                    Snackbar.make(linLayout,message,Snackbar.LENGTH_LONG).show();

                }, e -> Snackbar.make(linLayout,R.string.deviceSwitchGetStateErr,Snackbar.LENGTH_LONG).show());

                //this.msgs.put("name",d);
                //Utils.createNewIntent(this, ChangeColorActivity.class, this.msgs);
                return true;
            });

            try {
                linLayout.addView(button);
            } catch (Exception e){
                Log.e("SelfHome_button_error",e.getMessage());
            }
        }
    }

    private void scheduleAlarm()
    {
        Intent intent = new Intent(this, Receiver.class);
        intent.setAction("a");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,Receiver.REQUEST_CODE,intent,PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),5*1000,pendingIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.navigation_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean result = true;

        if(item.getItemId() == R.id.admin_menu) Utils.createNewIntent(this, SecuritySettingsActivity.class);
        else if(item.getItemId() == R.id.language_settings_menu) Utils.createNewIntent(this, LanguageSettingsActivity.class);
        else if(item.getItemId() == R.id.screen_settings_menu) Utils.createNewIntent(this, ScreenSettingsActivity.class);
        else if(item.getItemId() == R.id.terminal_settings_menu) Utils.createNewIntent(this, TerminalSettingsActivity.class,this.msgs);
        else result = super.onOptionsItemSelected(item);

        return result;
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.sensorUtils.getSensorManager().registerListener(this.sensorUtils.getAccelerometerSensorListener(),this.sensorUtils.getAccelerometerSensor(), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();

        this.sensorUtils.getSensorManager().unregisterListener(this.sensorUtils.getAccelerometerSensorListener(),this.sensorUtils.getAccelerometerSensor());
    }

    @Override
    protected void onStart() {
        super.onStart();

        /*TODO Lo lascio scritto solo per ricordarmi come si fa
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            WorkManager workManager = WorkManager.getInstance(this.getApplicationContext());
            WorkRequest workRequest = new PeriodicWorkRequest.Builder(SelfHomeMiddleChecker.SelfhomeWorker.class, 5, TimeUnit.SECONDS).build();
            workManager.enqueue(workRequest);
        }
        */
    }
}
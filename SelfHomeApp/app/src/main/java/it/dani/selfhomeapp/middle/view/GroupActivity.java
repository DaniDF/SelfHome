package it.dani.selfhomeapp.middle.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import it.dani.selfhomeapp.R;
import it.dani.selfhomeapp.middle.controller.Controller;
import it.dani.selfhomeapp.middle.icontroller.IDeviceController;
import it.dani.selfhomeapp.middle.icontroller.IGroupController;

public class GroupActivity extends AppCompatActivity {

    private IDeviceController deviceController;
    private IGroupController groupController;

    private static final Map<String,String> MSGS = new HashMap<>();

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.groups);

        Toolbar toolbar = findViewById(R.id.app_toolbar);
        this.setSupportActionBar(toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this,R.drawable.more_vert));
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View groupsView = findViewById(R.id.groupsView);

        Intent intent = this.getIntent();
        if(intent.getStringExtra("IP") != null) MSGS.put("IP",intent.getStringExtra("IP"));
        if(intent.getStringExtra("PORT") != null) MSGS.put("PORT",intent.getStringExtra("PORT"));

        try {
            Button createNewGroupMenu = findViewById(R.id.createNewGroupMenu);
            createNewGroupMenu.setOnClickListener(x -> Utils.createNewIntent(this,NewGroupActivity.class,MSGS));

            SwipeRefreshLayout groupRefreshView = findViewById(R.id.groupRefreshView);
            groupRefreshView.setOnRefreshListener(() -> Utils.reload(this,GroupActivity.class,MSGS));

            Controller controller = new Controller(MSGS.get("IP"),Integer.parseInt(MSGS.get("PORT")));
            this.deviceController = controller;
            this.groupController = controller;
            this.groupController.getGroups(x -> runOnUiThread(new DisplayerThread<>(this::createButton, x)), e -> Snackbar.make(groupsView,R.string.noGroupsFoundErr,Snackbar.LENGTH_LONG).show());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void createButton(Set<String> item)
    {
        LinearLayout linLayout = findViewById(R.id.devices);

        for(String g : item)
        {
            Log.d("SelfHome_button","BUTTON: " + g);
            Button button = new Button(new ContextThemeWrapper(this,R.style.deviceButton), null, R.style.deviceButton);
            button.setText(g);

            button.setOnClickListener((x) -> this.groupController.getDevicesFromGroup(g,dns -> dns.forEach(dn -> this.deviceController.getDeviceState(dn,ds -> {
                    this.deviceController.setDeviceState(dn,(ds != 0)? 0:1, y -> {
                        Snackbar.make(linLayout,R.string.groupSwitchState,Snackbar.LENGTH_LONG).show();
                        Log.v("SelfHome_button_group_change",g + ": " + ds + " -> " + ((ds != 0) ? 0 : 1) + " => " + y);
                    }, e -> {
                        Snackbar.make(linLayout,R.string.groupSwitchStateErr,Snackbar.LENGTH_LONG).show();
                        Log.e("SelfHome_button_group_change","Error changing group state");
                    });
            }))));

            button.setOnLongClickListener(x -> {
                MSGS.put("MODE","MOD");
                MSGS.put("GROUPNAME",g);

                Utils.createNewIntent(this,NewGroupActivity.class,MSGS);

                MSGS.remove("MODE");

                return true;
            });

            try {
                linLayout.addView(button);
            } catch (Exception e){
                Log.e("SelfHome_button_error",e.getMessage());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu,menu);
        menu.findItem(R.id.settings).setVisible(false);

        return true;
    }

    /*
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean result = true;

        if(item.getItemId() == R.id.reload_button) Utils.reload(this,GroupActivity.class,MSGS);
        else result = super.onOptionsItemSelected(item);

        return result;
    }
    */
}

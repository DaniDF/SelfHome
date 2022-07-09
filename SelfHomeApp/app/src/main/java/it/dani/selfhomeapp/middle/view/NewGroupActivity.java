package it.dani.selfhomeapp.middle.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import it.dani.selfhomeapp.R;
import it.dani.selfhomeapp.middle.controller.Controller;
import it.dani.selfhomeapp.middle.icontroller.IDeviceController;
import it.dani.selfhomeapp.middle.icontroller.IGroupController;
import it.dani.selfhomeapp.middle.view.dialogs.NewGroupIncompleteErrorDialog;

public class NewGroupActivity extends AppCompatActivity {

    private IDeviceController deviceController;
    private IGroupController groupController;

    private final Map<String,String> msgs = new HashMap<>();

    private Set<String> devices = new HashSet<>();

    @Override
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.newgroup);

        Toolbar toolbar = findViewById(R.id.app_toolbar);
        this.setSupportActionBar(toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this,R.drawable.more_vert));
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = this.getIntent();
        this.msgs.put("IP",intent.getStringExtra("IP"));
        this.msgs.put("PORT",intent.getStringExtra("PORT"));

        boolean isInModify = (intent.getStringExtra("MODE") != null && intent.getStringExtra("MODE").equals("MOD"));

        EditText newGroupName = findViewById(R.id.newGroupName);

        if(isInModify)
        {
            TextView title = findViewById(R.id.labelNewGroupView);
            title.setText(R.string.modGroupView);
            newGroupName.setInputType(InputType.TYPE_NULL);
            newGroupName.setTextIsSelectable(false);
            newGroupName.setOnKeyListener((x,y,z) -> true);

            this.msgs.put("GROUPNAME",intent.getStringExtra("GROUPNAME"));
            newGroupName.setText(this.msgs.get("GROUPNAME"));
        }

        Button createNewGroupMenu = findViewById(R.id.createNewGroupMenu);

        try
        {
            Controller controller = new Controller(this.msgs.get("IP"),Integer.parseInt(this.msgs.get("PORT")));
            this.deviceController = controller;
            this.groupController = controller;

            this.deviceController.getDevices(x -> this.devices = x);

            while(this.devices.size() == 0);

            this.createRadioButton(devices);
            if(isInModify) this.groupController.getDevicesFromGroup(this.msgs.get("GROUPNAME"),x -> {
                this.checkRadioButton(x);
                createNewGroupMenu.setOnClickListener(y -> this.modifyGroup(x));
            });
            else createNewGroupMenu.setOnClickListener(x -> {
                if(newGroupName.getText().length() == 0) new NewGroupIncompleteErrorDialog().show(getSupportFragmentManager(),"BACK");
                else this.createNewGroup();
            });

            Button deleteButton = findViewById(R.id.deleteGroupMenu);
            deleteButton.setOnClickListener(x -> {
                this.groupController.delGroup(this.msgs.get("GROUPNAME"), y -> {
                    if (y) Log.i("DEL", "Delete group: " + this.msgs.get("GROUPNAME"));
                    else Log.e("DEL", "Failed delete group: " + this.msgs.get("GROUPNAME"));
                });

                finish();
            });

            if(!isInModify) deleteButton.setVisibility(View.GONE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createRadioButton(Set<String> deviceNames)
    {
        LinearLayout groups = findViewById(R.id.devices);

        for(String d : deviceNames)
        {
            CheckBox check = new CheckBox(this);
            check.setText(d);
            groups.addView(check);
        }
    }

    private void checkRadioButton(Set<String> deviceNames)
    {
        LinearLayout groups = findViewById(R.id.devices);

        for(int count = 1; count < groups.getChildCount(); count++)
        {
            CheckBox cb = (CheckBox)(groups.getChildAt(count));

            if(deviceNames.contains(cb.getText()))
            {
                cb.setChecked(true);
            }
        }
    }

    private void createNewGroup()
    {
        EditText newGroupName = findViewById(R.id.newGroupName);

        if(newGroupName.getText().length() > 0)
        {
            this.groupController.addNewGroup(newGroupName.getText().toString(), x -> {
                if(x)
                {
                    LinearLayout groups = findViewById(R.id.devices);

                    for(int count = 1; count < groups.getChildCount(); count++)
                    {
                        CheckBox cb = (CheckBox)(groups.getChildAt(count));

                        if(cb.isChecked())
                        {
                            String deviceName = cb.getText().toString();
                            Log.d("NAME",deviceName);

                            this.groupController.addDeviceToGroup(deviceName, newGroupName.getText().toString(), y -> {
                                if (y) Log.i("NEW", "Create new button: " + deviceName);
                                else Log.e("NEW", "Failed create new button: " + deviceName);
                            }, e -> Snackbar.make(findViewById(R.id.newGroupLayout),R.string.insertDeviceInGroupErr,Snackbar.LENGTH_LONG));
                        }
                    }

                    finish();
                }
            }, e -> Snackbar.make(findViewById(R.id.newGroupLayout),R.string.createNewGroupErr,Snackbar.LENGTH_LONG));
        }
    }

    private void modifyGroup(Set<String> prevGroups)
    {
        LinearLayout groups = findViewById(R.id.devices);

        for(int count = 1; count < groups.getChildCount(); count++)
        {
            CheckBox cb = (CheckBox)(groups.getChildAt(count));
            String deviceName = cb.getText().toString();

            if(cb.isChecked() && !prevGroups.contains(cb.getText()))
            {
                Log.d("NAME","ADD new device: " + deviceName);

                this.groupController.addDeviceToGroup(deviceName, this.msgs.get("GROUPNAME"), y -> {
                    if (y) Log.i("NEW", "Create new button: " + deviceName);
                    else Log.e("NEW", "Failed create new button: " + deviceName);
                }, e -> Snackbar.make(findViewById(R.id.newGroupLayout),R.string.insertDeviceInGroupErr,Snackbar.LENGTH_LONG));
            }
            else if(!cb.isChecked() && prevGroups.contains(cb.getText()))
            {
                Log.d("NAME","DEL device: " + deviceName);

                this.groupController.delDeviceToGroup(deviceName, this.msgs.get("GROUPNAME"), y -> {
                    if (y) Log.i("NEW", "Delete button: " + deviceName);
                    else Log.e("NEW", "Failed delete button: " + deviceName);
                }, e -> Snackbar.make(findViewById(R.id.newGroupLayout),R.string.removeDeviceFromGroupErr,Snackbar.LENGTH_LONG));
            }
        }

        finish();
    }
}

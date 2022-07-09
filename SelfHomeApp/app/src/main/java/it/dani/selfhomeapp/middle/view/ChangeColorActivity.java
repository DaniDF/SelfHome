package it.dani.selfhomeapp.middle.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.skydoves.colorpickerview.ActionMode;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import it.dani.selfhomeapp.R;
import it.dani.selfhomeapp.middle.controller.Controller;
import it.dani.selfhomeapp.middle.icontroller.IDeviceController;

public class ChangeColorActivity extends AppCompatActivity {

    private IDeviceController deviceController;
    private boolean isFirstChange = true;

    private final Map<String,String> msgs = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_color_layout);

        Toolbar toolbar = findViewById(R.id.app_toolbar);
        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = this.getIntent();
        if(intent.getStringExtra("IP") != null) this.msgs.put("IP",intent.getStringExtra("IP"));
        if(intent.getStringExtra("PORT") != null) this.msgs.put("PORT",intent.getStringExtra("PORT"));
        if(intent.getStringExtra("name") != null) this.msgs.put("name",intent.getStringExtra("name"));
        else this.finish();

        try {
            this.deviceController = new Controller(this.msgs.get("IP"),Integer.parseInt(this.msgs.get("PORT")));

        } catch (IOException e) {
            e.printStackTrace();
        }

        ColorPickerView colorPicker = findViewById(R.id.colorPicker);
        this.deviceController.getDeviceState(this.msgs.get("name"),x -> {
            colorPicker.setInitialColor(Color.rgb((x & 0xFF0000)>>16,(x & 0xFF00)>>8,(x & 0xFF)));
            Log.d("COLOR_INIT",x + " (" + ((x & 0xFF0000)>>16) + "," + ((x & 0xFF00)>>8) + "," + (x & 0xFF) + ")");
            },e -> colorPicker.setInitialColor(Color.rgb(0,0,0)));
        colorPicker.setColorListener((ColorEnvelopeListener) (envelope, fromUser) -> {
            if(!this.isFirstChange)
            {
                int color = ((envelope.getArgb()[1] << 16) + (envelope.getArgb()[2] << 8) + envelope.getArgb()[3]);
                deviceController.setDeviceState(msgs.get("name"), color, x -> {
                    Log.v("SelfHome_button_device_change", msgs.get("name") + ": -> " + color + " (" + envelope.getArgb()[1] + "," + envelope.getArgb()[2] + "," + envelope.getArgb()[3] + ") " + " => " + x);
                    Snackbar.make(colorPicker, x ? R.string.deviceSetStateColor : R.string.deviceSetStateColorErr, Snackbar.LENGTH_LONG).show();
                }, e -> Snackbar.make(colorPicker, R.string.deviceSetStateColorErr, Snackbar.LENGTH_LONG).show());
            }

            this.isFirstChange = false;
        });
        colorPicker.setActionMode(ActionMode.LAST);

        SwitchMaterial actionModeColorPitcherSwitch = findViewById(R.id.actionModeColorPitcherSwitch);
        actionModeColorPitcherSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> colorPicker.setActionMode((isChecked)? ActionMode.ALWAYS : ActionMode.LAST));
    }
}

package it.dani.selfhomeapp.middle.view.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.material.switchmaterial.SwitchMaterial;

import it.dani.selfhomeapp.R;
import it.dani.selfhomeapp.middle.view.MainActivity;
import it.dani.selfhomeapp.middle.view.Utils;

public class ScreenSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.screen_settings);

        Toolbar toolbar = findViewById(R.id.app_toolbar);
        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedPreferences = this.getSharedPreferences(String.valueOf(R.string.app_screen_settings), MODE_PRIVATE);

        SwitchMaterial switchDayNight = findViewById(R.id.dayNightScreenSetting);
        switchDayNight.setChecked(sharedPreferences.getBoolean(String.valueOf(R.string.app_dayNight_screen_settings), false));
        switchDayNight.setOnClickListener(x -> {
            if (switchDayNight.isChecked()) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                Log.i("ScreenSettings", "Turning on night mode");

            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                Log.i("ScreenSettings", "Turning night mode in system settings");
            }

            SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
            sharedPreferencesEditor.putBoolean(String.valueOf(R.string.app_dayNight_screen_settings), switchDayNight.isChecked());
            sharedPreferencesEditor.apply();

        });
    }
}

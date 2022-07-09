package it.dani.selfhomeapp.middle.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import it.dani.selfhomeapp.R;

public class SlashScreenActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = this.getSharedPreferences(String.valueOf(R.string.app_screen_settings),MODE_PRIVATE);
        if(sharedPreferences.getBoolean(String.valueOf(R.string.app_dayNight_screen_settings),false))
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            Log.i("ScreenSettings","Turning on night mode as default app setting");
        }

        Intent intent = new Intent(this,MainActivity.class);
        if(this.getIntent().getSerializableExtra("SERVER") != null)
        {
            intent.putExtra("SERVER",this.getIntent().getSerializableExtra("SERVER"));
        }
        startActivity(intent);
        finish();
    }
}

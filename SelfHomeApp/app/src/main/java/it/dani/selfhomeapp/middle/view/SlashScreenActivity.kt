package it.dani.selfhomeapp.middle.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import it.dani.selfhomeapp.R
import it.dani.selfhomeapp.middle.controller.FindServer

class SlashScreenActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences(R.string.app_screen_settings.toString(), MODE_PRIVATE)

        if (sharedPreferences.getBoolean(R.string.app_dayNight_screen_settings.toString(), false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            Log.i("ScreenSettings", "Turning on night mode as default app setting")
        }

        Intent(this, MainActivity::class.java).also {
            startActivity(it)
            finish()
        }

        startActivity(intent)
        finish()
    }
}
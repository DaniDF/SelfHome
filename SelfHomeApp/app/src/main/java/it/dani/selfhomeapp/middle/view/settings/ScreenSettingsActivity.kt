package it.dani.selfhomeapp.middle.view.settings

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import com.google.android.material.switchmaterial.SwitchMaterial
import it.dani.selfhomeapp.R

class ScreenSettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.screen_settings)

        val toolbar = findViewById<Toolbar>(R.id.app_toolbar)
        this.setSupportActionBar(toolbar)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val sharedPreferences = this.getSharedPreferences(this.resources.getString(R.string.app_screen_settings), MODE_PRIVATE)
        findViewById<SwitchMaterial>(R.id.dayNightScreenSetting).apply {
            isChecked = sharedPreferences.getBoolean(this.resources.getString(R.string.app_dayNight_screen_settings), false)
            setOnClickListener {
                if(this.isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    Log.i("ScreenSettings", "Turning on night mode")
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    Log.i("ScreenSettings", "Turning night mode in system settings")
                }

                val sharedPreferencesEditor = sharedPreferences.edit()
                sharedPreferencesEditor.putBoolean(this@ScreenSettingsActivity.resources.getString(R.string.app_dayNight_screen_settings), this.isChecked)
                sharedPreferencesEditor.apply()
            }
        }
    }
}
package it.dani.selfhomeapp.middle.view.settings

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import it.dani.selfhomeapp.R
import it.dani.selfhomeapp.middle.view.dialogs.LanguageSettingDialog
import java.util.*

class LanguageSettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.language_settings)

        val toolbar = findViewById<Toolbar>(R.id.app_toolbar)
        this.setSupportActionBar(toolbar)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        findViewById<Button>(R.id.buttonLanguageSettings).apply {
            text = Locale.getDefault().language
            setOnClickListener {
                LanguageSettingDialog().show(this@LanguageSettingsActivity.supportFragmentManager,"CHOOSE_LANGUAGE")
            }
        }
    }
}
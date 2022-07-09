package it.dani.selfhomeapp.middle.view.settings;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import java.util.Locale;

import it.dani.selfhomeapp.R;
import it.dani.selfhomeapp.middle.view.MainActivity;
import it.dani.selfhomeapp.middle.view.Utils;
import it.dani.selfhomeapp.middle.view.dialogs.LanguageSettingDialog;

public class LanguageSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.language_settings);

        Toolbar toolbar = findViewById(R.id.app_toolbar);
        this.setSupportActionBar(toolbar);
        //toolbar.setOverflowIcon(ContextCompat.getDrawable(this,R.drawable.more_vert));
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button buttonLanguageSetting = findViewById(R.id.buttonLanguageSettings);
        buttonLanguageSetting.setText(Locale.getDefault().getLanguage());
        buttonLanguageSetting.setOnClickListener(x -> {
            new LanguageSettingDialog().show(getSupportFragmentManager(),"CHOOSE_LANGUAGE");
        });
    }
}

package it.dani.selfhomeapp.middle.view.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Locale;

import it.dani.selfhomeapp.R;

public class LanguageSettingDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());

        builder.setTitle(R.string.titleLanguageRequest);
        builder.setPositiveButton(R.string.positiveLanguageRequest,(dialog, which) -> {});
        builder.setNegativeButton(R.string.negativeLanguageRequest,(dialog, which) -> {});

        String[] locales = new String[Locale.getAvailableLocales().length];
        int currentActive = 0;
        for(int count = 0; count < locales.length; count++)
        {
            if(Locale.getAvailableLocales()[count].equals(Locale.getDefault())) currentActive = count;
            locales[count] = Locale.getAvailableLocales()[count].getDisplayName() + " " + Locale.getAvailableLocales()[count].getDisplayScript();
        }

        builder.setSingleChoiceItems(locales,currentActive,(dialog, which) -> {});

        return builder.create();
    }
}

package it.dani.selfhomeapp.middle.view;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import it.dani.selfhomeapp.R;
import it.dani.selfhomeapp.middle.view.settings.LanguageSettingsActivity;
import it.dani.selfhomeapp.middle.view.settings.ScreenSettingsActivity;
import it.dani.selfhomeapp.middle.view.settings.SecuritySettingsActivity;
import it.dani.selfhomeapp.middle.view.settings.TerminalSettingsActivity;

public class Utils {
    public static Intent createNewIntent(@NonNull Activity activity, @NonNull Class clas, @NonNull Map<String,String> map)
    {
        Intent intent = new Intent(activity,clas);

        for(String s : map.keySet())
        {
            intent.putExtra(s,map.get(s));
        }

        activity.startActivity(intent);

        return intent;
    }

    public static Intent createNewIntent(@NonNull Activity activity, @NonNull Class clas)
    {
        return createNewIntent(activity, clas,new HashMap<>());
    }

    public static Intent createNewIntent(@NonNull Activity activity, @NonNull String clas)
    {
        Intent intent = new Intent(clas);
        activity.startActivity(intent);
        return intent;
    }

    public static void reload(@NonNull Activity activity, @NonNull Class clas, @NonNull Map<String,String> map)
    {
        Log.d("Reload","Reload activity");
        if(map != null) Utils.createNewIntent(activity,clas,map);
        else Utils.createNewIntent(activity,clas);
        activity.finish();
    }

    public static void reload(@NonNull Activity activity, @NonNull Class clas)
    {
        reload(activity,clas,null);
    }
}

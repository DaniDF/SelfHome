package it.dani.selfhomeapp.middle.view;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;

import androidx.annotation.NonNull;

public class NetworkUtils {

    private final Context context;

    public NetworkUtils(@NonNull Context context)
    {
        this.context = context;
    }

    public boolean isConnectedVia(int networkCapabilitiesType)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean connected = false;

        /*
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {
        */
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            connected = networkCapabilities.hasTransport(networkCapabilitiesType);
        /*
        }
        //TODO To be removed
        else {
            for (Network n : connectivityManager.getAllNetworks())
            {
                connected |= (connectivityManager.getNetworkInfo(n).getType() == ConnectivityManager.TYPE_WIFI && connectivityManager.getNetworkInfo(n).isConnected());
            }
        }
        */

        return connected;
    }

    public void addNetworkTypeChangeListener(int networkCapabilitiesType, @NonNull ConnectivityManager.NetworkCallback networkCallback)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkRequest.Builder builder = new NetworkRequest.Builder();
        builder.addTransportType(networkCapabilitiesType);

        connectivityManager.registerNetworkCallback(builder.build(), networkCallback);
    }
}

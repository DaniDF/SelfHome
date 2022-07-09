package it.dani.selfhomeapp.middle.view.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.function.Supplier;

import it.dani.selfhomeapp.R;

public class NetworkServerErrorDialog extends DialogFragment {

    private Supplier<Void> positiveSupplier;
    private Supplier<Void> negativeSupplier;

    public NetworkServerErrorDialog()
    {
        super();
        this.positiveSupplier = null;
        this.negativeSupplier = null;
    }

    public NetworkServerErrorDialog(@NonNull Supplier<Void> positiveSupplier, @NonNull Supplier<Void> negativeSupplier)
    {
        super();
        this.positiveSupplier = positiveSupplier;
        this.negativeSupplier = negativeSupplier;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstance)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.titleHandleNoWifiNetworkResponseErr);
        builder.setMessage(R.string.noWifiNetworkResponseErr);
        builder.setPositiveButton(R.string.positiveHandleNoWifiNetworkResponseErr,(x,y) -> {
            if(this.positiveSupplier != null) this.positiveSupplier.get();
        });
        builder.setNegativeButton(R.string.negativeHandleNoWifiNetworkResponseErr,(x,y) -> {
            if(this.negativeSupplier != null) this.negativeSupplier.get();
        });

        return builder.create();
    }
}

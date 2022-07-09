package it.dani.selfhomeapp.middle.view.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.function.Supplier;

import it.dani.selfhomeapp.R;

public class NoServerErrorDialog extends DialogFragment {

    private Supplier<Void> positiveSupplier;
    private Supplier<Void> negativeSupplier;

    public NoServerErrorDialog()
    {
        super();
        this.positiveSupplier = null;
        this.negativeSupplier = null;
    }

    public NoServerErrorDialog(@NonNull Supplier<Void> positiveSupplier, @NonNull Supplier<Void> negativeSupplier)
    {
        super();
        this.positiveSupplier = positiveSupplier;
        this.negativeSupplier = negativeSupplier;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstance)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.titleHandleNoServerResponseErr);
        builder.setMessage(R.string.noServerResponseErr);
        builder.setPositiveButton(R.string.positiveHandleNoServerResponseErr,(x,y) -> {
            if(this.positiveSupplier != null) this.positiveSupplier.get();
        });
        builder.setNegativeButton(R.string.negativeHandleNoServerResponseErr,(x,y) -> {
            if(this.negativeSupplier != null) this.negativeSupplier.get();
        });

        return builder.create();
    }
}

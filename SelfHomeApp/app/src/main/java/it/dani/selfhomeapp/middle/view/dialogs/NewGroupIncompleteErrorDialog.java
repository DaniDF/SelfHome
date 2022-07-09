package it.dani.selfhomeapp.middle.view.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.function.Supplier;

import it.dani.selfhomeapp.R;

public class NewGroupIncompleteErrorDialog extends DialogFragment {

    private Supplier<Void> negativeSupplier;

    public NewGroupIncompleteErrorDialog()
    {
        super();
        this.negativeSupplier = null;
    }

    public NewGroupIncompleteErrorDialog(@NonNull Supplier<Void> negativeSupplier)
    {
        super();
        this.negativeSupplier = negativeSupplier;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstance)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.titleNewGroupIncompleteResponse);
        builder.setMessage(R.string.newGroupIncompleteResponse);

        builder.setNegativeButton(R.string.negativeNewGroupIncompleteResponse,(x,y) -> {
            if(this.negativeSupplier != null) this.negativeSupplier.get();
        });

        return builder.create();
    }
}

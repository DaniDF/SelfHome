package it.dani.selfhomeapp.middle.view.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.function.Consumer;
import java.util.function.Supplier;

import it.dani.selfhomeapp.R;

public class TelegramCodeDialog extends DialogFragment {

    private Consumer<String> positiveConsumer;
    private Supplier<Void> negativeSupplier;

    public TelegramCodeDialog()
    {
        super();
        this.positiveConsumer = null;
        this.negativeSupplier = null;
    }

    public TelegramCodeDialog(@NonNull Consumer<String> positiveConsumer, @NonNull Supplier<Void> negativeSupplier)
    {
        super();
        this.positiveConsumer = positiveConsumer;
        this.negativeSupplier = negativeSupplier;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.titleTelegramCode);

        EditText codeEditText = new EditText(getActivity());
        codeEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(codeEditText);

        builder.setPositiveButton(R.string.positiveTelegramCode, (dialog, which) -> {if(this.positiveConsumer != null) this.positiveConsumer.accept(codeEditText.getText().toString());});
        builder.setNegativeButton(R.string.negativeTelegramCode, (dialog, which) -> {if(this.negativeSupplier != null) this.negativeSupplier.get();});

        return builder.create();
    }
}

package it.dani.selfhomeapp.middle.view.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import it.dani.selfhomeapp.R

class NoServerErrorDialog(private val onPositive : () -> Any, private val onNegative : () -> Any, private val onNeutral: () -> Any) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(this.activity).apply {
            setTitle(R.string.titleHandleNoServerResponseErr)
            setMessage(R.string.noServerResponseErr)
            setPositiveButton(R.string.positiveHandleNoServerResponseErr) {_,_ ->
                this@NoServerErrorDialog.onPositive()
            }
            setNegativeButton(R.string.negativeHandleNoServerResponseErr) {_,_ ->
                this@NoServerErrorDialog.onNegative()
            }
            setNeutralButton(R.string.neutralHandleNoServerResponseErr) {_,_ ->
                this@NoServerErrorDialog.onNeutral()
            }
        }.create()
    }
}
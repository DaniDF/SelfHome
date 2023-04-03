package it.dani.selfhomeapp.middle.view.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import it.dani.selfhomeapp.R

class NetworkServerErrorDialog(private val positiveSupplier : () -> Unit, private val negativeSupplier : () -> Unit) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(this.activity).apply {
            setTitle(R.string.titleHandleNoWifiNetworkResponseErr)
            setMessage(R.string.noWifiNetworkResponseErr)
            setPositiveButton(R.string.positiveHandleNoWifiNetworkResponseErr) { _, _ ->
                this@NetworkServerErrorDialog.positiveSupplier()
            }
            setNegativeButton(R.string.negativeHandleNoWifiNetworkResponseErr) {_,_ ->
                this@NetworkServerErrorDialog.negativeSupplier()
            }
        }.create()
    }
}
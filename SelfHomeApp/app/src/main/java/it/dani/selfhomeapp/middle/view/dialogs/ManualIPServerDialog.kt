package it.dani.selfhomeapp.middle.view.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import it.dani.selfhomeapp.R

class ManualIPServerDialog(private val onPositive : (String, String) -> Unit, private val onNegative : () -> Unit) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?) : Dialog {
        return AlertDialog.Builder(this.activity).apply {
            this.setTitle(R.string.titleHandleManualIp)
            this.setMessage(R.string.messageHandleManualIp)

            val input = EditText(this.context).apply {
                this@ManualIPServerDialog.context?.let{context ->
                    this.hint = context.getString(R.string.hintIPPortHandleManualIp)
                }
            }.also {
                this.setView(it)
            }

            this.setPositiveButton(R.string.positiveHandleManualIp) { _, _ ->
                val ip = input.text.toString().split(":")
                this@ManualIPServerDialog.onPositive(ip[0], ip[1])
            }
            setNegativeButton(R.string.negativeHandleManualIp) { _, _ ->
                this@ManualIPServerDialog.onNegative()
            }
        }.create()
    }
}
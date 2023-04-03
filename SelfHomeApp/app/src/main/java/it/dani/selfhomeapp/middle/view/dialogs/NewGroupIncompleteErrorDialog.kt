package it.dani.selfhomeapp.middle.view.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import it.dani.selfhomeapp.R

class NewGroupIncompleteErrorDialog(private val negativeSupplier : () -> Unit) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(this.activity).apply {
            setTitle(R.string.titleNewGroupIncompleteResponse)
            setMessage(R.string.newGroupIncompleteResponse)
            setNegativeButton(R.string.negativeNewGroupIncompleteResponse) { _, _ ->
                this@NewGroupIncompleteErrorDialog.negativeSupplier()
            }
        }.create()
    }
}
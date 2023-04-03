package it.dani.selfhomeapp.middle.view.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import it.dani.selfhomeapp.R
import java.util.*

class LanguageSettingDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(this.activity).apply {
            setTitle(R.string.titleLanguageRequest)

            val locales = Array<String>(Locale.getAvailableLocales().size) {
                Locale.getAvailableLocales()[it].displayName
            }

            var flagFind = false
            var currentActive = -1
            for(l in locales) {
                if(l == Locale.getDefault().displayName) {
                    flagFind = true
                } else if(!flagFind) {
                    currentActive++
                }
            }

            if(currentActive >= 0) {
                setSingleChoiceItems(locales,currentActive) {_,_ ->}
            }
        }.create()
    }
}
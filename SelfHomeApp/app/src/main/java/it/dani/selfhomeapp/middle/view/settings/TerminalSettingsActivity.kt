package it.dani.selfhomeapp.middle.view.settings

import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import it.dani.selfhomeapp.R
import it.dani.selfhomeapp.middle.controller.Controller
import it.dani.selfhomeapp.middle.controller.FindServer
import it.dani.selfhomeapp.middle.view.notification.NotificationUtilsK
import java.io.IOException

class TerminalSettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.terminal_settings)

        val terminalLayout = findViewById<View>(R.id.terminalLayout)

        val toolbar = findViewById<Toolbar>(R.id.app_toolbar).apply {
            this.overflowIcon = ContextCompat.getDrawable(this@TerminalSettingsActivity, R.drawable.more_vert)
        }
        setSupportActionBar(toolbar)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val findServerData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            this.intent.getSerializableExtra("SERVER", FindServer.Data::class.java)
        } else {
            this.intent.getSerializableExtra("SERVER") as FindServer.Data?
        }

        findServerData ?: finish()

        val terminalCode = findViewById<EditText>(R.id.terminalCode)

        try {
            val deviceController = Controller(findServerData!!.address,findServerData.port)
            findViewById<Button>(R.id.terminalExecute).apply {
                this.setOnClickListener {
                    NotificationUtilsK.createNotificationAlertChannel(this@TerminalSettingsActivity)

                    val notificationBuilder = NotificationCompat.Builder(this@TerminalSettingsActivity,NotificationUtilsK.ALERT_CHANNEL_ID).apply {
                        this.setContentText(this@TerminalSettingsActivity.resources.getString(R.string.notification_terminal_title))
                        this.setSmallIcon(R.mipmap.ic_launcher)
                        this.priority = NotificationManager.IMPORTANCE_MIN
                        this.setAutoCancel(true)
                    }

                    NotificationManagerCompat.from(this@TerminalSettingsActivity)
                    deviceController.freeText(terminalCode.text.toString(),{
                        notificationBuilder.setContentText("${this.resources.getString(R.string.notification_terminal_text)} $it")
                        //manager.notify(0x10, notificationBuilder.build())
                        Snackbar.make(terminalLayout, it, Snackbar.LENGTH_LONG).show()
                    }, {
                        notificationBuilder.setContentText("${this.resources.getString(R.string.notification_terminal_text)}  ${this.resources.getString(R.string.deviceSwitchGetStateErr)}")
                        //manager.notify(0x10, notificationBuilder.build())
                        Snackbar.make(terminalLayout, R.string.deviceSwitchGetStateErr, Snackbar.LENGTH_LONG).show()
                    })
                }
            }

        } catch (e : IOException) {
            e.printStackTrace()
        }

    }
}
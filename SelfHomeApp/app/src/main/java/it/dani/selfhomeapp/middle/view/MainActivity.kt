package it.dani.selfhomeapp.middle.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import it.dani.selfhomeapp.R
import it.dani.selfhomeapp.middle.controller.Controller
import it.dani.selfhomeapp.middle.controller.FindServer
import it.dani.selfhomeapp.middle.view.dialogs.ManualIPServerDialog
import it.dani.selfhomeapp.middle.view.dialogs.NoServerErrorDialog
import it.dani.selfhomeapp.middle.view.settings.LanguageSettingsActivity
import it.dani.selfhomeapp.middle.view.settings.ScreenSettingsActivity
import it.dani.selfhomeapp.middle.view.settings.TerminalSettingsActivity
import java.net.UnknownHostException
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private lateinit var serverData : FindServer.Data

    private lateinit var deviceController: Controller

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.devices)

        val toolbar = findViewById<Toolbar>(R.id.app_toolbar).apply {
            this.overflowIcon = ContextCompat.getDrawable(this@MainActivity,R.drawable.more_vert)
        }
        this.setSupportActionBar(toolbar)

        this.searchServer()
    }

    private fun searchServer() {
        val findServerData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            this.intent.getSerializableExtra("SERVER", FindServer.Data::class.java)
        } else {
            this.intent.getSerializableExtra("SERVER") as FindServer.Data?
        }

        if(findServerData == null) {
            FindServer().search ({ searchedServerData ->
                this.serverData = searchedServerData
                Log.i("SelfHome_settings", "Server find at ${this.serverData}")

                runOnUiThread { this.connectToServer() }
            },{
                //TODO chiamare fragment per chiedere ip manuale
                NoServerErrorDialog({
                    Intent(this,MainActivity::class.java).also {
                        startActivity(it)
                        finish()
                    }
                },{
                    ManualIPServerDialog({ip,port ->
                        this.serverData = FindServer.Data(ip,port.toInt())
                        this.connectToServer()
                    },{}).show(this.supportFragmentManager,"ADD-IP-SERVER")
                },{finish()}).show(this.supportFragmentManager, "NO-SERVER")
            })
        } else {
            this.serverData = findServerData
            Log.i("SelfHome_settings", "Server find at ${this.serverData}")
            this.connectToServer()
        }
    }

    private fun connectToServer() {
        findViewById<Button>(R.id.createNewGroupMenu).apply {
            this.setOnClickListener {
                Intent(this@MainActivity, GroupActivity::class.java).apply {
                    this.putExtra("SERVER", this@MainActivity.serverData)
                }.also { startActivity(it) }
            }
        }

        findViewById<SwipeRefreshLayout>(R.id.deviceRefreshView).apply {
            this.setOnRefreshListener {
                Intent(this@MainActivity,MainActivity::class.java).apply {
                    this.putExtra("SERVER",this@MainActivity.serverData)
                }.also { startActivity(it) }
            }
        }

        try {
            Executors.newSingleThreadExecutor().also {
                it.submit {
                    this.deviceController = Controller(this.serverData.address, this.serverData.port).also {dc ->
                        dc.getDevices({ x ->
                            runOnUiThread { this.createButton(x) }
                        },{
                            Snackbar.make(findViewById(R.id.mainView), R.string.noDeviceFoundErr, Snackbar.LENGTH_LONG).show()
                        })
                    }
                }
            }
        } catch (e: UnknownHostException) {
            e.printStackTrace()
        }
    }


    private fun createButton(items : Collection<String>) {
        val linLayout = findViewById<LinearLayout>(R.id.devices)

        items.forEach { item ->
            Log.d("SelfHome_button", "BUTTON: $item")
            Button(ContextThemeWrapper(this, R.style.deviceButton), null, R.style.deviceButton).apply {
                this.text = item

                this.setOnClickListener {
                    this@MainActivity.deviceController.toggleDevice(item,{ result ->
                        when(result) {
                            false -> Snackbar.make(linLayout, R.string.deviceSwitchSetStateErr, Snackbar.LENGTH_LONG).show()
                            true -> {
                                this@MainActivity.deviceController.getDeviceState(item, { state ->
                                    val message = when(state) {
                                        0 -> R.string.deviceSwitchSetStateOn
                                        else -> R.string.deviceSwitchSetStateOff
                                    }
                                    Snackbar.make(linLayout,message,Snackbar.LENGTH_LONG).show()
                                }, { Snackbar.make(linLayout,R.string.deviceSwitchGetStateErr,Snackbar.LENGTH_LONG).show() })
                            }
                        }
                    },{ Snackbar.make(linLayout,R.string.deviceSwitchGetStateErr,Snackbar.LENGTH_LONG).show() })
                }

                this.setOnLongClickListener {
                    this@MainActivity.deviceController.getDeviceState(item,{ result ->
                        Log.v("Device_get","SelfHome_button_device_get $item: $result")
                        val message = when(result) {
                            0 -> R.string.deviceGetStateOff
                            else -> R.string.deviceGetStateOn
                        }
                        Snackbar.make(linLayout,message,Snackbar.LENGTH_LONG).show()
                    }, { Snackbar.make(linLayout,R.string.deviceSwitchGetStateErr,Snackbar.LENGTH_LONG).show() })

                    true
                }
            }.also {
                linLayout.addView(it)
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menuInflater.inflate(R.menu.navigation_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.language_settings_menu -> {
                Intent(this,LanguageSettingsActivity::class.java).also { startActivity(it) }
                true
            }
            R.id.screen_settings_menu -> {
                Intent(this,ScreenSettingsActivity::class.java).also { startActivity(it) }
                true
            }
            R.id.terminal_settings_menu -> {
                Intent(this,TerminalSettingsActivity::class.java).apply {
                    this.putExtra("SERVER",this@MainActivity.serverData)
                }.also { startActivity(it) }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
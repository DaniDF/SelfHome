package it.dani.selfhomeapp.middle.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
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
import java.io.IOException

class GroupActivity : AppCompatActivity() {

    private lateinit var deviceController : Controller
    private lateinit var groupController : Controller

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.groups)

        val toolbar = findViewById<Toolbar>(R.id.app_toolbar).apply {
            this.overflowIcon = ContextCompat.getDrawable(this@GroupActivity,R.drawable.more_vert)
        }
        this.setSupportActionBar(toolbar)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val serverData = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            this.intent.getSerializableExtra("SERVER",FindServer.Data::class.java)
        } else {
            this.intent.getSerializableExtra("SERVER") as FindServer.Data
        } ?: throw IllegalArgumentException("Error server data")

        try {
            findViewById<Button>(R.id.createNewGroupMenu).apply {
                this.setOnClickListener {
                    Intent(this@GroupActivity,NewGroupActivity::class.java).apply {
                        this.putExtra("SERVER", serverData)
                    }.also {
                        startActivity(it)
                        finish()
                    }
                }
            }

            findViewById<SwipeRefreshLayout>(R.id.groupRefreshView).apply {
                this.setOnRefreshListener {
                    Intent(this@GroupActivity,GroupActivity::class.java).apply {
                        this.putExtra("SERVER", serverData)
                    }.also { startActivity(it) }
                }
            }

            this.deviceController = Controller(serverData.address,serverData.port)
            this.groupController = this.deviceController

            this.groupController.getGroups { groups ->
                runOnUiThread {
                    this.createButton(groups)
                }
            }
        } catch (e : IOException) {
            e.printStackTrace()
        }
    }

    private fun createButton(items : Collection<String>) {
        val linLayout = findViewById<LinearLayout>(R.id.devices)

        items.forEach { item ->
            Log.d("SelfHome_button", "BUTTON: $item")

            Button(ContextThemeWrapper(this,R.style.deviceButton), null, R.style.deviceButton).apply {
                this.text = item

                this.setOnClickListener {
                    this@GroupActivity.groupController.getDevicesFromGroup(item) { dns ->
                        dns.forEach { dn ->
                            this@GroupActivity.deviceController.getDeviceState(dn) { ds ->
                                this@GroupActivity.deviceController.setDeviceState(dn,if(ds != 0) 0 else 1, { result ->
                                    Snackbar.make(linLayout, R.string.groupSwitchState,Snackbar.LENGTH_LONG).show()
                                    Log.v("SelfHome_button_group_change" ,"$item : $ds ->  ${(if (ds != 0) 0 else 1)} => $result")
                                },{
                                    Snackbar.make(linLayout,R.string.groupSwitchStateErr,Snackbar.LENGTH_LONG).show()
                                    Log.e("SelfHome_button_group_change","Error changing group state")
                                })
                            }
                        }
                    }
                }
            }.also { linLayout.addView(it) }
        }
    }
}
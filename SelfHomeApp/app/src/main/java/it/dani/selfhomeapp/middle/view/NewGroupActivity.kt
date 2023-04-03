package it.dani.selfhomeapp.middle.view

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import it.dani.selfhomeapp.R
import it.dani.selfhomeapp.middle.icontroller.IDeviceController
import it.dani.selfhomeapp.middle.icontroller.IGroupController

class NewGroupActivity : AppCompatActivity() {
    
    private lateinit var deviceController: IDeviceController
    private lateinit var groupController: IGroupController
    
    private val msgs: MutableMap<String, String?> = HashMap()
    private var devices: Set<String> = HashSet()
    
    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.newgroup)
        
        val toolbar = findViewById<Toolbar>(R.id.app_toolbar).apply {
            this.overflowIcon = ContextCompat.getDrawable(this@NewGroupActivity, R.drawable.more_vert)
        }
        setSupportActionBar(toolbar)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        /*
        val intent = this.intent
        msgs["IP"] = intent.getStringExtra("IP")
        msgs["PORT"] = intent.getStringExtra("PORT")
        
        val isInModify = intent.getStringExtra("MODE") != null && intent.getStringExtra("MODE") == "MOD"
        val newGroupName = findViewById<EditText>(R.id.newGroupName)
        
        if (isInModify) {
            val title = findViewById<TextView>(R.id.labelNewGroupView)
            title.setText(R.string.modGroupView)
            newGroupName.inputType = InputType.TYPE_NULL
            newGroupName.setTextIsSelectable(false)
            newGroupName.setOnKeyListener { x: View?, y: Int, z: KeyEvent? -> true }
            msgs["GROUPNAME"] = intent.getStringExtra("GROUPNAME")
            newGroupName.setText(msgs["GROUPNAME"])
        }
        
        val createNewGroupMenu = findViewById<Button>(R.id.createNewGroupMenu)
        
        try {
            val controller = Controller(
                msgs["IP"]!!, msgs["PORT"]!!.toInt()
            )
            this.deviceController = controller
            this.groupController = controller
            this.deviceController.getDevices { x -> this.devices += x }
            
            while (devices.isEmpty());
            createRadioButton(devices)
            if (isInModify) groupController.getDevicesFromGroup(msgs["GROUPNAME"]!!) { x: Set<String> ->
                checkRadioButton(x)
                createNewGroupMenu.setOnClickListener { y: View? -> modifyGroup(x) }
            } else createNewGroupMenu.setOnClickListener { x: View? ->
                if (newGroupName.text.isEmpty()) NewGroupIncompleteErrorDialog().show(
                    supportFragmentManager, "BACK"
                ) else createNewGroup()
            }
            val deleteButton = findViewById<Button>(R.id.deleteGroupMenu)
            deleteButton.setOnClickListener { x: View? ->
                groupController.delGroup(msgs["GROUPNAME"]!!) { y: Boolean ->
                    if (y) Log.i(
                        "DEL",
                        "Delete group: " + msgs["GROUPNAME"]
                    ) else Log.e("DEL", "Failed delete group: " + msgs["GROUPNAME"])
                }
                finish()
            }
            if (!isInModify) deleteButton.visibility = View.GONE
        } catch (e: IOException) {
            e.printStackTrace()
        }*/
    }

    private fun createRadioButton(deviceNames: Set<String>) {
        val groups = findViewById<LinearLayout>(R.id.devices)
        for (d in deviceNames) {
            val check = CheckBox(this)
            check.text = d
            groups.addView(check)
        }
    }

    private fun checkRadioButton(deviceNames: Set<String>) {
        val groups = findViewById<LinearLayout>(R.id.devices)
        for (count in 1 until groups.childCount) {
            val cb = groups.getChildAt(count) as CheckBox
            if (deviceNames.contains(cb.text)) {
                cb.isChecked = true
            }
        }
    }

    private fun createNewGroup() {
        val newGroupName = findViewById<EditText>(R.id.newGroupName)
        if (newGroupName.text.length > 0) {
            groupController!!.addNewGroup(newGroupName.text.toString(), { x: Boolean ->
                if (x) {
                    val groups = findViewById<LinearLayout>(R.id.devices)
                    for (count in 1 until groups.childCount) {
                        val cb = groups.getChildAt(count) as CheckBox
                        if (cb.isChecked) {
                            val deviceName = cb.text.toString()
                            Log.d("NAME", deviceName)
                            groupController!!.addDeviceToGroup(
                                deviceName,
                                newGroupName.text.toString(),
                                { y: Boolean ->
                                    if (y) Log.i(
                                        "NEW",
                                        "Create new button: $deviceName"
                                    ) else Log.e("NEW", "Failed create new button: $deviceName")
                                }) { e: Exception? ->
                                Snackbar.make(
                                    findViewById(R.id.newGroupLayout),
                                    R.string.insertDeviceInGroupErr,
                                    Snackbar.LENGTH_LONG
                                )
                            }
                        }
                    }
                    finish()
                }
            }) { e: Exception? ->
                Snackbar.make(
                    findViewById(R.id.newGroupLayout),
                    R.string.createNewGroupErr,
                    Snackbar.LENGTH_LONG
                )
            }
        }
    }

    private fun modifyGroup(prevGroups: Set<String>) {
        val groups = findViewById<LinearLayout>(R.id.devices)
        for (count in 1 until groups.childCount) {
            val cb = groups.getChildAt(count) as CheckBox
            val deviceName = cb.text.toString()
            if (cb.isChecked && !prevGroups.contains(cb.text)) {
                Log.d("NAME", "ADD new device: $deviceName")
                groupController!!.addDeviceToGroup(
                    deviceName,
                    msgs["GROUPNAME"]!!,
                    { y: Boolean ->
                        if (y) Log.i(
                            "NEW",
                            "Create new button: $deviceName"
                        ) else Log.e("NEW", "Failed create new button: $deviceName")
                    }) { e: Exception? ->
                    Snackbar.make(
                        findViewById(R.id.newGroupLayout),
                        R.string.insertDeviceInGroupErr,
                        Snackbar.LENGTH_LONG
                    )
                }
            } else if (!cb.isChecked && prevGroups.contains(cb.text)) {
                Log.d("NAME", "DEL device: $deviceName")
                groupController!!.delDeviceToGroup(
                    deviceName,
                    msgs["GROUPNAME"]!!,
                    { y: Boolean ->
                        if (y) Log.i(
                            "NEW",
                            "Delete button: $deviceName"
                        ) else Log.e("NEW", "Failed delete button: $deviceName")
                    }) { e: Exception? ->
                    Snackbar.make(
                        findViewById(R.id.newGroupLayout),
                        R.string.removeDeviceFromGroupErr,
                        Snackbar.LENGTH_LONG
                    )
                }
            }
        }
        finish()
    }
}
package it.dani.selfhomeapp.middle.view

import android.app.Activity
import android.content.Intent
import android.util.Log

class Utils {
    companion object {
        fun createNewIntent(activity : Activity, clas : Class<Any>, map : Map<String,String>) : Intent
        {
            val intent = Intent(activity,clas)

            for((k,v) in map)
            {
                intent.putExtra(k,v)
            }

            activity.startActivity(intent)

            return intent
        }

        fun createNewIntent(activity : Activity, clas : Class<Any>) : Intent
        {
            return createNewIntent(activity, clas,HashMap())
        }

        fun createNewIntent(activity : Activity, clas : String) : Intent
        {
            val intent = Intent(clas)
            activity.startActivity(intent)
            return intent
        }

        fun reload(activity : Activity, clas : Class<Any>, map : Map<String,String>)
        {
            Log.d("Reload","Reload activity")
            Utils.createNewIntent(activity,clas,map)
            activity.finish()
        }

        fun reload(activity : Activity, clas : Class<Any>)
        {
            Log.d("Reload","Reload activity")
            Utils.createNewIntent(activity,clas)
            activity.finish()
        }
    }
}
package com.shrouk.alarmmanager

import android.app.Dialog
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class AlertReceiver:BroadcastReceiver() {
    private val NOTIFICATION_ID = 0
    private lateinit var notificationManager: NotificationManager

    override fun onReceive(context: Context, intent: Intent?) {

             context.startService(Intent(context,AlertService::class.java))
//        notificationManager = getSystemService(
//            context,
//            NotificationManager::class.java
//        ) as NotificationManager
//        createDownloadNotificationChannel(context)
//
//        //notificationManager.createNotificationChannel()
//        notificationManager.sendNotification("Alarm just fired",context)
//            Log.d("Alarm Ring", "Alarm just fired")


    }
//    fun showDialog(){
//
//        val dialog = Dialog()
//        dialog.setContentView(R.layout.alert_dialog)
//        dialog.show()
//    }


}


package com.shrouk.alarmmanager

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService

class AlertReceiver:BroadcastReceiver() {
    private val NOTIFICATION_ID=0
    private lateinit var notificationManager: NotificationManager


    override fun onReceive(context: Context, intent: Intent?) {

        notificationManager = getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager
        createDownloadNotificationChannel(context)

        //notificationManager.createNotificationChannel()
        notificationManager.sendNotification("Alarm just fired",context)
            Log.d("Alarm Ring", "Alarm just fired")


        }
    }

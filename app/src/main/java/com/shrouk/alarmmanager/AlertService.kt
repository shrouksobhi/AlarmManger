package com.shrouk.alarmmanager

import android.app.AlertDialog
import android.app.Service
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.view.WindowManager





class AlertService : Service() {
    override fun onBind(intent: Intent?): IBinder? {

     return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
      super.onStartCommand(intent, flags, startId)
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Test dialog")
        builder.setIcon(androidx.transition.R.drawable.notification_bg)
        builder.setMessage("your alarm manager fired")
        builder.setPositiveButton("OK",
            DialogInterface.OnClickListener { dialog, which ->
                //Do something
                dialog.dismiss()
            })
        builder.setNegativeButton(
            "Close"
        ) { dialog, whichButton -> dialog.dismiss() }
        val alert = builder.create()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            alert.window!!.setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY)
        }

    //    WindowManager.LayoutParams.TYPE_SYSTEM_ALERT)
        alert.show()

        return START_NOT_STICKY
    }

}
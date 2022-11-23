package com.shrouk.alarmmanager

import android.Manifest
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var alarmtextview: TextView
    private lateinit var btncancel: Button
    private lateinit var btnstart: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        alarmtextview = findViewById(R.id.textView)
        btncancel = findViewById(R.id.btncancel)
        btnstart = findViewById(R.id.btnstartalarm)
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.SYSTEM_ALERT_WINDOW
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Log.e("PERMISION", "onCreate:Granted ")
        } else {
            Log.d("PERMISION", "onCreate:Denied ")
            requestOverlayPermission()

        }
        btncancel.setOnClickListener {
            cancelAlarm()
        }

        btnstart.setOnClickListener {
            val picker =
                MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(12)
                    .setMinute(10)
                    .setTitleText("Select Appointment time")
                    .build()

            picker.addOnPositiveButtonClickListener {
                val selectedTime = Calendar.getInstance()
                selectedTime[0, 0, 0, picker.hour, picker.minute] = 0
                //        selectedTime.set(HOURS,picker.hour)

                val calendar: Calendar = Calendar.getInstance()
                val formattedTime =
                    SimpleDateFormat("hh:mm:ss a", Locale.getDefault()).format(selectedTime.time)
//                Log.e("TIME",selectedTime.timeInMillis.toString())
                alarmtextview.text = "Alarm set to " + formattedTime
                if (Build.VERSION.SDK_INT >= 23) {
                    calendar.set(
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH),
                        picker.hour,
                        picker.minute,
                        0
                    )
//                } else {
//                    calendar.set(
//                        calendar.get(Calendar.YEAR),
//                        calendar.get(Calendar.MONTH),
//                        calendar.get(Calendar.DAY_OF_MONTH),
//                        picker.hour,
//                       picker.currentMinute, 0
//                    )
                }
                Log.e("CALENDER", calendar.timeInMillis.toString())
                setAlarm(calendar.timeInMillis)
            }

            picker.addOnNegativeButtonClickListener {
                // call back code
            }
            picker.addOnCancelListener {
                // call back code
            }
            picker.addOnDismissListener {
                // call back code
            }

            picker.show(supportFragmentManager, "tag")
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestOverlayPermission() {
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:$packageName")
        )
        startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE)
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
            Log.d("Result", "onActivityResult: $resultCode")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    AlertDialog.Builder(this)
                        .setMessage("يجب تفعيل اذن الظهور فوق التطبيقات لتسطيع استقبال الطلبات الجديدة")
                        .setPositiveButton(
                            "موافق"
                        ) { dialogInterface: DialogInterface?, i: Int -> requestOverlayPermission() }
                        .setIcon(android.R.drawable.ic_dialog_alert).show()
                    // You have permission
                }
            }
        }

    }
//    private fun isPermissionGranted(): Boolean {
//        return ContextCompat.checkSelfPermission(
//            this,
//            Manifest.permission.SYSTEM_ALERT_WINDOW
//        ) == PackageManager.PERMISSION_GRANTED
//
//
//    }

    private fun setAlarm(timeInMillis: Long) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlertReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
        alarmManager.setRepeating(
            AlarmManager.RTC,
            timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
        //alarmManager.setExact(AlarmManager.RTC_WAKEUP,timeInMillis,pendingIntent)
        Toast.makeText(this, "Alarm is set", Toast.LENGTH_SHORT).show()
    }


    private fun cancelAlarm() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlertReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

        alarmManager.cancel(pendingIntent)
    }

    companion object {
        val ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 1
    }
}
//private class MyAlarm : BroadcastReceiver() {
//    override fun onReceive(
//        context: Context,
//        intent: Intent
//    ) {
//        Log.d("Alarm Ring", "Alarm just fired")
//
//    }
//}

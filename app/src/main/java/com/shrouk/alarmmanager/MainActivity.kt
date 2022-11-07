package com.shrouk.alarmmanager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.datatype.DatatypeConstants.HOURS


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

            picker.addOnPositiveButtonClickListener{
                val selectedTime = Calendar.getInstance()
                selectedTime[0, 0, 0, picker.hour, picker.minute] = 0
//                    selectedTime.set(HOURS,picker.hour)
                val formattedTime = SimpleDateFormat("hh:mm:ss a", Locale.getDefault()).format(selectedTime.time)
                Log.e("TIME",selectedTime.timeInMillis.toString())
                alarmtextview.text="Alarm set to "+formattedTime

                    setAlarm(selectedTime.timeInMillis)
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

              picker.show(supportFragmentManager,"tag")    }
    }

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
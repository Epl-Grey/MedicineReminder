package com.example.medicineremindernew.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiverNotify : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val name = intent.getStringExtra("name")!!
        val time = intent.getStringExtra("time")!!
        NotificationService.makeNotify(context, name, time)
    }
}

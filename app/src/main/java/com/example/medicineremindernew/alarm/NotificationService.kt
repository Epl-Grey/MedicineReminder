package com.example.medicineremindernew.alarm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.medicineremindernew.R
import com.example.medicineremindernew.activities.MainActivity

object NotificationService {
    private const val PILLS_CHANNEL_ID = 99
    fun makeNotify(ctx: Context, title: String, time: String) {
        Log.i("Notification", "NOTIFY!!! $title")
        val homeIntent = Intent(ctx, MainActivity::class.java)

        val pendingIntent: PendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(ctx, 0, homeIntent, PendingIntent.FLAG_MUTABLE)
        } else {
            PendingIntent.getActivity(ctx, 0, homeIntent, PendingIntent.FLAG_IMMUTABLE)
        }

        val mBuilder = NotificationCompat.Builder(ctx, title)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.bell)
            .setContentTitle(title)
            .setContentText("Примите в $time")
            .setSmallIcon(R.drawable.bell__1_)
            .setPriority(Notification.PRIORITY_MAX)

        val notificationManager: NotificationManager = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            "pills",
            "Pills",
            NotificationManager.IMPORTANCE_HIGH
        )

        notificationManager.createNotificationChannel(channel)
        mBuilder.setChannelId("pills")
        val notification = mBuilder.build()

//        notification.flags |= Notification.FLAG_ONGOING_EVENT;
        notificationManager.notify(title, 0, notification)
    }
}

package com.example.medicineremindernew.alarm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.medicineremindernew.fragments.HomeFragment;
import com.example.medicineremindernew.R;

public class NotificationService {
    static void makeNotify(Context ctx, String title, String time){
        System.out.println("NOTIFY!!!");
        NotificationManager mNotificationManager;

        Intent ii = new Intent(ctx, HomeFragment.class);

        PendingIntent pendingIntent;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            pendingIntent = PendingIntent.getActivity(ctx, 0, ii, PendingIntent.FLAG_MUTABLE);
        }else{
            pendingIntent = PendingIntent.getActivity(ctx, 0, ii, PendingIntent.FLAG_IMMUTABLE);
        }

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(ctx, title)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.bell)
                        .setContentTitle(title)
                        .setContentText("Примите в "+ time)
                        .setSmallIcon(R.drawable.frame_78)
                        .setPriority(Notification.PRIORITY_MAX);



        mNotificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    title,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(title);
        }

        Notification notification = mBuilder.build();

//        notification.flags |= Notification.FLAG_ONGOING_EVENT;

        mNotificationManager.notify(title, 0, notification);
    }
}

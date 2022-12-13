package com.example.medicineremindernew;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationMaker {
    static void makeNotify(Context ctx, String title, String time){
        System.out.println("NOTIFY!!!");
        NotificationManager mNotificationManager;

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(ctx, "notify_001");
        Intent ii = new Intent(ctx, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText("Примите в " + time);
        bigText.setBigContentTitle(title);
//        bigText.setSummaryText(subTitle);

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.bell);
        mBuilder.setContentTitle("Your Title");
        mBuilder.setContentText("Your text");
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);

        mNotificationManager =
                (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "Your_channel_id";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        mNotificationManager.notify(0, mBuilder.build());
    }
}

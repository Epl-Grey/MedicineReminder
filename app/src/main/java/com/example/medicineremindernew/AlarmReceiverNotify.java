package com.example.medicineremindernew;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class AlarmReceiverNotify extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        String name = extras.getString("name");
        NotificationMaker.makeNotify(context, name);

    }
}

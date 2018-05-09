package com.example.brianb.reminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;

public class DismissReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int reminderId = intent.getIntExtra("NOTIFICATION_ID", 0);
        NotificationUtil.cancelNotification(context, reminderId);
    }
}

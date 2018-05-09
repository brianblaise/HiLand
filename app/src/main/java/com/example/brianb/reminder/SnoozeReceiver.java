package com.example.brianb.reminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;



public class SnoozeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ReminderDatabaseHelper database = ReminderDatabaseHelper.getInstance(context);
        int reminderId = intent.getIntExtra("NOTIFICATION_ID", 0);
        if (reminderId != 0 && database.isNotificationPresent(reminderId)) {
            Reminder reminder = database.getNotification(reminderId);
            NotificationUtil.createNotification(context, reminder);
        }
        database.close();
    }
}
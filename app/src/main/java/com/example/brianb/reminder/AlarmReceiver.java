package com.example.brianb.reminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ReminderDatabaseHelper database = ReminderDatabaseHelper.getInstance(context);
        Reminder reminder = database.getNotification(intent.getIntExtra("NOTIFICATION_ID", 0));
        reminder.setNumberShown(reminder.getNumberShown() + 1);
        database.addNotification(reminder);

        NotificationUtil.createNotification(context, reminder);

        // Check if new alarm needs to be set
        if (reminder.getNumberToShow() > reminder.getNumberShown() || Boolean.parseBoolean(reminder.getForeverState())) {
            AlarmUtil.setNextAlarm(context, reminder, database);
        }
        Intent updateIntent = new Intent("BROADCAST_REFRESH");
        LocalBroadcastManager.getInstance(context).sendBroadcast(updateIntent);
        database.close();
    }
}
package com.example.brianb.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Calendar;

public class AlarmUtil {

    public static void setAlarm(Context context, Intent intent, int notificationId, Calendar calendar) {
        intent.putExtra("NOTIFICATION_ID", notificationId);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }

    public static void cancelAlarm(Context context, Intent intent, int notificationId) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }



    public static void setNextAlarm(Context context, Reminder reminder, ReminderDatabaseHelper database) {
        Calendar calendar = DateAndTimeUtil.parseDateAndTime(reminder.getDateAndTime());
        calendar.set(Calendar.SECOND, 0);

        switch (reminder.getRepeatType()) {
            case Reminder.HOURLY:
                calendar.add(Calendar.HOUR, reminder.getInterval());
                break;
            case Reminder.DAILY:
                calendar.add(Calendar.DATE, reminder.getInterval());
                break;
            case Reminder.WEEKLY:
                calendar.add(Calendar.WEEK_OF_YEAR, reminder.getInterval());
                break;
            case Reminder.MONTHLY:
                calendar.add(Calendar.MONTH, reminder.getInterval());
                break;
            case Reminder.YEARLY:
                calendar.add(Calendar.YEAR, reminder.getInterval());
                break;
        }

        reminder.setDateAndTime(DateAndTimeUtil.toStringDateAndTime(calendar));
        database.addNotification(reminder);

        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        setAlarm(context, alarmIntent, reminder.getId(), calendar);
    }
}
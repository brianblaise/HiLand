package com.example.brianb.reminder;

import android.content.Context;

import com.example.brianb.demo1.R;


public class TextFormatUtil {



    public static String formatAdvancedRepeatText(Context context, int repeatType, int interval) {
        String typeText;
        switch (repeatType) {
            default:
            case Reminder.HOURLY:
                typeText = context.getResources().getQuantityString(R.plurals.hour, interval);
                break;
            case Reminder.DAILY:
                typeText = context.getResources().getQuantityString(R.plurals.day, interval);
                break;
            case Reminder.WEEKLY:
                typeText = context.getResources().getQuantityString(R.plurals.week, interval);
                break;
            case Reminder.MONTHLY:
                typeText = context.getResources().getQuantityString(R.plurals.month, interval);
                break;
            case Reminder.YEARLY:
                typeText = context.getResources().getQuantityString(R.plurals.year, interval);
                break;
        }
        return context.getString(R.string.repeats_every, interval, typeText);
    }
}
package com.example.brianb.reminder;

public class Reminder {

    // Reminder types
    public static final int ACTIVE = 1;
    public static final int INACTIVE = 2;

    // Repetition types
    public static final int DOES_NOT_REPEAT = 0;
    public static final int HOURLY = 1;
    public static final int DAILY = 2;
    public static final int WEEKLY = 3;
    public static final int MONTHLY = 4;
    public static final int YEARLY = 5;

    private int id;
    private String title;
    private String dateAndTime;
    private int repeatType;
    private String foreverState;
    private int numberToShow;
    private int numberShown;
    private int interval;
  /*  private String icon;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }*/
    //daily, weekly, monthly, yearly ONLY

    public int getId() {
        return id;
    }

    public Reminder setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Reminder setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public Reminder setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
        return this;
    }

    public String getDate() {
        return dateAndTime.substring(0, 8);
    }

    public int getRepeatType() {
        return repeatType;
    }

    public Reminder setRepeatType(int repeatType) {
        this.repeatType = repeatType;
        return this;
    }

    public String getForeverState() {
        return foreverState;
    }

    public Reminder setForeverState(String foreverState) {
        this.foreverState = foreverState;
        return this;
    }

    public int getNumberToShow() {
        return numberToShow;
    }

    public Reminder setNumberToShow(int numberToShow) {
        this.numberToShow = numberToShow;
        return this;
    }

    public int getNumberShown() {
        return numberShown;
    }

    public Reminder setNumberShown(int numberShown) {
        this.numberShown = numberShown;
        return this;
    }

    public int getInterval() {
        return interval;
    }

    public Reminder setInterval(int interval) {
        this.interval = interval;
        return this;
    }
}
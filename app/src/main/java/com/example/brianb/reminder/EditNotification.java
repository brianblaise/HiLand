package com.example.brianb.reminder;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.brianb.demo1.R;
import com.example.brianb.reminder.AlarmUtil;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditNotification extends AppCompatActivity implements RepeatSelector.RepeatSelectionListener {
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    @BindView(R.id.create_coordinator)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.notification_title)
    TextView notification_title;
    @BindView(R.id.edit_time)
    TextView timeText;
    @BindView(R.id.date_row)
    TextView dateText;
    @BindView(R.id.repeat_day)
    TextView repeatText;
    @BindView(R.id.switch_toggle)
    SwitchCompat foreverSwitch;
    @BindView(R.id.show_times_number)
    EditText timesEditText;
    @BindView(R.id.forever_row)
    LinearLayout foreverRow;
    @BindView(R.id.bottom_row)
    LinearLayout bottomRow;
    @BindView(R.id.bottom_view)
    View bottomView;
    @BindView(R.id.show)
    TextView showText;
    @BindView(R.id.joinEdit)
    TextView joinEdit;
    @BindView(R.id.times)
    TextView timesText;
    @BindView(R.id.error_time)
    ImageView imageWarningTime;
    @BindView(R.id.error_date)
    ImageView imageWarningDate;
    @BindView(R.id.error_show)
    ImageView imageWarningShow;
    @BindView(R.id.editToolbar) Toolbar toolbar;

    TextView testDate;
    private Calendar calendar;
    private Calendar joinCalendar;
    private int timesShown = 0;
    private int timesToShow = 1;
    private int repeatType;
    private int id;
    private int interval = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_notification);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        if (getActionBar() != null) getActionBar().setDisplayHomeAsUpEnabled(true);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle(null);
      //set retrieved date to dateText i.e dateRow
        dateText.setText(joinEdit.getText());
        calendar = Calendar.getInstance();

        joinCalendar = DateAndTimeUtil.parseDateAndTime(joinEdit.getText().toString());
        joinCalendar = Calendar.getInstance();
        //joinCalendar = DateAndTimeUtil.parseDateAndTime(joinEdit.getText().toString());
        //joinCalendar.set(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH);


        if (id == 0) {
            ReminderDatabaseHelper database = ReminderDatabaseHelper.getInstance(this);
            id = database.getLastNotificationId() + 1;
            database.close();
        } else {
            assignReminderValues();
        }
    }


    public void assignReminderValues() {
        // Prevent keyboard from opening automatically
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        ReminderDatabaseHelper database = ReminderDatabaseHelper.getInstance(this);
        Reminder reminder = database.getNotification(id);
        database.close();

        timesShown = reminder.getNumberShown();
        repeatType = reminder.getRepeatType();
        interval = reminder.getInterval();

        calendar = DateAndTimeUtil.parseDateAndTime(reminder.getDateAndTime());

        showText.setText(getString(R.string.times_shown_edit, reminder.getNumberShown())); //shown x times out of y
        notification_title.setText(reminder.getTitle());
        dateText.setText(DateAndTimeUtil.toStringReadableDate(calendar));
        timeText.setText(DateAndTimeUtil.toStringReadableTime(calendar, this));
        timesEditText.setText(String.valueOf(reminder.getNumberToShow()));
        timesText.setVisibility(View.VISIBLE);

       //Repeat Types
        if (reminder.getRepeatType() != Reminder.DOES_NOT_REPEAT) {
            if (reminder.getInterval() > 1) {
                repeatText.setText(TextFormatUtil.formatAdvancedRepeatText(this, repeatType, interval));
            } else {
                repeatText.setText(getResources().getStringArray(R.array.repeat_array)[reminder.getRepeatType()]);
            }
            showFrequency(true);
        }

        if (Boolean.parseBoolean(reminder.getForeverState())) {
            foreverSwitch.setChecked(true);
            bottomRow.setVisibility(View.GONE);
        }
    }
    //Repeat Frequency
    public void showFrequency(boolean show) {
        if (show) {
            foreverRow.setVisibility(View.VISIBLE);
            bottomRow.setVisibility(View.VISIBLE);
            bottomView.setVisibility(View.VISIBLE);
        } else {
            foreverSwitch.setChecked(false);
            foreverRow.setVisibility(View.GONE);
            bottomRow.setVisibility(View.GONE);
            bottomView.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.time_row)
    public void timePicker() {
        TimePickerDialog TimePicker = new TimePickerDialog(EditNotification.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(android.widget.TimePicker timePicker, int hour, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                timeText.setText(DateAndTimeUtil.toStringReadableTime(calendar, getApplicationContext()));
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), DateFormat.is24HourFormat(this));
        TimePicker.show();
    }
    public void extractDay(){

        DateAndTimeUtil.parseDateAndTime(joinEdit.getText().toString());

    }

    @OnClick(R.id.date_row)

    public void datePicker(View view) {

        DatePickerDialog DatePicker = new DatePickerDialog(EditNotification.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker DatePicker, int year, int month, int dayOfMonth) {

                joinCalendar.set(Calendar.YEAR, 2);
                joinCalendar.set(Calendar.MONTH, 2);
                joinCalendar.set(Calendar.DAY_OF_MONTH, 1);

              // dateText.setText(DateAndTimeUtil.toStringReadableDate(joinCalendar));
            }
        }, joinCalendar.get(Calendar.YEAR), joinCalendar.get(Calendar.MONTH), joinCalendar.get(Calendar.DAY_OF_MONTH));
        DatePicker.show();
    }
 /*   public void setOnDateSetListener(DatePickerDialog.OnDateSetListener onDateSetListener) {
        this.onDateSetListener = onDateSetListener;
    }
    public void createDialog(){
        joinCalendar = DateAndTimeUtil.parseDateAndTime(joinEdit.getText().toString());
        final Calendar c = Calendar.getInstance();
        int year = joinCalendar.get(Calendar.YEAR);
        int month = joinCalendar.get(Calendar.MONTH);
        int day = joinCalendar.get(Calendar.DAY_OF_MONTH);


        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog datePicker = new DatePickerDialog(this, onDateSetListener, year, month, day);

        datePicker.show();
    } */

    @OnClick(R.id.repeat_row)
    public void repeatSelector() {
        DialogFragment dialog = new RepeatSelector();
        dialog.show(getSupportFragmentManager(), "RepeatSelector");
    }

    @Override
    public void onRepeatSelection(DialogFragment dialog, int which, String repeatText) {
        interval = 1;
        repeatType = which;
        this.repeatText.setText(repeatText);
        if (which == Reminder.DOES_NOT_REPEAT) {
            showFrequency(false);
        } else {
            showFrequency(true);
        }
    }

    public void saveNotification() {
        ReminderDatabaseHelper database = ReminderDatabaseHelper.getInstance(this);
        Reminder reminder = new Reminder()
                .setId(id)
                .setTitle(notification_title.getText().toString())
                .setDateAndTime(DateAndTimeUtil.toStringDateAndTime(calendar))
                .setRepeatType(repeatType)
                .setForeverState(Boolean.toString(foreverSwitch.isChecked()))
                .setNumberToShow(timesToShow)
                .setNumberShown(timesShown)
                .setInterval(interval);

        database.addNotification(reminder);

        database.close();
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        calendar.set(Calendar.SECOND, 0);
        AlarmUtil.setAlarm(this, alarmIntent, reminder.getId(), calendar);
        finish();
    }

    @OnClick(R.id.forever_row)
    public void toggleSwitch() {
        foreverSwitch.toggle();
        if (foreverSwitch.isChecked()) {
            bottomRow.setVisibility(View.GONE);
        } else {
            bottomRow.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.switch_toggle)
    public void switchClicked() {
        if (foreverSwitch.isChecked()) {
            bottomRow.setVisibility(View.GONE);
        } else {
            bottomRow.setVisibility(View.VISIBLE);
        }
    }

    public void validateInput() {
        imageWarningShow.setVisibility(View.GONE);
        imageWarningTime.setVisibility(View.GONE);
        imageWarningDate.setVisibility(View.GONE);
        Calendar nowCalendar = Calendar.getInstance();

        if (timeText.getText().equals(getString(R.string.time_now))) {
            calendar.set(Calendar.HOUR_OF_DAY, nowCalendar.get(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, nowCalendar.get(Calendar.MINUTE));
        }
        if (dateText.getText().equals(getString(R.string.date_today))) {
            calendar.set(Calendar.YEAR, nowCalendar.get(Calendar.YEAR));
            calendar.set(Calendar.MONTH, nowCalendar.get(Calendar.MONTH));
            calendar.set(Calendar.DAY_OF_MONTH, nowCalendar.get(Calendar.DAY_OF_MONTH));
        }

        // Check if the number of times to show notification is empty
        if (timesEditText.getText().toString().isEmpty()) {
            timesEditText.setText("1");
        }

        timesToShow = Integer.parseInt(timesEditText.getText().toString());
        if (repeatType == Reminder.DOES_NOT_REPEAT) {
            timesToShow = timesShown + 1;
        }

        // Check if selected date is before today's date
        if (DateAndTimeUtil.toLongDateAndTime(calendar) < DateAndTimeUtil.toLongDateAndTime(nowCalendar)) {
            Snackbar.make(coordinatorLayout, R.string.toast_past_date, Snackbar.LENGTH_SHORT).show();
            imageWarningTime.setVisibility(View.VISIBLE);
            imageWarningDate.setVisibility(View.VISIBLE);

            // Check if title is empty
        } else if (notification_title.getText().toString().trim().isEmpty()) {
            Snackbar.make(coordinatorLayout, R.string.toast_title_empty, Snackbar.LENGTH_SHORT).show();

            // Check if times to show notification is too low
        } else if (timesToShow <= timesShown && !foreverSwitch.isChecked()) {
            Snackbar.make(coordinatorLayout, R.string.toast_higher_number, Snackbar.LENGTH_SHORT).show();
            imageWarningShow.setVisibility(View.VISIBLE);
        } else {
            saveNotification();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.save_property:
                validateInput();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


package com.hit.yeelightcatalogapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;


public class ScheduleActivity extends AppCompatActivity {
    Button dateBtn;
    TextView dateTv, timeTv;
    int currentYear, currentMonth, currentDay, currentHour, currentMinute;
    int chosenYear, chosenMonth, chosenDay, chosenHour, chosenMinute;
    Calendar c;
    DatePickerDialog dpd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        TextView name_tv = findViewById(R.id.name_schedule_tv);
        String name = getIntent().getStringExtra("name");
        name_tv.setText(name_tv.getText().toString()+" "+name);


        datePicker();

        timePicker();

        setInCalender();
    }

    private void setInCalender() {
        Button calendar_btn = findViewById(R.id.btn_calendar);
        calendar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c= Calendar.getInstance();
                c.set(chosenYear, (chosenMonth - 1), chosenDay, chosenHour, chosenMinute);
                Intent intent = new Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.Events.EVENT_LOCATION, getResources().getString(R.string.address))
                        .putExtra(CalendarContract.Events.TITLE, getResources().getString(R.string.Meeting))
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, c.getTimeInMillis());
                startActivity(intent);
            }
        });
    }

    private void timePicker() {
        c = Calendar.getInstance();
        currentHour = c.get(Calendar.HOUR_OF_DAY);
        currentMinute = c.get(Calendar.MINUTE);

        timeTv = findViewById(R.id.tv_time);
        Button timeBtn = findViewById(R.id.btn_time);
        timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(ScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        if (minute < 10) {
                            timeTv.setText(hour + ":" + "0" + minute);
                        } else {
                            timeTv.setText(hour + ":" + minute);
                        }
                        chosenHour = hour;
                        chosenMinute = minute;
                    }
                }, currentHour, currentMinute, true);
                timePickerDialog.show();
            }
        });
    }

    private void datePicker() {
        dateBtn = findViewById(R.id.btn_date);
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                currentYear =c.get(Calendar.YEAR);
                currentMonth=c.get(Calendar.MONTH);
                currentDay =c.get(Calendar.DAY_OF_MONTH);

                dateTv = findViewById(R.id.tv_date);
                dpd = new DatePickerDialog(ScheduleActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateTv.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        chosenYear = year;
                        chosenMonth = month + 1;
                        chosenDay = dayOfMonth;

                    }
                }, currentYear, currentMonth, currentDay);
                dpd.show();
            }
        });
    }
}
package com.seakwon.tman;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ProgressBar;
import android.widget.TextView;

import junit.framework.Assert;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView mTextViewAttendanceTime;
    TextView mTextViewLeavingTime;

    Button mButtonToday;

    CalendarView mCalendarView;

    ProgressBar mProgressBarDay;
    ProgressBar mProgressBarWeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initialize();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Set current time to mTextViewAttendanceTime
     */
    public void setAttendanceTime() {
        Date date = new Date();
        date.setTime(Calendar.getInstance().getTimeInMillis());
        mTextViewAttendanceTime.setText(new SimpleDateFormat(
                "HH:mm:ss", Locale.KOREA).format(date));
    }

    /**
     * Set date to mTextViewAttendanceTime
     * @param date
     */
    public void setAttendanceTime(Date date) {
        mTextViewAttendanceTime.setText(new SimpleDateFormat(
                "HH:mm:ss", Locale.KOREA).format(date));
    }

    /**
     * Set current time to mTextViewLeavingTime
    */
    public void setLeavingTime() {
        Date date = new Date();
        String time = new SimpleDateFormat("HH:mm:ss", Locale.KOREA).format(date);
        mTextViewLeavingTime.setText(time);
    }

    /**
     * Set date to mTextViewLeavingTime
     * @param date
     */
    public void setLeavingTime(Date date) {
        mTextViewLeavingTime.setText(new SimpleDateFormat(
                "HH:mm:ss", Locale.KOREA).format(date));
    }

    public void setButtonTodayText() {
        mButtonToday.setText(getResources().getString(R.string.today) + " (" + new SimpleDateFormat(
                "yyyy-MM-dd", Locale.KOREA).format(new Date()) + ")");
    }

    public void setStatusForSelectedDate(int year, int month, int dayOfMonth) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonModifyAttendanceTime:
                setAttendanceTime();
                break;
            case R.id.buttonModifyLeavingTime:
                setLeavingTime();
                break;
            case R.id.buttonToday:
                Date date = new Date();
                mCalendarView.setDate(date.getTime());
                break;
            case R.id.calendarView:
                Date today = new Date(mCalendarView.getDate());
                break;
            default:
                Assert.assertFalse("Should not reached", true);
        }
    }

    private void initialize() {
        mTextViewAttendanceTime = (TextView) findViewById(R.id.textViewAttendanceTime);
        setAttendanceTime();

        mTextViewLeavingTime = (TextView) findViewById(R.id.textViewLeavingTime);
        setLeavingTime();

        mProgressBarDay = (ProgressBar) findViewById(R.id.progressBarDay);
        mProgressBarWeek = (ProgressBar) findViewById(R.id.progressBarWeek);

        mButtonToday = (Button) findViewById(R.id.buttonToday);
        mButtonToday.setOnClickListener(this);
        setButtonTodayText();

        findViewById(R.id.buttonModifyAttendanceTime).setOnClickListener(this);
        findViewById(R.id.buttonModifyLeavingTime).setOnClickListener(this);

        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        mCalendarView.setOnClickListener(this);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                setStatusForSelectedDate(year, month, dayOfMonth);
            }
        });
        mCalendarView.setDate(new Date().getTime());
    }
}

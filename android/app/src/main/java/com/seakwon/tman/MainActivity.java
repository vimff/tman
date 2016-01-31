package com.seakwon.tman;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView mTextViewAttendanceTime;
    TextView mTextViewLeavingTime;
    TextView mTextViewDayStatus;
    TextView mTextViewWeekStatus;
    Button mButtonModifyAttendanceTime;
    Button mButtonModifyLeavingTime;

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
        String time = new SimpleDateFormat("HH:mm:ss", Locale.KOREA).format(date.getTime());
        mTextViewLeavingTime.setText(time);
    }

    public void setDayHours() {
        mTextViewDayStatus.setText("0 / 8");
    }

    public void setWeekHours() {
        mTextViewWeekStatus.setText("0 / 40");
    }

    /**
     * Set date to mTextViewLeavingTime
     * @param date
     */
    public void setLeavingTime(Date date) {
        mTextViewLeavingTime.setText(new SimpleDateFormat(
                "HH:mm:ss", Locale.KOREA).format(date));
    }

    private void initialize() {
        mTextViewAttendanceTime = (TextView) findViewById(R.id.textViewAttendanceTime);
        mTextViewLeavingTime = (TextView) findViewById(R.id.textViewLeavingTime);
        mButtonModifyAttendanceTime = (Button) findViewById(R.id.buttonModifyAttendanceTime);
        mButtonModifyLeavingTime = (Button) findViewById(R.id.buttonModifyLeavingTime);
        mTextViewDayStatus = (TextView) findViewById(R.id.textViewDayStatus);
        mTextViewWeekStatus = (TextView) findViewById(R.id.textViewWeekStatus);


        mButtonModifyAttendanceTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAttendanceTime();
            }
        });


        mButtonModifyLeavingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLeavingTime();
            }
        });

        setAttendanceTime();
        setLeavingTime();
        setDayHours();
        setWeekHours();
    }
}

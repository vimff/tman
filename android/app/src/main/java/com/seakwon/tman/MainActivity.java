package com.seakwon.tman;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Spinner mSpinnerWeekdays;
    TextView mTextViewAttendanceTime;
    TextView mTextViewLeavingTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTextViewAttendanceTime = (TextView) findViewById(R.id.textViewAttendanceTime);
        mTextViewAttendanceTime.setText(new SimpleDateFormat(
                "HH:mm:ss", Locale.KOREA).format(new Date()));

        mTextViewLeavingTime = (TextView) findViewById(R.id.textViewLeavingTime);
        mTextViewLeavingTime.setText(new SimpleDateFormat(
                "HH:mm:ss", Locale.KOREA).format(new Date()));
//
//        String[] weekdayArray = getResources().getStringArray(R.array.weekdays);
//        ArrayAdapter<String> weekdayAdapter = new ArrayAdapter<String>(
//                this, android.R.layout.simple_spinner_dropdown_item, weekdayArray);
//        mSpinnerWeekdays = (Spinner) findViewById(R.id.spinnerDays);
//        mSpinnerWeekdays.setAdapter(weekdayAdapter);
//        mSpinnerWeekdays.setSelection(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
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
}

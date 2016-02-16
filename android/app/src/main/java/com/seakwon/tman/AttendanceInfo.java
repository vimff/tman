package com.seakwon.tman;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

// FIXME: Better to separate SQL queries from this class later.
// But do we really need it? (or can we introduce simple ORM library?)
public class AttendanceInfo {
    private SQLiteDatabase mDB;
    private SimpleDateFormat mSFD;

    public AttendanceInfo(Context context) {
        mSFD = new SimpleDateFormat("HH:mm:ss");
        mDB = (new SQLiteOpenHelper(context, "attendance.db", null, 1) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL("CREATE TABLE AttendanceLog(date integer primary key, is_entered integer)");
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                // If need to migrate DB, then we can do something here.
                throw new UnsupportedOperationException();
            }
        }).getWritableDatabase();
    }

    public void close() {
        mDB.close();
    }

    // This function will be called by AttendanceDetector.onReceive() when receiving a intent from MDM.
    public void updateLog(Date date, boolean isEntered) {
        String timestampForDB = String.valueOf(date.getTime() / 1000);
        String isEnteredForDB = isEntered ? "1" : "0";
        mDB.execSQL("INSERT INTO AttendanceLog VALUES(" + timestampForDB + ", " + isEnteredForDB + ")");
    }

    public String getAttendanceTimeAsString(Date date) {
        String timestampeForDB = String.valueOf(date.getTime() / 1000);
        Cursor cursor = mDB.rawQuery(
            "SELECT date FROM AttendanceLog " +
            "WHERE date(" + timestampeForDB + ", 'unixepoch') == date(date, 'unixepoch') and is_entered == 1 " +
            "ORDER BY date LIMIT 1", null);
        if (cursor.moveToFirst())
            return mSFD.format(new Date(cursor.getLong(0) * 1000));
        return null;
    }

    public String getLeavingTimeAsString(Date date) {
        String timestampeForDB = String.valueOf(date.getTime() / 1000);
        Cursor cursor = mDB.rawQuery(
            "SELECT date FROM AttendanceLog " +
            "WHERE date(" + timestampeForDB + ", 'unixepoch') == date(date, 'unixepoch') and is_entered == 0 " +
            "ORDER BY date DESC LIMIT 1", null);
        if (cursor.moveToFirst())
            return mSFD.format(new Date(cursor.getLong(0) * 1000));
        return null;
    }
}
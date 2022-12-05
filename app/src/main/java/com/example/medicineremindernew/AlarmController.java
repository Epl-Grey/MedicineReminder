package com.example.medicineremindernew;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Calendar;

public class AlarmController {
    Context context;
    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor cursor;

    AlarmController(Context context){
        this.context = context;
    }

    void refresh(){
        sqlHelper = new DatabaseHelper(context);
        db = sqlHelper.getWritableDatabase();
        cursor = db.rawQuery(String.format("SELECT * FROM %s ORDER BY %s", DatabaseHelper.TABLE, DatabaseHelper.COLUMN_DATE1), new String[]{});
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            System.out.println("ID:     " + cursor.getInt(0));
            System.out.println("Name:   " + cursor.getString(1));
            System.out.println("Value:  " + cursor.getInt(2));
            System.out.println("Dosage: " + cursor.getString(3));
            System.out.println("Date 1: " + cursor.getString(4));
            System.out.println("Date 2: " + cursor.getString(5));
            System.out.println("Time 1: " + cursor.getString(6));
            System.out.println("Time 2: " + cursor.getString(7));
            System.out.println("Time 3: " + cursor.getString(8));
            System.out.println("Time 4: " + cursor.getString(9));
            System.out.println("Time 5: " + cursor.getString(10));
            System.out.println("Time 6: " + cursor.getString(11));
            System.out.println("Times : " + cursor.getString(12));
            System.out.println();
            cursor.moveToNext();
        }
    }

    void add_alarm(Pill pill){

        String[] time = pill.time.split(":");
        String[] date1 = pill.date1.split("\\.");
        String[] date2 = pill.date2.split("\\.");


        Calendar calendar_date1 = Calendar.getInstance();
        calendar_date1.set(Calendar.MILLISECOND, 0);
        calendar_date1.set(Calendar.SECOND, 0);
//        calendar_date1.set(Calendar.MINUTE, Integer.parseInt(pill));

        Calendar alarm_calendar = Calendar.getInstance();
        alarm_calendar.set(Calendar.MILLISECOND, 0);
        alarm_calendar.set(Calendar.SECOND, 0);
        alarm_calendar.set(Calendar.MINUTE, Integer.parseInt(time[1]));
        alarm_calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(alarm_calendar.getTimeInMillis(), getAlarmInfoPendingIntent());

        alarmManager.setAlarmClock(alarmClockInfo, getAlarmActionPendingIntent());
    }

    private PendingIntent getAlarmInfoPendingIntent() {
        Intent alarmInfoIntent = new Intent(context, MainActivity.class);
        alarmInfoIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getActivity(context, 0, alarmInfoIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private PendingIntent getAlarmActionPendingIntent() {
        Intent intent = new Intent(context, AlarmActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}

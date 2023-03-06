package com.example.medicineremindernew;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class AlarmController {
    Context context;
    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor cursor;

    AlarmController(Context context){
        this.context = context;
    }

    void refresh(){
        System.out.println("\n\n\n\n\n\n\n\n\n");
        sqlHelper = new DatabaseHelper(context);
        db = sqlHelper.getWritableDatabase();
        cursor = db.rawQuery(String.format("SELECT * FROM %s ORDER BY %s", DatabaseHelper.TABLE, DatabaseHelper.COLUMN_DATE1), new String[]{});
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            int value = cursor.getInt(2);
            String dosage = cursor.getString(3);
            String date1 = cursor.getString(4);
            String date2 = cursor.getString(5);
            String time1 = cursor.getString(6);
            String time2 = cursor.getString(7);
            String time3 = cursor.getString(8);
            String time4 = cursor.getString(9);
            String time5 = cursor.getString(10);
            String time6 = cursor.getString(11);
            String times = cursor.getString(12);


            System.out.println("ID:     " + id);
            System.out.println("Name:   " + name);
            System.out.println("Value:  " + value);
            System.out.println("Dosage: " + dosage);
            System.out.println("Date 1: " + date1);
            System.out.println("Date 2: " + date2);
            System.out.println("Time 1: " + time1);
            System.out.println("Time 2: " + time2);
            System.out.println("Time 3: " + time3);
            System.out.println("Time 4: " + time4);
            System.out.println("Time 5: " + time5);
            System.out.println("Time 6: " + time6);
            System.out.println("Times : " + times);
            System.out.println();

            add_alarm(new Pill(id, name, value, dosage, date1, date2, time1));
            add_alarm(new Pill(id, name, value, dosage, date1, date2, time2));
            add_alarm(new Pill(id, name, value, dosage, date1, date2, time3));
            add_alarm(new Pill(id, name, value, dosage, date1, date2, time4));
            add_alarm(new Pill(id, name, value, dosage, date1, date2, time5));
            add_alarm(new Pill(id, name, value, dosage, date1, date2, time6));

            add_alarm_notify(new Pill(id, name, value, dosage, date1, date2, time1));
            add_alarm_notify(new Pill(id, name, value, dosage, date1, date2, time2));
            add_alarm_notify(new Pill(id, name, value, dosage, date1, date2, time3));
            add_alarm_notify(new Pill(id, name, value, dosage, date1, date2, time4));
            add_alarm_notify(new Pill(id, name, value, dosage, date1, date2, time5));
            add_alarm_notify(new Pill(id, name, value, dosage, date1, date2, time6));
            cursor.moveToNext();
        }
    }

    void add_alarm(Pill pill){

        if (pill.time.equals("") || pill.date1.equals("дата") || pill.date2.equals("дата"))return;

        String[] time = pill.time.split(":");
        String[] date1 = pill.date1.split("\\.");
        String[] date2 = pill.date2.split("\\.");

        System.out.println("\n\n===ALARMS===");

        Calendar calendar_date1 = Calendar.getInstance();
        calendar_date1.set(Calendar.MILLISECOND, 0);
        calendar_date1.set(Calendar.SECOND, 0);
        calendar_date1.set(Calendar.MINUTE, Integer.parseInt(time[1]));
        calendar_date1.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
        calendar_date1.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date1[0]));
        calendar_date1.set(Calendar.MONTH, Integer.parseInt(date1[1])-1);
        calendar_date1.set(Calendar.YEAR, Integer.parseInt(date1[2]));

        Calendar calendar_date2 = Calendar.getInstance();
        calendar_date2.set(Calendar.MILLISECOND, 0);
        calendar_date2.set(Calendar.SECOND, 0);
        calendar_date2.set(Calendar.MINUTE, Integer.parseInt(time[1]));
        calendar_date2.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
        calendar_date2.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date2[0]));
        calendar_date2.set(Calendar.MONTH, Integer.parseInt(date2[1])-1);
        calendar_date2.set(Calendar.YEAR, Integer.parseInt(date2[2]));

        Calendar alarm_calendar = Calendar.getInstance();
        alarm_calendar.set(Calendar.MILLISECOND, 0);
        alarm_calendar.set(Calendar.SECOND, 0);
        alarm_calendar.set(Calendar.MINUTE, Integer.parseInt(time[1]));
        alarm_calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));

        System.out.println("Date1  " + calendar_date1.get(Calendar.DAY_OF_MONTH) + "." + calendar_date1.get(Calendar.MONTH) + "." + calendar_date1.get(Calendar.YEAR));
        System.out.println("Date2  " + calendar_date2.get(Calendar.DAY_OF_MONTH) + "." + calendar_date2.get(Calendar.MONTH) + "." + calendar_date2.get(Calendar.YEAR));

        System.out.println(alarm_calendar.getTime() + " > " + calendar_date1.getTime() + " = " + (alarm_calendar.compareTo(calendar_date1) > 0));
        System.out.println(alarm_calendar.getTime() + " > " + calendar_date2.getTime() + " = " + (alarm_calendar.compareTo(calendar_date2) < 0));

        if(alarm_calendar.compareTo(calendar_date1) > 0 & alarm_calendar.compareTo(calendar_date2) < 0){
            System.out.println(Calendar.getInstance().getTime() + " > " + alarm_calendar.getTime() + " = " + (Calendar.getInstance().compareTo(alarm_calendar) > 0));
            if(Calendar.getInstance().compareTo(alarm_calendar) > 0){
                alarm_calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(alarm_calendar.getTimeInMillis(), getAlarmInfoPendingIntent());
            alarmManager.setAlarmClock(alarmClockInfo, getAlarmActionPendingIntent());
        }
    }

    private PendingIntent getAlarmInfoPendingIntent() {
        Intent alarmInfoIntent = new Intent(context, MainActivity.class);
        alarmInfoIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return PendingIntent.getActivity(context, 0, alarmInfoIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
        }else{
            return PendingIntent.getActivity(context, 0, alarmInfoIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
    }

    private PendingIntent getAlarmActionPendingIntent() {
        Intent intent = new Intent(context, AlarmActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
        }else{
            return PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
    }

    void add_alarm_notify(Pill pill){
        if (pill.time.equals("") || pill.date1.equals("дата") || pill.date2.equals("дата"))return;

        String[] time = pill.time.split(":");
        String[] date1 = pill.date1.split("\\.");
        String[] date2 = pill.date2.split("\\.");

        System.out.println("\n\n===NOFICATIONS===");

        System.out.println("Date1: " + Arrays.toString(date1));
        System.out.println("Date2: " + Arrays.toString(date2));

        Calendar calendar_date1 = Calendar.getInstance();
        calendar_date1.set(Calendar.MILLISECOND, 0);
        calendar_date1.set(Calendar.SECOND, 0);
        calendar_date1.set(Calendar.MINUTE, Integer.parseInt(time[1]));
        calendar_date1.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
        calendar_date1.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date1[0]));
        calendar_date1.set(Calendar.MONTH, Integer.parseInt(date1[1])-1);
        calendar_date1.set(Calendar.YEAR, Integer.parseInt(date1[2]));

        Calendar calendar_date2 = Calendar.getInstance();
        calendar_date2.set(Calendar.MILLISECOND, 0);
        calendar_date2.set(Calendar.SECOND, 0);
        calendar_date2.set(Calendar.MINUTE, Integer.parseInt(time[1]));
        calendar_date2.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
        calendar_date2.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date2[0]));
        calendar_date2.set(Calendar.MONTH, Integer.parseInt(date2[1])-1);
        calendar_date2.set(Calendar.YEAR, Integer.parseInt(date2[2]));

        Calendar alarm_calendar = Calendar.getInstance();
        alarm_calendar.set(Calendar.MILLISECOND, 0);
        alarm_calendar.set(Calendar.SECOND, 0);
        alarm_calendar.set(Calendar.MINUTE, Integer.parseInt(time[1]));
        alarm_calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));

        System.out.println("Date1  " + calendar_date1.get(Calendar.DAY_OF_MONTH) + "." + calendar_date1.get(Calendar.MONTH) + "." + calendar_date1.get(Calendar.YEAR));
        System.out.println("Date2  " + calendar_date2.get(Calendar.DAY_OF_MONTH) + "." + calendar_date2.get(Calendar.MONTH) + "." + calendar_date2.get(Calendar.YEAR));

        System.out.println(alarm_calendar.getTime() + " > " + calendar_date1.getTime() + " = " + (alarm_calendar.compareTo(calendar_date1) > 0));
        System.out.println(alarm_calendar.getTime() + " > " + calendar_date2.getTime() + " = " + (alarm_calendar.compareTo(calendar_date2) < 0));

        if(alarm_calendar.compareTo(calendar_date1) > 0 & alarm_calendar.compareTo(calendar_date2) < 0){
            System.out.println(Calendar.getInstance().getTime() + " > " + alarm_calendar.getTime() + " = " + (Calendar.getInstance().compareTo(alarm_calendar) > 0));
            if(Calendar.getInstance().compareTo(alarm_calendar) > 0){
                alarm_calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
            Intent intent = new Intent(context, AlarmReceiverNotify.class);
            intent.putExtra("name", pill.name);
            SimpleDateFormat df = new SimpleDateFormat("HH:mm");
            intent.putExtra("time", df.format(alarm_calendar.getTime()));
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);

            alarm_calendar.add(Calendar.MINUTE, -15);

            AlarmManager alarmManager = (AlarmManager)context.getSystemService(ALARM_SERVICE);

            System.out.println(pill.name + " " + alarm_calendar.getTime());
            alarmManager.set(AlarmManager.RTC_WAKEUP, alarm_calendar.getTimeInMillis(), pendingIntent);
            System.out.println("setAlarmNotify");
        }


    }
}

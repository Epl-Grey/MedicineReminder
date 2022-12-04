package com.example.medicineremindernew;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
}

package com.example.medicineremindernew;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
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
        cursor = db.rawQuery(String.format("SELECT * FROM %s ORDER BY %s, %s", DatabaseHelper.TABLE, DatabaseHelper.COLUMN_DATE1), new String[]{});
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){

            cursor.moveToNext();
        }
    }
}

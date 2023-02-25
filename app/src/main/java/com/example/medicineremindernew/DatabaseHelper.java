package com.example.medicineremindernew;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "pillInfo.db"; // название бд
    private static final int SCHEMA = 1; // версия базы данных
    static final String TABLE = "pillsettings"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_VALUE = "value";
    public static final String COLUMN_DOSAGE = "dosage";
    public static final String COLUMN_DATE1 = "date1";
    public static final String COLUMN_DATE2 = "date2";
    public static final String COLUMN_TIME1 = "time1";
    public static final String COLUMN_TIME2 = "time2";
    public static final String COLUMN_TIME3 = "time3";
    public static final String COLUMN_TIME4 = "time4";
    public static final String COLUMN_TIME5 = "time5";
    public static final String COLUMN_TIME6 = "time6";
    public static final String COLUMN_VALUETIME = "valuetime";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 9);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "+TABLE+" (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT, "
                + COLUMN_VALUE + " INTEGER, "
                + COLUMN_DOSAGE + " TEXT, "
                + COLUMN_DATE1 + " TEXT, "
                + COLUMN_DATE2 + " TEXT, "
                + COLUMN_TIME1 + " TEXT, "
                + COLUMN_TIME2 + " TEXT, "
                + COLUMN_TIME3 + " TEXT, "
                + COLUMN_TIME4 + " TEXT, "
                + COLUMN_TIME5 + " TEXT, "
                + COLUMN_TIME6 + " TEXT, "
                + COLUMN_VALUETIME + " TEXT );");

//        //добавление начальных данных
//        db.execSQL("INSERT INTO "+ TABLE +" ("
//                + COLUMN_NAME
//                + ", " + COLUMN_VALUE
//                + ", " + COLUMN_DOSAGE
//                + ", " + COLUMN_DATE1
//                + ", " + COLUMN_DATE2
//                + ", " + COLUMN_TIME1
//                + ", " + COLUMN_TIME2
//                + ", " + COLUMN_TIME3
//                + ", " + COLUMN_TIME4
//                + ", " + COLUMN_TIME5
//                + ", " + COLUMN_TIME6
//                + ", " + COLUMN_VALUETIME
//                + ") VALUES ('Працетомол', 2, 'Таблеток', '12.05.2022', '12.06.2022', '9:30', '10:30', '11:30', '13:30', '15:30', '17:30', 'ШЕСТЬ РАЗ В ДЕНЬ');");
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }



}

package com.example.medicineremindernew;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.medicineremindernew.firebase.BolusData;
import com.example.medicineremindernew.firebase.PillData;

public class BolusHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "bolusInfo.db"; // название бд
    private static final int SCHEMA = 1; // версия базы данных
    public static final String TABLE = "pillsettings"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_GLUKOZA = "name";
    public static final String COLUMN_XE = "value";
    public static final String COLUMN_EAT = "dosage";
    public static final String COLUMN_INSULIN = "date1";
    public static final String COLUMN_CORECT = "date2";
    public static final String COLUMN_RESULT = "time1";
    public static final String COLUMN_CALCEAT = "time2";
    public static final String COLUMN_CALCCORECT = "time3";
    public static final String COLUMN_CALCRESULT = "time4";
    public static final String COLUMN_TIME = "time5";


    public BolusHelper(Context context) {
        super(context, DATABASE_NAME, null, 10);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "+TABLE+" (" + COLUMN_ID
                + " TEXT PRIMARY KEY,"
                + COLUMN_GLUKOZA + " TEXT, "
                + COLUMN_XE + " INTEGER, "
                + COLUMN_EAT + " TEXT, "
                + COLUMN_INSULIN + " TEXT, "
                + COLUMN_CORECT + " TEXT, "
                + COLUMN_RESULT + " TEXT, "
                + COLUMN_CALCEAT + " TEXT, "
                + COLUMN_CALCCORECT + " TEXT, "
                + COLUMN_CALCRESULT + " TEXT, "
                + COLUMN_TIME + " TEXT );");

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

    public void insertBolus(BolusData bolus){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID, bolus.getBolusId());
        cv.put(COLUMN_GLUKOZA, bolus.getGlukoza());
        cv.put(COLUMN_XE, bolus.getXE());
        cv.put(COLUMN_EAT, bolus.getEat());
        cv.put(COLUMN_INSULIN, bolus.getInsulin());
        cv.put(COLUMN_CORECT, bolus.getCorect());
        cv.put(COLUMN_RESULT, bolus.getResult());
        cv.put(COLUMN_CALCEAT, bolus.getCalcEat());
        cv.put(COLUMN_CALCCORECT, bolus.getCalcCorect());
        cv.put(COLUMN_CALCRESULT, bolus.getCalcResult());
        cv.put(COLUMN_TIME, bolus.getTime());


        db.insert(TABLE, null, cv);
    }

    public void cleanDB(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE);
    }


}

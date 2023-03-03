package com.example.medicineremindernew;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.medicineremindernew.firebase.PillsManager;
import com.example.medicineremindernew.firebase.UsersManager;

import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ListView pillList;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor pillCursor;
    Cursor dataCursor;
    SimpleCursorAdapter pillAdapter;
    SimpleCursorAdapter dataAdapter;

    Intent intent;
    Button addPill;
    Button calendar;

    DatePickerDialog datePickerDialog;

    public static long userId;
    PillsManager pillsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        addPill = findViewById(R.id.addButton);
        calendar= findViewById(R.id.calendar);
        Button settings=findViewById(R.id.settings);
        TextView title = findViewById(R.id.medicine_re);
        Intent sett = new Intent(this, SettingsActivity.class);
        settings.setOnClickListener(view -> startActivity(sett));
        intent = new Intent(this, AddingPill.class);
        addPill.setOnClickListener(view -> startActivity(intent));
        RelativeLayout inf=findViewById(R.id.inform);
        Intent informIntent = new Intent(this, InformActivity.class);
        inf.setOnClickListener(view -> startActivity(informIntent));


        pillList = findViewById(R.id.list);
        pillList.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getApplicationContext(),InformActivity.class);
            intent.putExtra("id", id);
            userId = id;
            startActivity(intent);
        });

        calendar.setOnClickListener(v -> {
            openDatePickerAfter(calendar);
            readFromDb(calendar);
        });

        databaseHelper = new DatabaseHelper(getApplicationContext());
        AlarmController alarmController = new AlarmController(this);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alarmController.refresh();
            }
        });

        alarmController.refresh();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 1);
        System.out.println(calendar.getTime());
        String time = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
        alarmController.add_alarm_notify(new Pill(5, "Боярошник", 10, "шт", "1.02.2023", "31.02.2023", time));

        SharedPreferences sharedPreference = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        System.out.println(sharedPreference.getString("userName", "userId don't set"));
        pillsManager = new PillsManager(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        // открываем подключение
        db = databaseHelper.getReadableDatabase();
        //получаем данные из бд в виде курсора
        pillCursor = db.rawQuery("select " + DatabaseHelper.COLUMN_ID + ", " + DatabaseHelper.COLUMN_NAME  + ", " + DatabaseHelper.COLUMN_VALUETIME + ", " + DatabaseHelper.COLUMN_TIME1 + " from " + DatabaseHelper.TABLE, null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[]{DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_VALUETIME, DatabaseHelper.COLUMN_TIME1};
        // создаем адаптер, передаем в него курсор
        pillAdapter = new SimpleCursorAdapter(this, R.layout.row_layout,
                pillCursor, headers, new int[]{R.id.name, R.id.kl, R.id.time}, 0);
        pillList.setAdapter(pillAdapter);


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        pillCursor.close();
    }

    private void initDatePicker(Button calDate) {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            String date = month + "." + day + "." + year;
            calDate.setText(date);
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }


    public void openDatePickerAfter(View view) {
        initDatePicker(calendar);
        datePickerDialog.show();
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }

    public void readFromDb(Button button) {
        db = databaseHelper.getReadableDatabase();

        dataCursor = db.query(DatabaseHelper.TABLE, null, null, null, null, null, null);
        String strDate = "01 11 22";
        String[] words = strDate.split(" ");
        System.out.println(words);
        if (dataCursor.moveToFirst()) {
            int id = dataCursor.getColumnIndex(DatabaseHelper.COLUMN_ID);
            int d1 = dataCursor.getColumnIndex(DatabaseHelper.COLUMN_DATE1);
            int d2 = dataCursor.getColumnIndex(DatabaseHelper.COLUMN_DATE2);

            do {
//                dataCursor.getString(d1);


            } while (dataCursor.moveToNext());
        } else{
            System.out.println("тd");
        }
        dataCursor.close();
    }

}
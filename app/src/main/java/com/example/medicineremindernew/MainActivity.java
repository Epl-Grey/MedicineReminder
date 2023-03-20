package com.example.medicineremindernew;

import static com.example.medicineremindernew.CalendarUtils.daysInWeekArray;
import static com.example.medicineremindernew.CalendarUtils.monthYearFromDate;
import static com.example.medicineremindernew.CalendarUtils.numberOfDays;
import static com.example.medicineremindernew.CalendarUtils.selectedDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.medicineremindernew.alarm.AlarmController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

public class
MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener{

    ListView pillList;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor pillCursor;
    Cursor dataCursor;
    Cursor testCursor;
    PillCursorAdapter pillAdapter;
    public static DatabaseHelper databaseHelper1;
    public static SQLiteDatabase db2;

    LinearLayoutManager linearLayoutManager;
    Intent intent;

    public Intent navigator;
    Button addPill;
    ImageButton navBtn;
    TextView calendar;
    LocalDate date;
    DatePickerDialog datePickerDialog;
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    public ArrayList<LocalDate> days;
    public ArrayList<String> numberWeek;
    public AlarmController alarmController;



    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper1 = new DatabaseHelper(this);
        db2 = databaseHelper1.getReadableDatabase();
        addPill = findViewById(R.id.addButton);
        calendar= findViewById(R.id.monthYearTV);
        Button settings=findViewById(R.id.settings);
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        RelativeLayout inf = findViewById(R.id.inform);
        pillList = findViewById(R.id.list);
        databaseHelper = new DatabaseHelper(this);
        db = databaseHelper.getReadableDatabase();
        Cursor addPillCursor = db.rawQuery("select " + databaseHelper.COLUMN_ID + ", " + databaseHelper.COLUMN_DATE1 + ", " + databaseHelper.COLUMN_DATE2 + ", "+databaseHelper.COLUMN_NAME + ", " + databaseHelper.COLUMN_VALUETIME + ", " + databaseHelper.COLUMN_TIME1 + " from " + databaseHelper.TABLE + " where  " +  databaseHelper.COLUMN_ID + " = " + " 1", null);


        intent = new Intent(this, AddingPill.class);
        Intent sett = new Intent(this, SettingsActivity.class);
        Intent informIntent = new Intent(this, InformActivity.class);

        settings.setOnClickListener(view -> startActivity(sett));
        addPill.setOnClickListener(view -> startActivity(intent));
        inf.setOnClickListener(view -> startActivity(informIntent));

        CalendarUtils.selectedDate = LocalDate.now();
        setWeekView();
        date = CalendarUtils.selectedDate;
        monthYearText.setOnClickListener(v -> openDatePickerAfter());

        pillList.setOnItemClickListener((parent, view, position, id) -> {
            TextView idTextView = view.findViewById(R.id.id_storage); // Yeah, TextView for storing data :)
            Intent intent = new Intent(getApplicationContext(), InformActivity.class);
            intent.putExtra("id", idTextView.getText().toString());
            startActivity(intent);
        });

        calendar.setOnClickListener(v -> {
            openDatePickerAfter(calendar);
            readFromDb();
        });

//        databaseHelper = new DatabaseHelper(this);
//        db = databaseHelper.getReadableDatabase();
//        pillCursor = db.rawQuery("select " + DatabaseHelper.COLUMN_ID + ", " + DatabaseHelper.COLUMN_NAME  + ", " + DatabaseHelper.COLUMN_VALUETIME + ", " + DatabaseHelper.COLUMN_TIME1 + " from " + DatabaseHelper.TABLE, null);
//        pillAdapter = new PillCursorAdapter(this, pillCursor);
//        pillList.setAdapter(pillAdapter);

        databaseHelper = new DatabaseHelper(getApplicationContext());
        alarmController = new AlarmController(this);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 1);
//        alarmController.add_alarm_notify(new Pill("boyaroshnik", "Боярошник", 10, "шт", "1.02.2023", "1.09.2023", time));

        alarmController.refresh();

        SharedPreferences sharedPreference = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        System.out.println(sharedPreference.getString("userName", "userId don't set"));

      //  onCalendarItem();
    }


    //
//    @Override
//    public void onResume() {
//        super.onResume();
//        pillsManager = new PillsManager(this);
//        pillsManager.setListener((pills) -> {
//            System.out.println("PILLS CHANGED");
//            db = databaseHelper.getReadableDatabase();
//            pillCursor = db.rawQuery("SELECT " + DatabaseHelper.COLUMN_ID + ", " + DatabaseHelper.COLUMN_NAME  + ", " + DatabaseHelper.COLUMN_VALUETIME + ", " + DatabaseHelper.COLUMN_TIME1 + " FROM " + DatabaseHelper.TABLE, null);
//            pillAdapter.changeCursor(pillCursor);
//            pillAdapter.notifyDataSetChanged();
//
//            alarmController.refresh();
//            return null;
//        });
//    }
    String id;
    String data;
    String data2;
    String[] str;
    String[] str2;
    String dataSplit;
    LocalDate localDate;
    String dateSplit2;
    LocalDate localDate2;
    public void onCalendarItem(DatabaseHelper databaseHelper1, SQLiteDatabase db) {
        testCursor = db.rawQuery("select " + databaseHelper1.COLUMN_ID + ", " + databaseHelper1.COLUMN_DATE1 + ", " + databaseHelper1.COLUMN_DATE2 + ", "+databaseHelper1.COLUMN_NAME + ", " + databaseHelper1.COLUMN_VALUETIME + ", " + databaseHelper1.COLUMN_TIME1 + " from " + databaseHelper1.TABLE, null);
        testCursor.moveToFirst();
        int length = testCursor.getCount();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println("selectedDate " + selectedDate);


        for (int i = 1; i <= length; i++) {

            System.out.println("length " + length);
            id = testCursor.getString(0);
            data = testCursor.getString(1);
            data2 = testCursor.getString(2);

            str = data.split("\\.");
            str2 = data2.split("\\.");

            dataSplit = str[2] + "-" + str[1] + "-" + str[0];
            localDate = LocalDate.parse(dataSplit);
            System.out.println("str " + localDate);

            dateSplit2 = str2[2] + "-" + str2[1] + "-" + str2[0];
            localDate2 = LocalDate.parse(dateSplit2);
            System.out.println("str2 " + localDate2);

            if (selectedDate.isAfter(localDate) && selectedDate.isBefore(localDate2)) {
                System.out.println(" ----- if complete -----");
                System.out.println(" ids " + id);
                pillCursor = db.rawQuery("SELECT " + DatabaseHelper.COLUMN_ID + ", " + DatabaseHelper.COLUMN_NAME  + ", " + DatabaseHelper.COLUMN_VALUETIME + ", " + DatabaseHelper.COLUMN_TIME1 + " FROM " + DatabaseHelper.TABLE + " WHERE " + DatabaseHelper.COLUMN_ID + " =   \""+ id + "\"", null);
                pillAdapter = new PillCursorAdapter(this, pillCursor);
                pillList.setAdapter(pillAdapter);



            }

            testCursor.moveToNext();

        }

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        pillCursor.close();
    }
    public void openDatePickerAfter(View view) {
        initDatePicker();
        datePickerDialog.show();
    }
    public void readFromDb() {
        db = databaseHelper.getReadableDatabase();

        dataCursor = db.query(DatabaseHelper.TABLE, null, null, null, null, null, null);
        String strDate = "01 11 2022";
        String[] words = strDate.split(" ");
        String date = words[2] + "-" + words[1] + "-" + words[0];
        System.out.println("words " + date);
        if (dataCursor.moveToFirst()) {
            int id = dataCursor.getColumnIndex(DatabaseHelper.COLUMN_ID);
            int d1 = dataCursor.getColumnIndex(DatabaseHelper.COLUMN_DATE1);
            int d2 = dataCursor.getColumnIndex(DatabaseHelper.COLUMN_DATE2);

        //    System.out.println("d1 " + d1);

            do {
//                dataCursor.getString(d1);


            } while (dataCursor.moveToNext());
        } else{
            System.out.println("тd");
        }
        dataCursor.close();
    }

    private void setWeekView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        days = daysInWeekArray(CalendarUtils.selectedDate);
        numberWeek = numberOfDays(CalendarUtils.selectedDate);
        System.out.println("numberWeek " + numberWeek);
        CalendarAdapter calendarAdapter = new CalendarAdapter(this, days, numberWeek, (MainActivity) this);
        linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        calendarRecyclerView.setLayoutManager(linearLayoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);

    }


    public void previousWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setWeekView();
    }

    public void nextWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setWeekView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        CalendarUtils.selectedDate = date;
        setWeekView();
    }
    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            date = LocalDate.of(year, month, day);
            onItemClick(1, date);
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }


    public void openDatePickerAfter() {
        initDatePicker();
        datePickerDialog.show();
    }

}
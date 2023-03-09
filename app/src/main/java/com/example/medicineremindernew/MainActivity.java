package com.example.medicineremindernew;

import static com.example.medicineremindernew.CalendarUtils.daysInWeekArray;
import static com.example.medicineremindernew.CalendarUtils.monthYearFromDate;
import static com.example.medicineremindernew.CalendarUtils.selectedDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.example.medicineremindernew.alarm.AlarmController;
import com.example.medicineremindernew.alarm.Pill;
import com.example.medicineremindernew.firebase.PillsManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener{

    ListView pillList;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor pillCursor;
    Cursor dataCursor;
    Cursor testCursor;
    Cursor numberIdCursor;
    PillCursorAdapter pillAdapter;
    SimpleCursorAdapter dataAdapter;

    public static DatabaseHelper databaseHelper1;
    public static SQLiteDatabase db2;

    Intent intent;
    Button addPill;
    TextView calendar;
    LocalDate date;
    DatePickerDialog datePickerDialog;

    public static String userId;
    PillsManager pillsManager;

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    public ArrayList<LocalDate> days;
    public AlarmController alarmController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper1 = new DatabaseHelper(this);
        db2 = databaseHelper1.getReadableDatabase();
        addPill = findViewById(R.id.addButton);
        calendar= findViewById(R.id.monthYearTV);
        Button settings=findViewById(R.id.settings);
        TextView title = findViewById(R.id.medicine_re);
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        RelativeLayout inf = findViewById(R.id.inform);
        pillList = findViewById(R.id.list);

        intent = new Intent(this, AddingPill.class);
        Intent sett = new Intent(this, SettingsActivity.class);
        Intent informIntent = new Intent(this, InformActivity.class);

        settings.setOnClickListener(view -> startActivity(sett));
        addPill.setOnClickListener(view -> startActivity(intent));
        inf.setOnClickListener(view -> startActivity(informIntent));

        CalendarUtils.selectedDate = LocalDate.now();
        setWeekView();
        date = CalendarUtils.selectedDate;
        monthYearText.setOnClickListener(v -> {
            openDatePickerAfter();
        });

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

        databaseHelper = new DatabaseHelper(this);
        db = databaseHelper.getReadableDatabase();
        pillCursor = db.rawQuery("select " + DatabaseHelper.COLUMN_ID + ", " + DatabaseHelper.COLUMN_NAME  + ", " + DatabaseHelper.COLUMN_VALUETIME + ", " + DatabaseHelper.COLUMN_TIME1 + " from " + DatabaseHelper.TABLE, null);
        pillAdapter = new PillCursorAdapter(this, pillCursor);
        pillList.setAdapter(pillAdapter);

        databaseHelper = new DatabaseHelper(getApplicationContext());
        alarmController = new AlarmController(this);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 1);
        String time = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
//        alarmController.add_alarm_notify(new Pill("boyaroshnik", "Боярошник", 10, "шт", "1.02.2023", "1.09.2023", time));

        alarmController.refresh();

        SharedPreferences sharedPreference = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        System.out.println(sharedPreference.getString("userName", "userId don't set"));

      //  onCalendarItem();
    }

    @Override
    public void onResume() {
        super.onResume();
        pillsManager = new PillsManager(this);
        pillsManager.setListener((pills) -> {
            System.out.println("PILLS CHANGED");
            db = databaseHelper.getReadableDatabase();
            pillCursor = db.rawQuery("SELECT " + DatabaseHelper.COLUMN_ID + ", " + DatabaseHelper.COLUMN_NAME  + ", " + DatabaseHelper.COLUMN_VALUETIME + ", " + DatabaseHelper.COLUMN_TIME1 + " FROM " + DatabaseHelper.TABLE, null);
            pillAdapter.changeCursor(pillCursor);
            pillAdapter.notifyDataSetChanged();

            alarmController.refresh();
            return null;
        });
    }

    public void onCalendarItem(DatabaseHelper databaseHelper1, SQLiteDatabase db) {
        testCursor = db.rawQuery("select " + databaseHelper1.COLUMN_ID + ", " + databaseHelper1.COLUMN_DATE1 + ", " + databaseHelper1.COLUMN_DATE2 + " from " + databaseHelper1.TABLE, null);
        int length = testCursor.getCount();
        testCursor.moveToFirst();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println(selectedDate);
      //  for (int i = 0; i < length; i++) {
            String id = testCursor.getString(0);
            String data = testCursor.getString(1);
            String data2 = testCursor.getString(2);
            String[] str = data.split("\n.");
//            String data3 = str[2] + "-" + str[1] + "-" + str[0];
            //LocalDate localDate = LocalDate.parse(data3);
           //     System.out.println("str " + str[0]);



            testCursor.moveToNext();
       // }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        pillCursor.close();
    }

    private void initDatePicker(TextView calDate) {


        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            LocalDate date = LocalDate.of(year, month, day);
            onItemClick(1, date);
            monthYearText.setText(date.toString());
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
    public void readFromDb() {
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

    private void setWeekView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }


    public void previousWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusDays(1);
        setWeekView();
    }

    public void nextWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusDays(+1);
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
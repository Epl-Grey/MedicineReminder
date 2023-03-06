package com.example.medicineremindernew;

import static com.example.medicineremindernew.CalendarUtils.daysInWeekArray;
import static com.example.medicineremindernew.CalendarUtils.monthYearFromDate;

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

import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.medicineremindernew.firebase.PillsManager;
import com.example.medicineremindernew.firebase.UsersManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener{

    ListView pillList;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor pillCursor;
    Cursor dataCursor;
    Cursor testCursor;
    Cursor numberIdCursor;
    SimpleCursorAdapter pillAdapter;
    SimpleCursorAdapter dataAdapter;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        addPill = findViewById(R.id.addButton);
        calendar= findViewById(R.id.monthYearTV);
        Button settings=findViewById(R.id.settings);
        TextView title = findViewById(R.id.medicine_re);
        Intent sett = new Intent(this, SettingsActivity.class);
        settings.setOnClickListener(view -> startActivity(sett));
        intent = new Intent(this, AddingPill.class);
        addPill.setOnClickListener(view -> startActivity(intent));
        RelativeLayout inf=findViewById(R.id.inform);
        Intent informIntent = new Intent(this, InformActivity.class);
        inf.setOnClickListener(view -> startActivity(informIntent));
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        CalendarUtils.selectedDate = LocalDate.now();
        setWeekView();
        date = CalendarUtils.selectedDate;
        monthYearText.setOnClickListener(v -> {
            openDatePickerAfter();
        });

        pillList = findViewById(R.id.list);
        pillList.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getApplicationContext(),InformActivity.class);
            intent.putExtra("id", id);

            startActivity(intent);
        });

        calendar.setOnClickListener(v -> {
            openDatePickerAfter(calendar);
            readFromDb();
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

        SharedPreferences sharedPreference = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        System.out.println(sharedPreference.getString("userName", "userId don't set"));

        onCalendarItem();
    }

    @Override
    public void onResume() {
        super.onResume();
        pillsManager = new PillsManager(this);
        pillsManager.setListener((pills) -> {
            System.out.println("PILLS CHANGED");
            db = databaseHelper.getReadableDatabase();

            //получаем данные из бд в виде курсора
            pillCursor = db.rawQuery("select " + DatabaseHelper.COLUMN_ID + ", " + DatabaseHelper.COLUMN_NAME  + ", " + DatabaseHelper.COLUMN_VALUETIME + ", " + DatabaseHelper.COLUMN_TIME1 + " from " + DatabaseHelper.TABLE, null);
            // определяем, какие столбцы из курсора будут выводиться в ListView
            String[] headers = new String[]{DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_VALUETIME, DatabaseHelper.COLUMN_TIME1};
            // создаем адаптер, передаем в него курсор
            pillAdapter = new SimpleCursorAdapter(this, R.layout.row_layout,
                    pillCursor, headers, new int[]{R.id.name, R.id.kl, R.id.time}, 0);
            pillList.setAdapter(pillAdapter);
            return null;
        });
    }

    public void onCalendarItem() {
        DatabaseHelper databaseHelper1 = new DatabaseHelper(this);
        db = databaseHelper1.getReadableDatabase();
        testCursor = db.rawQuery("select " + databaseHelper1.COLUMN_ID + ", " + databaseHelper1.COLUMN_DATE1  + ", " + databaseHelper1.COLUMN_DATE2 + " from " + databaseHelper1.TABLE, null);
        int length = testCursor.getCount();
        testCursor.moveToFirst();
        for (int i = 0; i < length; i++) {
            String id = testCursor.getString(0);
            String data = testCursor.getString(1);
            String data2 = testCursor.getString(2);

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
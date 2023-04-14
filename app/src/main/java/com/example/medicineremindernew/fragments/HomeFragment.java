package com.example.medicineremindernew.fragments;

import static com.example.medicineremindernew.calendar.CalendarUtils.daysInWeekArray;
import static com.example.medicineremindernew.calendar.CalendarUtils.monthYearFromDate;
import static com.example.medicineremindernew.calendar.CalendarUtils.numberOfDays;
import static com.example.medicineremindernew.calendar.CalendarUtils.selectedDate;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.medicineremindernew.AddingPill;

import com.example.medicineremindernew.calendar.CalendarAdapter;
import com.example.medicineremindernew.calendar.CalendarUtils;
import com.example.medicineremindernew.DatabaseHelper;
import com.example.medicineremindernew.InformActivity;
import com.example.medicineremindernew.PillSimpleAdapter;
import com.example.medicineremindernew.PillsView;
import com.example.medicineremindernew.R;
import com.example.medicineremindernew.alarm.AlarmController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;


public class HomeFragment extends Fragment {

    ListView pillList;
    DatabaseHelper databaseHelper;
    public SQLiteDatabase db;
    Cursor pillCursor;
    Cursor dataCursor;
    Cursor testCursor;
    PillSimpleAdapter pillAdapter;
    public static DatabaseHelper databaseHelper1;
    public static SQLiteDatabase db2;

    LinearLayoutManager linearLayoutManager;
    Intent intent;

    Button addPill;
    Button minusMonthBtn;
    Button plusMonthBtn;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewP = inflater.inflate(R.layout.fragment_home, container, false);

        databaseHelper1 = new DatabaseHelper(getActivity());
        db2 = databaseHelper1.getReadableDatabase();
        addPill = viewP.findViewById(R.id.addButton);
        minusMonthBtn = viewP.findViewById(R.id.minMonthBtn);
        plusMonthBtn = viewP.findViewById(R.id.plusMonthBtn);
        calendar= viewP.findViewById(R.id.monthYearTV);

        calendarRecyclerView = viewP.findViewById(R.id.calendarRecyclerView);
        monthYearText = viewP.findViewById(R.id.monthYearTV);
        RelativeLayout inf = viewP.findViewById(R.id.inform);
        pillList = viewP.findViewById(R.id.list);
        databaseHelper = new DatabaseHelper(getActivity());
        db = databaseHelper.getReadableDatabase();

        intent = new Intent(getContext(), AddingPill.class);
        Intent sett = new Intent(getContext(), SettingsFragment.class);
        Intent informIntent = new Intent(getContext(), InformActivity.class);

        addPill.setOnClickListener(view -> startActivity(intent));
        inf.setOnClickListener(view -> startActivity(informIntent));

        CalendarUtils.selectedDate = LocalDate.now();
        setWeekView();
        date = CalendarUtils.selectedDate;
        monthYearText.setOnClickListener(v -> openDatePickerAfter());

        minusMonthBtn.setOnClickListener(view -> previousWeekAction());
        plusMonthBtn.setOnClickListener(view -> nextWeekAction());

        pillList.setOnItemClickListener((parent, view, position, id) -> {
            TextView idTextView = view.findViewById(R.id.id_storage); // Yeah, TextView for storing data :)
            Intent intent = new Intent(getContext(), InformActivity.class);
            intent.putExtra("id", idTextView.getText().toString());
            startActivity(intent);
        });

        calendar.setOnClickListener(v -> {
            openDatePickerAfter(calendar);
            readFromDb();
        });

        databaseHelper = new DatabaseHelper(getActivity());
        db = databaseHelper.getReadableDatabase();
//        pillCursor = db.rawQuery("select " + DatabaseHelper.COLUMN_ID + ", " + DatabaseHelper.COLUMN_NAME  + ", " + DatabaseHelper.COLUMN_VALUETIME + ", " + DatabaseHelper.COLUMN_TIME1 + " from " + DatabaseHelper.TABLE, null);
//        pillAdapter = new PillSimpleAdapter(getActivity(), pillCursor);
//        pillList.setAdapter(pillAdapter);

        databaseHelper = new DatabaseHelper(getActivity());
        alarmController = new AlarmController(getContext());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 1);

        alarmController.refresh();
        onCalendarItem(db);

        return viewP;
    }

    public void onCalendarItem( SQLiteDatabase db) {
        testCursor = db.rawQuery("select " + DatabaseHelper.COLUMN_ID + ", " + DatabaseHelper.COLUMN_DATE1 + ", " + DatabaseHelper.COLUMN_DATE2 + ", "+ DatabaseHelper.COLUMN_NAME + ", " + DatabaseHelper.COLUMN_VALUETIME + ", " + DatabaseHelper.COLUMN_TIME1 + " from " + DatabaseHelper.TABLE, null);
        testCursor.moveToFirst();
        int length = testCursor.getCount();
        System.out.println("selectedDate " + selectedDate);
        ArrayList<PillsView> arrayList = new ArrayList<PillsView>();
        pillAdapter = new PillSimpleAdapter(getContext(), arrayList);
        pillList.setAdapter(pillAdapter);
        for (int i = 1; i <= length; i++) {

            String id = testCursor.getString(0);
            String data = testCursor.getString(1);
            String data2 = testCursor.getString(2);

            String[] str = data.split("\\.");
            String[] str2 = data2.split("\\.");

            String dataSplit = str[2] + "-" + str[1] + "-" + str[0];
            LocalDate localDate = LocalDate.parse(dataSplit);
            System.out.println("str " + localDate);

            String dateSplit2 = str2[2] + "-" + str2[1] + "-" + str2[0];
            LocalDate localDate2 = LocalDate.parse(dateSplit2);
            System.out.println("str2 " + localDate2);

            if (selectedDate.isAfter(localDate) && selectedDate.isBefore(localDate2) || selectedDate.equals(localDate) || selectedDate.equals(localDate2)) {

                pillCursor = db.rawQuery("SELECT " + DatabaseHelper.COLUMN_ID + ", " + DatabaseHelper.COLUMN_NAME  + ", " + DatabaseHelper.COLUMN_VALUETIME + ", " + DatabaseHelper.COLUMN_TIME1 + " FROM " + DatabaseHelper.TABLE + " WHERE " + DatabaseHelper.COLUMN_ID + " =   \""+ id + "\"", null);
                pillCursor.moveToFirst();
                arrayList.add(new PillsView(pillCursor.getString(1), pillCursor.getString(2), pillCursor.getString(3), pillCursor.getString(0)));

                pillAdapter = new PillSimpleAdapter(getContext(), arrayList);
                pillList.setAdapter(pillAdapter);

            }


            testCursor.moveToNext();

        }

    }
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        db.close();
//        pillCursor.close();
//    }
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

            do {


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
        CalendarAdapter calendarAdapter = new CalendarAdapter(this::onItemClick, days, numberWeek,  this);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        calendarRecyclerView.setLayoutManager(linearLayoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);

    }


    public void previousWeekAction()
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setWeekView();
    }

    public void nextWeekAction()
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setWeekView();
    }


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

        datePickerDialog = new DatePickerDialog(getContext(), style, dateSetListener, year, month, day);
    }


    public void openDatePickerAfter() {
        initDatePicker();
        datePickerDialog.show();
    }

}
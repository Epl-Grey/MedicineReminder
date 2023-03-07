package com.example.medicineremindernew;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicineremindernew.alarm.AlarmController;
import com.example.medicineremindernew.firebase.PillsManager;

import java.util.Calendar;
import java.util.Locale;

public class AddTime extends AppCompatActivity {

    TextView setTimes;
    TextView setTimes2;
    TextView setTimes3;
    TextView setTimes4;
    TextView setTimes5;
    TextView setTimes6;

    Button nextBtn;

    Calendar dateAndTime=Calendar.getInstance();

    Intent IntentSave;

    long userId;

    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    int hour, minute;


    String[] times = {"1 раз в день", "2 раза в день", "3 раза в день", "4 раза в день", "5 раз в день", "6 раз в день"};    String selectItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_time);

        setTimes = (TextView) findViewById(R.id.setTimes);
        setTimes2 = (TextView) findViewById(R.id.setTimes2);
        setTimes3 = (TextView) findViewById(R.id.setTimes3);
        setTimes4 = (TextView) findViewById(R.id.setTimes4);
        setTimes5 = (TextView) findViewById(R.id.setTimes5);
        setTimes6 = (TextView) findViewById(R.id.setTimes6);

        nextBtn = (Button) findViewById(R.id.nextBtn);
        userId = getIntent().getLongExtra("id", 0);

        IntentSave = getIntent();

        sqlHelper = new DatabaseHelper(this);
        db = sqlHelper.getWritableDatabase();

        selectItem = IntentSave.getStringExtra("Item");

        Toast toast = Toast.makeText(getApplicationContext(),
                selectItem, Toast.LENGTH_SHORT);
        toast.show();

        if (selectItem.equals(times[0])) {
            setTimes.setVisibility(View.VISIBLE);
            setTimes2.setVisibility(View.GONE);
            setTimes3.setVisibility(View.GONE);
            setTimes4.setVisibility(View.GONE);
            setTimes5.setVisibility(View.GONE);
            setTimes6.setVisibility(View.GONE);
        } else if (selectItem.equals(times[1])) {
            setTimes.setVisibility(View.VISIBLE);
            setTimes2.setVisibility(View.VISIBLE);
            setTimes3.setVisibility(View.GONE);
            setTimes4.setVisibility(View.GONE);
            setTimes5.setVisibility(View.GONE);
            setTimes6.setVisibility(View.GONE);
        } else if (selectItem.equals(times[2])) {
            setTimes.setVisibility(View.VISIBLE);
            setTimes2.setVisibility(View.VISIBLE);
            setTimes3.setVisibility(View.VISIBLE);
            setTimes4.setVisibility(View.GONE);
            setTimes5.setVisibility(View.GONE);
            setTimes6.setVisibility(View.GONE);
        } else if (selectItem.equals(times[3])) {
            setTimes.setVisibility(View.VISIBLE);
            setTimes2.setVisibility(View.VISIBLE);
            setTimes3.setVisibility(View.VISIBLE);
            setTimes4.setVisibility(View.VISIBLE);
            setTimes5.setVisibility(View.GONE);
            setTimes6.setVisibility(View.GONE);
        } else if (selectItem.equals(times[4])) {
            setTimes.setVisibility(View.VISIBLE);
            setTimes2.setVisibility(View.VISIBLE);
            setTimes3.setVisibility(View.VISIBLE);
            setTimes4.setVisibility(View.VISIBLE);
            setTimes5.setVisibility(View.VISIBLE);
            setTimes6.setVisibility(View.GONE);
        } else if (selectItem.equals(times[5])) {
            setTimes.setVisibility(View.VISIBLE);
            setTimes2.setVisibility(View.VISIBLE);
            setTimes3.setVisibility(View.VISIBLE);
            setTimes4.setVisibility(View.VISIBLE);
            setTimes5.setVisibility(View.VISIBLE);
            setTimes6.setVisibility(View.VISIBLE);

        }

        setTimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTimePicker(setTimes);
            }
        });

        setTimes2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTimePicker(setTimes2);
            }
        });

        setTimes3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTimePicker(setTimes3);
            }
        });

        setTimes4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTimePicker(setTimes4);
            }
        });

        setTimes5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTimePicker(setTimes5);
            }
        });

        setTimes6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTimePicker(setTimes6);
            }
        });

    }

    public void save(View view){
//        ContentValues bValues = new ContentValues();
//        bValues.put(DatabaseHelper.COLUMN_NAME, getIntent().getStringExtra("name"));
//        bValues.put(DatabaseHelper.COLUMN_VALUE, getIntent().getStringExtra("value"));
//        bValues.put(DatabaseHelper.COLUMN_DOSAGE, getIntent().getStringExtra("dos"));
//        bValues.put(DatabaseHelper.COLUMN_DATE1, getIntent().getStringExtra("data1"));
//        bValues.put(DatabaseHelper.COLUMN_DATE2, getIntent().getStringExtra("data2"));
//        bValues.put(DatabaseHelper.COLUMN_TIME1, setTimes.getText().toString());
//        bValues.put(DatabaseHelper.COLUMN_TIME2, setTimes2.getText().toString());
//        bValues.put(DatabaseHelper.COLUMN_TIME3, setTimes3.getText().toString());
//        bValues.put(DatabaseHelper.COLUMN_TIME4, setTimes4.getText().toString());
//        bValues.put(DatabaseHelper.COLUMN_TIME5, setTimes5.getText().toString());
//        bValues.put(DatabaseHelper.COLUMN_TIME6, setTimes6.getText().toString());
//        bValues.put(DatabaseHelper.COLUMN_VALUETIME, getIntent().getStringExtra("Item"));

        SharedPreferences sharedPreference = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String userId = sharedPreference.getString("userName", "userId don't set");

        PillsManager pillsManager = new PillsManager(this);
        pillsManager.saveData(
                userId,
                getIntent().getStringExtra("name"),
                getIntent().getStringExtra("value"),
                getIntent().getStringExtra("dos"),
                getIntent().getStringExtra("data1"),
                getIntent().getStringExtra("data2"),
                setTimes.getText().toString(),
                setTimes2.getText().toString(),
                setTimes3.getText().toString(),
                setTimes4.getText().toString(),
                setTimes5.getText().toString(),
                setTimes6.getText().toString(),
                getIntent().getStringExtra("Item")
        );

        AlarmController alarmController = new AlarmController(this);
        alarmController.refresh();

        getToMainRes();

    }

    // установка начальных даты и времени
    private void setInitialDateTime(TextView textView) {

        textView.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME));
    }

    // метод для выбора времени
    public void setTimePicker(TextView textView) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                textView.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            }
        };

        int style = AlertDialog.THEME_HOLO_LIGHT;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("До какого времени");
        timePickerDialog.show();

    }

    private void getToMainRes(){
        // закрываем подключение
        db.close();
        // переход к главной activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }


}
package com.example.medicineremindernew.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.medicineremindernew.DatabaseHelper;
import com.example.medicineremindernew.R;
import com.example.medicineremindernew.alarm.AlarmController;
import com.example.medicineremindernew.calendar.CalendarUtils;

import com.example.medicineremindernew.firebase.BolusManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;

public class ResultActivity extends AppCompatActivity {

    TextView glukozaText;
    TextView XEText;
    TextView eatText;
    TextView insulinText;
    TextView corectText;
    TextView resultText;
    TextView calcEatText;
    TextView calcCorectText;

    TextView timeText;
    Button btnCalculate;

    DatePickerDialog datePickerDialog;
    LocalDate date;
    int hour, minute;
    String date2;

    String aaaa;

    DatabaseHelper sqlHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        sqlHelper = new DatabaseHelper(this);
        db = sqlHelper.getWritableDatabase();

        glukozaText = findViewById(R.id.glukoza);
        XEText = findViewById(R.id.XE);
        eatText = findViewById(R.id.eat);
        insulinText = findViewById(R.id.insulin);
        corectText = findViewById(R.id.corect);
        resultText = findViewById(R.id.result);
        calcEatText = findViewById(R.id.calcEat);
        calcCorectText = findViewById(R.id.calcCorect);

        timeText = findViewById(R.id.time);
        btnCalculate = findViewById(R.id.btnResult);

        CalendarUtils.selectedDate = LocalDate.now();

        date = CalendarUtils.selectedDate;

        Intent main_intent = new Intent(this, MainActivity.class);

        String strGluk = getIntent().getStringExtra("gluk");
        String strYglev = getIntent().getStringExtra("yglev");
        String strFchi = getIntent().getStringExtra("fchi");
        String strYk = getIntent().getStringExtra("yk");
        String strActivation = getIntent().getStringExtra("activation");
        String strCelGluk = getIntent().getStringExtra("celGluk");
        getIntent().getIntArrayExtra("gluk");

        float correct = Integer.parseInt(strGluk) - Integer.parseInt(strCelGluk);
        correct = correct / Integer.parseInt(strFchi);
        float corEat = Integer.parseInt(strYglev) * Integer.parseInt(strYk);
        float corRes = correct + corEat - Integer.parseInt(strActivation);
        String strCorrect = String.valueOf(correct);
        String strCalcCorect = "(" + strGluk + "-" + strCelGluk + ")" + "/" + strFchi + "=" + strCorrect;
        String strEat = String.valueOf(corEat);
        String strCalcEat = strYglev + "*" + strYk + "=" + strEat;
        String strResult = String.valueOf(corRes);
        String strRes = corEat + "+" + correct + "-" + strActivation + "=" + corRes;
        glukozaText.setText(strGluk);
        resultText.setText(strResult + " ЕД");
        eatText.setText(strEat + " ЕД");
        insulinText.setText(strCorrect + " ЕД");
        calcEatText.setText(strCalcEat);
        calcCorectText.setText(strCalcCorect);

        corectText.setText(strActivation + " ЕД");

        CalendarUtils.selectedDate = LocalDate.now();

        date = CalendarUtils.selectedDate;
        String monthOfYear = CalendarUtils.selectedDate.getMonth().toString();

        switch (monthOfYear) {
            case "JANUARY":
                monthOfYear = "января";
                break;
            case "FEBRUARY":
                monthOfYear = "февраля";
                break;
            case "MARCH":
                monthOfYear = "марта";
                break;
            case "APRIL":
                monthOfYear = "апреля";
                break;
            case "MAY":
                monthOfYear = "мая";
                break;
            case "JUNE":
                monthOfYear = "июня";
                break;
            case "JULY":
                monthOfYear = "июля";
                break;
            case "AUGUST":
                monthOfYear = "августа";
                break;
            case "SEPTEMBER":
                monthOfYear = "сентября";
                break;
            case "OCTOBER":
                monthOfYear = "октября";
                break;
            case "NOVEMBER":
                monthOfYear = "ноября";
                break;
            case "DECEMBER":
                monthOfYear = "декабря";
                break;
        }

        String dayOfWeek = date.getDayOfWeek().toString();

        switch (dayOfWeek) {
            case "WEDNESDAY":
                dayOfWeek = "ср";
                break;
            case "MONDAY":
                dayOfWeek = "пн";
                break;
            case "TUESDAY":
                dayOfWeek = "вт";
                break;
            case "THURSDAY":
                dayOfWeek = "чт";
                break;
            case "FRIDAY":
                dayOfWeek = "пт";
                break;
            case "SATURDAY":
                dayOfWeek = "сб";
                break;
            case "SUNDAY":
                dayOfWeek = "вс";
                break;
        }
        DateFormat df = new SimpleDateFormat("HH:mm");
        String date = df.format(Calendar.getInstance().getTime());
        String dateBolus = dayOfWeek + " " + CalendarUtils.selectedDate.getDayOfMonth() + " " + monthOfYear + " " + CalendarUtils.selectedDate.getYear() + " " +  date;
        timeText.setText(dateBolus);

        btnCalculate.setOnClickListener(view -> saveResult());

    }

    public void saveResult(){


        SharedPreferences sharedPreference = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String userId = sharedPreference.getString("userName", "userId don't set");

        BolusManager bolusManager = new BolusManager(this);
        bolusManager.saveData(
                userId,
                glukozaText.getText().toString(),
                XEText.getText().toString(),
                eatText.getText().toString(),
                insulinText.getText().toString(),
                corectText.getText().toString(),
                resultText.getText().toString(),
                calcEatText.getText().toString(),
                calcCorectText.getText().toString(),
                timeText.getText().toString()
        );
        getToMainRes();

    }

    private void getToMainRes(){
        // закрываем подключение
        db.close();
        // переход к главной activity
        Intent intent = new Intent(this, AddGoodActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    String day2;
    String month2;
    private void initDatePicker(TextView textView) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                if (day == 1 || day == 2 || day == 3 || day == 4 || day == 5 || day == 6 || day == 7 || day == 8 || day == 9){
                    day2 = "0" + day;
                } else {
                    day2 = String.valueOf(day);
                }
                if (month == 1 || month == 2 || month == 3 || month == 4 || month == 5 || month == 6 || month == 7 || month == 8 || month == 9){
                    month2 = "0" + month;
                } else {
                    month2 = String.valueOf(month);
                }
                date2 = day2 + "." + month2 + "." + year;
                setTimePicker(textView);
               // textView.setText(date2);

            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    public void setTimePicker(TextView textView) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                textView.setText(date2 + " " +String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            }
        };

        int style = AlertDialog.THEME_HOLO_LIGHT;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("До какого времени");
        timePickerDialog.show();

    }

    public void openDatePicker(View view) {
        initDatePicker(timeText);
        datePickerDialog.show();
    }

}
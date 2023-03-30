package com.example.medicineremindernew;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.medicineremindernew.calendar.CalendarUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ResultActivity extends AppCompatActivity {

    TextView glukoza;
    TextView XE;
    TextView eat;
    TextView insulin;
    TextView corect;
    TextView result;
    TextView calcEat;
    TextView calcCorect;
    TextView calcResult;
    TextView setDateBolus;
    Button btnCalculate;

    DatePickerDialog datePickerDialog;
    LocalDate date;
    int hour, minute;
    String date2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        glukoza = findViewById(R.id.glukoza);
        XE = findViewById(R.id.XE);
        eat = findViewById(R.id.eat);
        insulin = findViewById(R.id.insulin);
        corect = findViewById(R.id.corect);
        result = findViewById(R.id.result);
        calcEat = findViewById(R.id.calcEat);
        calcCorect = findViewById(R.id.calcCorect);
        calcResult = findViewById(R.id.calcResult);
        setDateBolus = findViewById(R.id.setDateBolus);
        btnCalculate = findViewById(R.id.btnCalculate);

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

        int correct = Integer.parseInt(strGluk) - Integer.parseInt(strCelGluk);
        correct = correct / Integer.parseInt(strFchi);
        int corEat = Integer.parseInt(strYglev) * Integer.parseInt(strYk);
        int corRes = correct + corEat - Integer.parseInt(strActivation);
        String strCorrect = String.valueOf(correct);
        String strCalcCorect = "(" + strGluk + "-" + strCelGluk + ")" + "/" + strFchi + "=" + strCorrect;
        String strEat = String.valueOf(corEat);
        String strCalcEat = strYglev + "*" + strYk + "=" + strEat;
        String strResult = String.valueOf(corRes);
        String strRes = corEat + "+" + correct + "-" + strActivation + "=" + corRes;
        glukoza.setText(strGluk);
        result.setText(strResult);
        eat.setText(strEat + " Ед");
        insulin.setText(strCorrect + " Ед");
        calcEat.setText(strCalcEat);
        calcCorect.setText(strCalcCorect);
        calcResult.setText(strRes);
        corect.setText(strActivation + " Ед");

        CalendarUtils.selectedDate = LocalDate.now();

        date = CalendarUtils.selectedDate;
        String monthOfYear = CalendarUtils.selectedDate.getMonth().toString();

        switch (monthOfYear) {
            case "MARCH":
                monthOfYear = "марта";
                break;
            case "MONDAY":
                monthOfYear = "пн";
                break;
            case "TUESDAY":
                monthOfYear = "вт";
                break;
            case "THURSDAY":
                monthOfYear = "чт";
                break;
            case "FRIDAY":
                monthOfYear = "пт";
                break;
            case "SATURDAY":
                monthOfYear = "сб";
                break;
            case "SUNDAY":
                monthOfYear = "вс";
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
        String dateBolus = dayOfWeek + " " + CalendarUtils.selectedDate.getDayOfMonth() + " " + CalendarUtils.selectedDate.getMonth().toString() + " " + CalendarUtils.selectedDate.getYear() + " " +  date;
        setDateBolus.setText(dateBolus);

        btnCalculate.setOnClickListener(view -> startActivity(main_intent));

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
        initDatePicker(setDateBolus);
        datePickerDialog.show();
    }

}
package com.example.medicineremindernew;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class AddingPill extends AppCompatActivity {

    //Переменные
    String selectItemOr;

    String[] countries = {"ГРАММОВ", "МИЛИЛИТРОВ", "СТОЛОВЫХ ЛОЖЕК", "ТАБЛЕТОК"};

    TextView textAfter;
    TextView textBefore;

    EditText nameEdit;
    EditText valueEdit;

    String[] times = {"ОДИН РАЗ В ДЕНЬ", "ДВА РАЗА В ДЕНЬ", "ТРИ РАЗА В ДЕНЬ", "ЧЕТЫРЕ РАЗА В ДЕНЬ", "ПЯТЬ РАЗ В ДЕНЬ", "ШЕСТЬ РАЗ В ДЕНЬ"};
    DatePickerDialog datePickerDialog;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_pill);
        // присваение
        Spinner spinner_num =  findViewById(R.id.Spinner);
        Spinner spinner_times =  findViewById(R.id.spinnerTimes);

        textAfter =  findViewById(R.id.textAfter);
        textBefore =  findViewById(R.id.textBefore);

        nameEdit = findViewById(R.id.NameEdit);
        valueEdit =  findViewById(R.id.NumberEdit);

        Button btnChoose =  findViewById(R.id.btnChoose);

        //спинеры spinner_num, spinner_times
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, countries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_num.setAdapter(adapter);

        ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, times);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_times.setAdapter(adapter2);
        // вывод текста для выбара времени

        Intent intent = new Intent(this, AddTime.class);


        textBefore.setOnClickListener(view -> openDatePickerBefore(textBefore));

        textAfter.setOnClickListener(view -> openDatePickerAfter(textAfter));



        btnChoose.setOnClickListener(view -> {
            selectItemOr = spinner_times.getSelectedItem().toString();
            String intent_dos = spinner_num.getSelectedItem().toString();

            intent.putExtra("Item", selectItemOr);
            intent.putExtra("name", nameEdit.getText().toString());
            intent.putExtra("value", valueEdit.getText().toString());
            intent.putExtra("dos", intent_dos);
            intent.putExtra("data1", textBefore.getText().toString());
            intent.putExtra("data2", textAfter.getText().toString());

            Toast toast = Toast.makeText(getApplicationContext(),
                    intent_dos, Toast.LENGTH_SHORT);
            toast.show();

            startActivity(intent);
        });
    }

    private void initDatePicker(TextView textView) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = month + "." + day + "." + year;
                textView.setText(date);
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


    public void openDatePickerAfter(View view) {
        initDatePicker(textAfter);
        datePickerDialog.show();
    }

    public void openDatePickerBefore(View view) {
        initDatePicker(textBefore);
        datePickerDialog.show();
    }

}
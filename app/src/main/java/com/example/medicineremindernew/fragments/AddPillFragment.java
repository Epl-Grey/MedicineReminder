package com.example.medicineremindernew.fragments;



import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.medicineremindernew.AddTime;
import com.example.medicineremindernew.DatabaseHelper;
import com.example.medicineremindernew.R;
import com.example.medicineremindernew.activities.MainActivity;

import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;


public class AddPillFragment extends Fragment {
    String selectItemOr;

    String[] countries = {"грамм", "мл", "стол. ложек", "табл"};

    TextView textAfter;
    TextView textBefore;

    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;

    EditText nameEdit;
    EditText valueEdit;
    long userId;
    String[] times = {"1 раз в день", "2 раза в день", "3 раза в день", "4 раза в день", "5 раз в день", "6 раз в день"};
    DatePickerDialog datePickerDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewP = inflater.inflate(R.layout.fragment_add_pill, container, false);

// присваение
        Spinner spinner_num =  viewP.findViewById(R.id.Spinner);
        Spinner spinner_times =  viewP.findViewById(R.id.spinnerTimes);

        textAfter =  viewP.findViewById(R.id.textAfter);
        textBefore =  viewP.findViewById(R.id.textBefore);

        nameEdit = viewP.findViewById(R.id.NameEdit);
        valueEdit =  viewP.findViewById(R.id.NumberEdit);

        Button btnChoose =  viewP.findViewById(R.id.btnChoose);


        Intent main_activity_intent = new Intent(getContext(), MainActivity.class);


//        sqlHelper = new DatabaseHelper(getContext());
//        db = sqlHelper.getWritableDatabase();
//
//        Bundle extras = Intent.getIntent().getExtras();
//        if (extras != null) {
//            userId = extras.getLong("id");
//        }
//        // если 0, то добавление
//        if (userId > 0) {
//            // получаем элемент по id из бд
//            userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE + " where " +
//                    DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(userId)});
//            userCursor.moveToFirst();
//            nameEdit.setText(userCursor.getString(1));
//            valueEdit.setText(String.valueOf(userCursor.getInt(2)));
//            textBefore.setText(userCursor.getString(4));
//            textAfter.setText(userCursor.getString(5));
//            userCursor.close();
//        }


//            nameEdit.setText(userCursor.getString(1));
//            valueEdit.setText(String.valueOf(userCursor.getInt(2)));
//            textBefore.setText(userCursor.getString(5));
//            textAfter.setText(userCursor.getString(6));
//            userCursor.close();






        //спинеры spinner_num, spinner_times
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, countries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_num.setAdapter(adapter);

        ArrayAdapter adapter2 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, times);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_times.setAdapter(adapter2);
        // вывод текста для выбара времени

        Intent intent = new Intent(getContext(), AddTime.class);


        textBefore.setOnClickListener(view -> openDatePickerBefore(textBefore));

        textAfter.setOnClickListener(view -> openDatePickerAfter(textAfter));



        btnChoose.setOnClickListener(view -> {
            if(nameEdit.getText().toString().isEmpty()){
                nameEdit.setError("Enter name");
                return;
            }
            if(valueEdit.getText().toString().isEmpty() || !StringUtils.isNumeric(valueEdit.getText().toString())){
                valueEdit.setError("Enter value");
                return;
            }

            selectItemOr = spinner_times.getSelectedItem().toString();
            String intent_dos = spinner_num.getSelectedItem().toString();

            intent.putExtra("id", userId);
            intent.putExtra("Item", selectItemOr);
            intent.putExtra("name", nameEdit.getText().toString().trim());
            intent.putExtra("value", valueEdit.getText().toString().trim());
            intent.putExtra("dos", intent_dos);
            intent.putExtra("data1", textBefore.getText().toString().trim());
            intent.putExtra("data2", textAfter.getText().toString().trim());

            startActivity(intent);
        });
        return viewP;
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
                String date = day2 + "." + month2 + "." + year;
                textView.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;

        datePickerDialog = new DatePickerDialog(getContext(), style, dateSetListener, year, month, day);
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

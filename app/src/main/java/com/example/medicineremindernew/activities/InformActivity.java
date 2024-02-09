package com.example.medicineremindernew.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import com.example.medicineremindernew.DatabaseHelper;
import com.example.medicineremindernew.R;
import com.example.medicineremindernew.fragments.HomeFragment;
import com.example.medicineremindernew.models.Pill;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class InformActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inform);
        ImageButton back=findViewById(R.id.back);
        Intent bak=new Intent(this, HomeFragment.class);

        TextView name = findViewById(R.id.name);
        TextView dose = findViewById(R.id.dose);
        TextView kl = findViewById(R.id.kl);
        TextView allt = findViewById(R.id.allt);
        TextView weeks = findViewById(R.id.weeks);
        TextView numbes = findViewById(R.id.numbes);
        TextView numbernext = findViewById(R.id.numbernext);
        Button redactor = findViewById(R.id.redactor);

        //MainActivity mainActivity = new MainActivity();

        Pill pill = (Pill) getIntent().getSerializableExtra("pill");
        if(pill == null) return;

        SimpleDateFormat formatter = new SimpleDateFormat("dd.mm.yyyy");
        int days = 0;
        Date date2 = null;
        Date date = null;
        try {
            date = formatter.parse(pill.getDate_from());
            date2 = formatter.parse(pill.getDate_to());
            System.out.println(date);
            System.out.println(date2);

            long milliseconds = date2.getTime() - date.getTime();
            days = (int) (milliseconds / (24 * 60 * 60 * 1000) + 1);
            System.out.println("Разница между датами в днях: " + days);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        int daysValue = days * pill.getTimes().size();

        LocalDate date31 = LocalDate.now();
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String str = date31.format(formatter2);
        System.out.println(str);

        int daysValue2 = 0;
        try {
            Date date3 = formatter.parse(str);
            System.out.println(date3);

            long milliseconds = date2.getTime() - date3.getTime();

            int days2 = (int) (milliseconds / (24 * 60 * 60 * 1000));
            System.out.println("Разница между датами в днях2: " + days2);

            daysValue2 = pill.getDosage_value() * pill.getTimes().size();

        } catch (ParseException e) {
            e.printStackTrace();
        }


        name.setText(pill.getName());
        dose.setText(pill.getDosage_value() + " " + pill.getDosage_unit());
        kl.setText(pill.getTimes().size() + " приёмов в сутки");
        numbernext.setText(daysValue2 + " " + pill.getDosage_unit());
        numbes.setText(daysValue + " " + pill.getDosage_unit());
        allt.setText(StringUtils.join(pill.getTimes(), " "));
        weeks.setText(pill.getDate_from() + "-" + pill.getDate_to());


        Intent mainIntent = new Intent(this, MainActivity.class);
        back.setOnClickListener(view -> startActivity(mainIntent));


        Intent addPillIntent = new Intent(this, AddPillActivity.class);
        redactor.setOnClickListener(view -> {
            addPillIntent.putExtra("pill", pill);
            startActivity(addPillIntent);
        });

    }
}
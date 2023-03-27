package com.example.medicineremindernew;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import com.example.medicineremindernew.fragments.HomeFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class InformActivity extends AppCompatActivity {

    String[] times = {"1 раз в день", "2 раза в день", "3 раза в день", "4 раза в день", "5 раз в день", "6 раз в день"};

    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    String userId=null;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inform);
        ImageButton back=findViewById(R.id.back);
        Intent bak=new Intent(this, HomeFragment.class);

        TextView name = findViewById(R.id.name);
        TextView dose = findViewById(R.id.dose);
        TextView time = findViewById(R.id.time);
        TextView kl = findViewById(R.id.kl);
        TextView allt = findViewById(R.id.allt);
        TextView weeks = findViewById(R.id.weeks);
        TextView numbes = findViewById(R.id.numbes);
        TextView numbernext = findViewById(R.id.numbernext);
        Button redactor = findViewById(R.id.redactor);

        sqlHelper = new DatabaseHelper(this);
        db = sqlHelper.getWritableDatabase();
        //MainActivity mainActivity = new MainActivity();

        userId = getIntent().getStringExtra("id");
        System.out.println("Inform: userId: " + userId);

        userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE + " where " +
                DatabaseHelper.COLUMN_ID + "=?", new String[]{userId});

//        userCursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE, new String[]{});

        userCursor.moveToFirst();
        String date1 = userCursor.getString(4);
        String[] date11 = date1.split("\\.");

        SimpleDateFormat formatter = new SimpleDateFormat("dd.mm.yyyy");
        String dateFirst = userCursor.getString(4);
        String dateSecond = userCursor.getString(5);
        System.out.println(dateFirst + ", " + dateSecond);
        int days = 0;
        Date date2 = null;
        Date date = null;
        try {

            date = formatter.parse(dateFirst);
            date2 = formatter.parse(dateSecond);
            System.out.println(date);
            System.out.println(date2);

            long milliseconds = date2.getTime() - date.getTime();
            days = (int) (milliseconds / (24 * 60 * 60 * 1000) + 1);
            System.out.println("Разница между датами в днях: " + days);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        String db12 = userCursor.getString(12);
        int db2 = userCursor.getInt(2);
        System.out.println(db2);
        System.out.println(db12);
        int daysValue = 0;
        if (db12.equals(times[0])) {
            daysValue = (1 * db2) * days;
            System.out.println(daysValue + " таблеток");
        } else if (db12.equals(times[1])) {
            daysValue = (2 * db2) * days;
            System.out.println(daysValue + " таблеток");
        } else if (db12.equals(times[2])) {
            daysValue = (3 * db2) * days;
            System.out.println(daysValue + " таблеток");
        } else if (db12.equals(times[3])) {
            daysValue = (4 * db2) * days;
            System.out.println(daysValue + " таблеток");
        } else if (db12.equals(times[4])) {
            daysValue = (5 * db2) * days;
            System.out.println(daysValue + " таблеток");
        } else if (db12.equals(times[5])) {
            daysValue = (6 * db2) * days;
            System.out.println(daysValue + " таблеток");
        }


        LocalDate date31 = LocalDate.now();
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String str = date31.format(formatter2);
        System.out.println(str);
        Date date3;
        int days2 = 0;
        int daysValue2 = 0;
        try {

            date3 = formatter.parse(str);
            System.out.println(date3);

            long milliseconds = date2.getTime() - date3.getTime();

            days2 = (int) (milliseconds / (24 * 60 * 60 * 1000));
            System.out.println("Разница между датами в днях2: " + days2);

            if (db12.equals(times[0])) {
                daysValue2 = (1 * db2) * days2;
                System.out.println(daysValue2 + " таблеток осталось");
            } else if (db12.equals(times[1])) {
                daysValue2 = (2 * db2) * days2;
                System.out.println(daysValue2 + " таблеток осталось");
            } else if (db12.equals(times[2])) {
                daysValue2 = (3 * db2) * days2;
                System.out.println(daysValue2 + " таблеток осталось");
            } else if (db12.equals(times[3])) {
                daysValue2 = (4 * db2) * days2;
                System.out.println(daysValue2 + " таблеток осталось");
            } else if (db12.equals(times[4])) {
                daysValue2 = (5 * db2) * days2;
                System.out.println(daysValue2 + " таблеток осталось");
            } else if (db12.equals(times[5])) {
                daysValue2 = (6 * db2) * days2;
                System.out.println(daysValue2 + " таблеток осталось");
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }


        name.setText(userCursor.getString(1));
        dose.setText(userCursor.getString(2) + " " + userCursor.getString(3));
        time.setText(userCursor.getString(6));
        kl.setText(userCursor.getString(12));
        numbernext.setText(daysValue2 + " " + userCursor.getString(3));
        numbes.setText(daysValue + " " + userCursor.getString(3));
        allt.setText(userCursor.getString(6) + " " + userCursor.getString(7) + " " +
                userCursor.getString(8) + " " +userCursor.getString(9) + " " +
                userCursor.getString(10) + " " + userCursor.getString(11));
        weeks.setText(userCursor.getString(4) + "-" + userCursor.getString(5));
        userCursor.close();




        back.setOnClickListener(view -> startActivity(bak));



        Intent intent = new Intent(this, AddingPill.class);
        redactor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("id", userId);
                startActivity(intent);
            }
        });

    }
}
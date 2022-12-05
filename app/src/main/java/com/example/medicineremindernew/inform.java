package com.example.medicineremindernew;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class inform extends AppCompatActivity {

    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    long userId=0;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inform);
        ImageButton back=findViewById(R.id.back);
        Intent bak=new Intent(this,MainActivity.class);

        TextView name = findViewById(R.id.name);
        TextView dose = findViewById(R.id.dose);
        TextView time = findViewById(R.id.time);
        TextView kl = findViewById(R.id.kl);
        TextView allt = findViewById(R.id.allt);
        TextView weeks = findViewById(R.id.weeks);
        TextView numbes = findViewById(R.id.numbes);
        TextView numbernext = findViewById(R.id.numbernext);

        sqlHelper = new DatabaseHelper(this);
        db = sqlHelper.getWritableDatabase();
        MainActivity mainActivity = new MainActivity();
        userId = mainActivity.userId;
        System.out.println(userId);



        userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE + " where " +
                DatabaseHelper.COLUMN_ID + "=?", new String[]{Long.toString(userId)});
        userCursor.moveToFirst();
        String date1 = userCursor.getString(4);
        String[] date11 = date1.split("\\.");

        Calendar calendar_date1 = Calendar.getInstance();
        calendar_date1.set(Calendar.HOUR_OF_DAY, Integer.parseInt(date11[0]));
        calendar_date1.set(Calendar.MONTH, Integer.parseInt(date11[1]));
        calendar_date1.set(Calendar.YEAR, Integer.parseInt(date11[2]));

//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
//        System.out.println(simpleDateFormat.format(calendar_date1.getTime()));
//        LocalDate newDate = LocalDate.parse("05-05-2018");


        name.setText(userCursor.getString(1));
        dose.setText(userCursor.getString(2) + " " + userCursor.getString(3));
        time.setText(userCursor.getString(6));
        kl.setText(userCursor.getString(12));
        allt.setText(userCursor.getString(6) + " " + userCursor.getString(7) + " " +
                userCursor.getString(8) + " " +userCursor.getString(9) + " " +
                userCursor.getString(10) + " " + userCursor.getString(11));
        weeks.setText(userCursor.getString(4) + "-" + userCursor.getString(5));
        userCursor.close();



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(bak);
            }
        });
        ImageButton settings=findViewById(R.id.settings);
        Intent sett = new Intent(this, SettingsActivity.class);
        settings.setOnClickListener(view -> startActivity(sett));
    }
}
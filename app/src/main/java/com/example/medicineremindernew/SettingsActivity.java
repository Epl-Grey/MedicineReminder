package com.example.medicineremindernew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ThemedSpinnerAdapter;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Button addpill = findViewById(R.id.addButton);
        Intent intent = new Intent(this, AddingPill.class);
        addpill.setOnClickListener(view -> startActivity(intent));
        ImageButton back = findViewById(R.id.back);
        Intent bak = new Intent(this, MainActivity.class);
        back.setOnClickListener(view -> startActivity(bak));


    }


}
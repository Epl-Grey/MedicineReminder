package com.example.medicineremindernew.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.medicineremindernew.R;

public class StartOnBoardingScreenActivity extends AppCompatActivity {
    Button btnNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_on_boarding_screen);
        btnNext = findViewById(R.id.buttonNext);
        Intent intent = new Intent(this, OnBoardingScreenActivity.class);
        btnNext.setOnClickListener(v -> {
              startActivity(intent);
        });
    }
}
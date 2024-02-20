package com.example.medicineremindernew.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.medicineremindernew.R;
import com.example.medicineremindernew.alarm.AlarmController;
import com.example.medicineremindernew.fragments.AddPillFragment;
import com.example.medicineremindernew.fragments.CalculatorBolusa;
import com.example.medicineremindernew.fragments.HomeFragment;
import com.example.medicineremindernew.fragments.SettingsFragment;
import com.example.medicineremindernew.services.PillsDataService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import dagger.internal.InjectedFieldSignature;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity
        implements BottomNavigationView
        .OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView
                = findViewById(R.id.bottomNavigationView);

        bottomNavigationView
                .setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);
    }
    HomeFragment homeFragment = new HomeFragment();
    AddPillFragment addPillFragment = new AddPillFragment();
    CalculatorBolusa calculatorBolusa = new CalculatorBolusa();
    SettingsFragment settingsFragment = new SettingsFragment();

    @Override
    public boolean
    onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, homeFragment)
                        .commit();
                return true;

            case R.id.addPill:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, addPillFragment)
                        .commit();
                return true;

            case R.id.calculatorBolusa:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, calculatorBolusa)
                        .commit();
                return true;

            case R.id.settings:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, settingsFragment)
                        .commit();
                return true;
        }
        return false;
    }
}
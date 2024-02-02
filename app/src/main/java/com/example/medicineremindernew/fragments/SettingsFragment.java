package com.example.medicineremindernew.fragments;

import static android.app.Activity.RESULT_OK;

import static androidx.core.app.ActivityCompat.recreate;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.medicineremindernew.R;
import com.example.medicineremindernew.SaveSound;
import com.example.medicineremindernew.SaveState;

public class SettingsFragment extends Fragment {
    private static final int REQUEST_CODE = 1;
    Button voiceMelodyBtn;
    Button themeBtn;
    private SaveSound saveSound;
    private boolean isDarkTheme = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View viewP = inflater.inflate(R.layout.fragment_settings, container, false);
        saveSound = new SaveSound(getContext(), "sound");
        themeBtn = viewP.findViewById(R.id.ThemeBtn);
        voiceMelodyBtn = viewP.findViewById(R.id.voiceMelodyBtn);
        voiceMelodyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilePicker();
            }
        });

        themeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleTheme();
            }
        });

        return viewP;
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/mpeg"); // Указывает mp3 тип файлов
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data != null && data.getData() != null) {
                Uri selectedFileUri = data.getData();
                System.out.println("uri " + selectedFileUri);
                saveSound.setState(selectedFileUri.toString());
            }
        }
    }



    private void toggleTheme() {
        int currentNightMode = getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
        if (currentNightMode == android.content.res.Configuration.UI_MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        getActivity().recreate();
    }




}
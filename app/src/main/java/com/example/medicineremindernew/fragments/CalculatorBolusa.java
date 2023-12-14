package com.example.medicineremindernew.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.medicineremindernew.R;
import com.example.medicineremindernew.activities.ResultActivity;


public class CalculatorBolusa extends Fragment {

    EditText editGluk;
    EditText editYglev;
    EditText editFCHI;
    EditText editYK;
    EditText editActivationInsulin;
    EditText editCelGluk;
    Button btnCalculate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewP = inflater.inflate(R.layout.fragment_calculator_bolusa, container, false);
        editGluk = viewP.findViewById(R.id.editGluk);
        editYglev = viewP.findViewById(R.id.editYglev);
        editFCHI = viewP.findViewById(R.id.editFCHI);
        editYK = viewP.findViewById(R.id.editYK);
        editActivationInsulin = viewP.findViewById(R.id.editActivationInsulin);
        editCelGluk = viewP.findViewById(R.id.editCelGluk);
        btnCalculate = viewP.findViewById(R.id.btnCalculate);
        Intent calculateIntent = new Intent(getContext(), ResultActivity.class);

        calculateIntent.putExtra("gluk", editGluk.getText().toString());
        calculateIntent.putExtra("yglev", editYglev.getText().toString());
        calculateIntent.putExtra("fchi", editFCHI.getText().toString());
        calculateIntent.putExtra("yk", editYK.getText().toString());
        calculateIntent.putExtra("activation", editActivationInsulin.getText().toString());
        calculateIntent.putExtra("celGluk", editCelGluk.getText().toString());


        btnCalculate.setOnClickListener(view -> {
            calculateIntent.putExtra("gluk", editGluk.getText().toString());
            calculateIntent.putExtra("yglev", editYglev.getText().toString());
            calculateIntent.putExtra("fchi", editFCHI.getText().toString());
            calculateIntent.putExtra("yk", editYK.getText().toString());
            calculateIntent.putExtra("activation", editActivationInsulin.getText().toString());
            calculateIntent.putExtra("celGluk", editCelGluk.getText().toString());
            startActivity(calculateIntent);
        });

        return viewP;
    }
}
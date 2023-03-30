package com.example.medicineremindernew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    TextView glukoza;
    TextView XE;
    TextView eat;
    TextView insulin;
    TextView corect;
    TextView result;
    TextView calcEat;
    TextView calcCorect;
    TextView calcResult;
    Button btnCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        glukoza = findViewById(R.id.glukoza);
        XE = findViewById(R.id.XE);
        eat = findViewById(R.id.eat);
        insulin = findViewById(R.id.insulin);
        corect = findViewById(R.id.corect);
        result = findViewById(R.id.result);
        calcEat = findViewById(R.id.calcEat);
        calcCorect = findViewById(R.id.calcCorect);
        calcResult = findViewById(R.id.calcResult);
        btnCalculate = findViewById(R.id.btnCalculate);
        Intent main_intent = new Intent(this, MainActivity.class);

        String strGluk = getIntent().getStringExtra("gluk");
        String strYglev = getIntent().getStringExtra("yglev");
        String strFchi = getIntent().getStringExtra("fchi");
        String strYk = getIntent().getStringExtra("yk");
        String strActivation = getIntent().getStringExtra("activation");
        String strCelGluk = getIntent().getStringExtra("celGluk");
        getIntent().getIntArrayExtra("gluk");

        int correct = Integer.parseInt(strGluk) - Integer.parseInt(strCelGluk);
        correct = correct / Integer.parseInt(strFchi);
        int corEat = Integer.parseInt(strYglev) * Integer.parseInt(strYk);
        int corRes = correct + corEat + Integer.parseInt(strActivation);
        String strCorrect = String.valueOf(correct);
        String strCalcCorect = "(" + strGluk + "-" + strCelGluk + ")" + "/" + strFchi + "=" + strCorrect;
        String strEat = String.valueOf(corEat);
        String strCalcEat = strYglev + "*" + strYk + "=" + strEat;
        String strResult = String.valueOf(corRes);
        String strRes = corEat + "+" + correct + "+" + strActivation + "=" + corRes;
        glukoza.setText(strGluk);
        result.setText(strResult);
        eat.setText(strEat + " Ед");
        insulin.setText(strCorrect + " Ед");
        calcEat.setText(strCalcEat);
        calcCorect.setText(strCalcCorect);
        calcResult.setText(strRes);
        corect.setText(strActivation);


        btnCalculate.setOnClickListener(view -> startActivity(main_intent));

    }
}
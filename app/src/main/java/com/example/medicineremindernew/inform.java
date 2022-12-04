package com.example.medicineremindernew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class inform extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inform);
        ImageButton back=findViewById(R.id.back);
        Intent bak=new Intent(this,MainActivity.class);

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
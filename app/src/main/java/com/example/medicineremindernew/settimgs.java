package com.example.medicineremindernew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class settimgs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settimgs);
       Button addpill=findViewById(R.id.addButton);
        Intent intent = new Intent(this, AddingPill.class);
        addpill.setOnClickListener(view -> startActivity(intent));
        ImageButton back=findViewById(R.id.back);
        Intent bak = new Intent(this, MainActivity.class);
        back.setOnClickListener(view -> startActivity(bak));
    }
}
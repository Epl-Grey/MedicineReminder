package com.example.medicineremindernew.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.medicineremindernew.R;

public class AddGoodActivity extends AppCompatActivity {

    ImageButton btn;
    private AnimatedVectorDrawable animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_good);
        Intent i = new Intent(this, MainActivity.class);
        Intent intent6 = new Intent(this,MainActivity.class);
        btn = (ImageButton) findViewById(R.id.bk);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent6);
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(i);
                finish();
            }
        }, 3000);
    }
    @Override
    protected void onStart() {
        super.onStart();
        ImageView image=(findViewById(R.id.imageView9));
        Drawable d = image.getDrawable();
        if (d instanceof AnimatedVectorDrawable) {
            Log.d("testanim", "onCreate: instancefound" + d.toString());
            animation = (AnimatedVectorDrawable) d;
            animation.start();
        }
    }
}
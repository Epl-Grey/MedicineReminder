package com.example.medicineremindernew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OnBordingScreen extends AppCompatActivity {


    private ViewPager viewPager;
    private CardView next;
    private ImageView images;
    private TextView skipText;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private SaveState saveState;

    private int buttons[] ={
            R.drawable.ob1,
            R.drawable.ob2,
            R.drawable.ob3,
            R.drawable.ob4
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_bording_screen);
        Intent intent2 = new Intent(this, LoginActivity.class);
        viewPager = findViewById(R.id.viewPager);
        next = findViewById(R.id.nextCard);
        images = findViewById(R.id.imageBtn);
        skipText = findViewById(R.id.skipText);
        saveState = new SaveState(this, "ob");
        if (saveState.getState() >= 1) {
            startActivity(intent2);
            finish();
        }
        skipText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SkipOnBordingScreen();
            }
        });

        ObAdapter adapter = new ObAdapter(this);
        viewPager.setAdapter(adapter);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1, true);
                images.setImageResource(buttons[1]);
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (position < 3) {
                            viewPager.setCurrentItem(position + 1, true);
                            images.setImageResource(buttons[position+1]);
                        }
                        else {
                            SkipOnBordingScreen();
                        }
                    }
                });
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    void SkipOnBordingScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        saveState.setState(1);
        startActivity(intent);
        finish();
    }

}
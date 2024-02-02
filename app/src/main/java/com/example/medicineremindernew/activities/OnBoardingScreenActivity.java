package com.example.medicineremindernew.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.medicineremindernew.ObAdapter;
import com.example.medicineremindernew.R;
import com.example.medicineremindernew.SaveState;

public class OnBoardingScreenActivity extends AppCompatActivity {


    private ViewPager viewPager;

    private Button images;
    private TextView skipText;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private SaveState saveState;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_bording_screen);
        Intent loginIntent = new Intent(this, LoginActivity.class);
        Intent mainIntent = new Intent(this, MainActivity.class);
        viewPager = findViewById(R.id.viewPager);
        images = findViewById(R.id.imageBtn);
        skipText = findViewById(R.id.skipText);
        saveState = new SaveState(this, "ob");
        if (saveState.getState() == 1) {
            startActivity(loginIntent);
            finish();
        }else if(saveState.getState() == 2) {
            startActivity(mainIntent);
            finish();
        }
        skipText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skipOnBoardingScreen();
            }
        });

        ObAdapter adapter = new ObAdapter(this);
        viewPager.setAdapter(adapter);
        images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1, true);

            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                images.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (position < 2) {
                            viewPager.setCurrentItem(position + 1, true);

                        }
                        else {
                            skipOnBoardingScreen();
                        }
                    }
                });
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    void skipOnBoardingScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        saveState.setState(1);
        startActivity(intent);
        finish();
    }

}
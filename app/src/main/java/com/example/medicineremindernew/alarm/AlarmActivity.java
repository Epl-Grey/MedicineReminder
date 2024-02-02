package com.example.medicineremindernew.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.medicineremindernew.SaveSound;
import com.example.medicineremindernew.SaveState;
import com.example.medicineremindernew.fragments.HomeFragment;
import com.example.medicineremindernew.R;

public class AlarmActivity extends AppCompatActivity {
    WebView webView;
    public String fileName = "a2.html";
    Ringtone ringtone;
    ImageButton  back;
    TextView nameTextView;
    private SaveSound saveSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        back=findViewById(R.id.imageButton);
        nameTextView = findViewById(R.id.textView);

        Bundle extras = getIntent().getExtras();
        String name = extras.getString("name");
        nameTextView.setText(name);

        saveSound = new SaveSound(this, "sound");

        Intent intent = new Intent(this, HomeFragment.class);
        back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onDestroy();
                    startActivity(intent);
                    finish();
                }
            });

        Uri notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);


        if(saveSound.getState() != null || saveSound.getState().equals("") || saveSound.getState().equals(" ")) {
            ringtone = RingtoneManager.getRingtone(this, Uri.parse(saveSound.getState()));
        } else {
            ringtone = RingtoneManager.getRingtone(this, notificationUri);
        }
        if (ringtone == null) {
            notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            ringtone = RingtoneManager.getRingtone(this, notificationUri);
        }
        if (ringtone != null) {
            ringtone.play();
        }
    }

    @Override
    protected void onDestroy() {
        if (ringtone != null && ringtone.isPlaying()) {
            ringtone.stop();
        }
        super.onDestroy();
    }
}
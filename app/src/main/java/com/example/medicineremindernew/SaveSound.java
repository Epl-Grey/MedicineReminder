package com.example.medicineremindernew;

import android.content.Context;
import android.content.SharedPreferences;

public class SaveSound {

    private Context context;
    private String saveName;
    private SharedPreferences savePref;

    public SaveSound(Context context, String saveName) {
        this.context = context;
        this.saveName = saveName;
        savePref = context.getSharedPreferences(saveName,context.MODE_PRIVATE);
    }

    public void setState(String filePath){
        SharedPreferences.Editor editor = savePref.edit();
        editor.putString("mp3_file_path", filePath);
        editor.apply();
    }

    public String getState(){
        return savePref.getString("mp3_file_path", "");
    }
}

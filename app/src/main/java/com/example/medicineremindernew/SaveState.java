package com.example.medicineremindernew;

import android.content.Context;
import android.content.SharedPreferences;

public class SaveState {

    private Context context;
    private String saveName;
    private SharedPreferences savePref;

    public SaveState(Context context, String saveName) {
        this.context = context;
        this.saveName = saveName;
        savePref = context.getSharedPreferences(saveName,context.MODE_PRIVATE);
    }

    public void setState(int key){
        SharedPreferences.Editor editor = savePref.edit();
        editor.putInt("key",key);
        editor.apply();
    }

    public int getState(){
        return savePref.getInt("key",0);
    }
}

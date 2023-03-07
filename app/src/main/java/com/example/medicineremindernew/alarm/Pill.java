package com.example.medicineremindernew.alarm;

public class Pill {
    public String id;
    public String name;
    public int value;
    public String dosage;
    public String date1;
    public String date2;
    public String time;

    public Pill(String id, String name, int value, String dosage, String date1, String date2, String time){
        this.id = id;
        this.name = name;
        this.value = value;
        this.dosage = dosage;
        this.date1 = date1;
        this.date2 = date2;
        this.time = time;
    }
}

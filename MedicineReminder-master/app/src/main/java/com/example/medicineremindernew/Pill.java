package com.example.medicineremindernew;

public class Pill {
    public int id;
    public String name;
    public int value;
    public String dosage;
    public String date1;
    public String date2;
    public String time;

    Pill(int id, String name, int value, String dosage, String date1, String date2, String time){
        this.id = id;
        this.name = name;
        this.value = value;
        this.dosage = dosage;
        this.date1 = date1;
        this.date2 = date2;
        this.time = time;
    }
}

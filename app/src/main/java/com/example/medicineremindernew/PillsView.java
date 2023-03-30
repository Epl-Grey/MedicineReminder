package com.example.medicineremindernew;

public class PillsView {

    // the resource ID for the imageView
    private String name;

    // TextView 1
    private String kl;

    // TextView 1
    private String time;
    private String id;

    // create constructor to set the values for all the parameters of the each single view
    public PillsView(String name, String kl, String time, String id) {
        this.name = name;
        this.kl = kl;
        this.time = time;
        this.id = id;
    }



    // getter method for returning the ID of the imageview
    public String getName() {
        return name;
    }

    // getter method for returning the ID of the TextView 1
    public String getKl() {
        return kl;
    }

    // getter method for returning the ID of the TextView 2
    public String getTime() {
        return time;
    }

    public String getId() {
        return id;
    }
}
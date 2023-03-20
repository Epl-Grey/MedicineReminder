package com.example.medicineremindernew;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CalendarUtils
{
    public static LocalDate selectedDate;



    public static String monthYearFromDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public static ArrayList<LocalDate> daysInWeekArray(LocalDate selectedDate) {
        ArrayList<LocalDate> days = new ArrayList<>();
        LocalDate current = selectedDate.withDayOfMonth(1);
        LocalDate endDate = selectedDate.withDayOfMonth(selectedDate.getMonth().length(selectedDate.isLeapYear()));

        while (current.isBefore(endDate.plusDays(1)))
        {
            days.add(current);
            current = current.plusDays(1);
        }



        return days;
    }

    public static ArrayList<String> numberOfDays(LocalDate selectedDate) {
        ArrayList<String> numberWeek = new ArrayList<>();
        LocalDate current = selectedDate.withDayOfMonth(1);
        LocalDate endDate = selectedDate.withDayOfMonth(selectedDate.getMonth().length(selectedDate.isLeapYear()));

        while (current.isBefore(endDate.plusDays(1)))
        {
            String dayOfWeek = current.getDayOfWeek().toString();

            if(dayOfWeek.equals("WEDNESDAY")) {
                numberWeek.add("ср");
            } else if(dayOfWeek.equals("MONDAY")) {
                numberWeek.add("пн");
            } else if(dayOfWeek.equals("TUESDAY")) {
                numberWeek.add("вт");
            } else if(dayOfWeek.equals("THURSDAY")) {
                numberWeek.add("чт");
            } else if(dayOfWeek.equals("FRIDAY")) {
                numberWeek.add("пт");
            } else if(dayOfWeek.equals("SATURDAY")) {
                numberWeek.add("сб");
            } else if(dayOfWeek.equals("SUNDAY")) {
                numberWeek.add("вс");
            }

            current = current.plusDays(1);
        }



        return numberWeek;
    }
}

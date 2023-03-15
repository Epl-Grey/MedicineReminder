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

    public static ArrayList<LocalDate> daysInWeekArray(LocalDate selectedDate)
    {
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

}

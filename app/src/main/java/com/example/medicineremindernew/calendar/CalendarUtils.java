package com.example.medicineremindernew.calendar;

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

            switch (dayOfWeek) {
                case "WEDNESDAY":
                    numberWeek.add("Ср");
                    break;
                case "MONDAY":
                    numberWeek.add("Пн");
                    break;
                case "TUESDAY":
                    numberWeek.add("Вт");
                    break;
                case "THURSDAY":
                    numberWeek.add("Чт");
                    break;
                case "FRIDAY":
                    numberWeek.add("Пт");
                    break;
                case "SATURDAY":
                    numberWeek.add("Сб");
                    break;
                case "SUNDAY":
                    numberWeek.add("Вс");
                    break;
            }

            current = current.plusDays(1);
        }



        return numberWeek;
    }
}

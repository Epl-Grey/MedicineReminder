package com.example.medicineremindernew;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>
{
    private final ArrayList<LocalDate> days;
    private final OnItemListener onItemListener;

    private String dayOfWeek;

    public CalendarAdapter(ArrayList<LocalDate> days, OnItemListener onItemListener)
    {
        this.days = days;
        this.onItemListener = onItemListener;
    }

    @NonNull 
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height = 155;



        return new CalendarViewHolder(view, onItemListener, days);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position)
    {
        final LocalDate date = days.get(position);
        if(date == null)
            holder.dayOfMonth.setText("");
        else
        {
            holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));
            if(date.equals(CalendarUtils.selectedDate)){
                holder.parentView.setBackgroundResource(R.drawable.selectitem);
            } else {
                holder.parentView.setBackgroundResource(R.drawable.noselectitem);


//                for (int i = 0; i < 10; i++) {
//                    dayOfWeek = days.get(i).getDayOfWeek().toString();
//                    System.out.println(dayOfWeek);
//                    ;
//                    if(dayOfWeek.equals("WEDNESDAY")) {
//                        holder.weekText.setText("Ср");
//                    } else if(dayOfWeek.equals("MONDAY")) {
//                        holder.weekText.setText("Пн");
//                    } else if(dayOfWeek.equals("TUESDAY")) {
//                        holder.weekText.setText("Вт");
//                    } else if(dayOfWeek.equals("THURSDAY")) {
//                        holder.weekText.setText("Чт");
//                    } else if(dayOfWeek.equals("FRIDAY")) {
//                        holder.weekText.setText("Пт");
//                    } else if(dayOfWeek.equals("SATURDAY")) {
//                        holder.weekText.setText("Сб");
//                    } else if(dayOfWeek.equals("SUNDAY")) {
//                        holder.weekText.setText("Вс");
//                    }
//                }

            }

        }
    }

    @Override
    public int getItemCount()
    {
        return days.size();
    }

    public interface  OnItemListener
    {
        void onItemClick(int position, LocalDate date);
    }
}

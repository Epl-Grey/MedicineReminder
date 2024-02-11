package com.example.medicineremindernew.calendar;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicineremindernew.R;
import com.example.medicineremindernew.fragments.HomeFragment;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>
{
    private final ArrayList<LocalDate> days;
    private final OnItemListener onItemListener;
    private final ArrayList<String> numberWeek;
    private HomeFragment context;



    public CalendarAdapter(OnItemListener onItemListener, ArrayList<LocalDate> days, ArrayList<String> numberWeek, HomeFragment context)
    {
        this.days = days;
        this.onItemListener = onItemListener;
        this.numberWeek = numberWeek;
        this.context = context;
    }



    @NonNull 
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
     //       layoutParams.height = 165;



        return new CalendarViewHolder(view, onItemListener, days, numberWeek, context);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position)
    {

        final LocalDate date = days.get(position);
        final String text = numberWeek.get(position);
        if(date == null){
            holder.dayOfMonth.setText("");
            holder.weekText.setText("");
        } else{
            holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));
            for(int i = 1; i <numberWeek.size(); i++) {
                holder.weekText.setText(text);
            }
            if(date.equals(CalendarUtils.selectedDate)){
                holder.parentView.setBackgroundResource(R.drawable.rectangle12111);
                holder.weekText.setTextColor(R.color.black);

            } else {

                holder.parentView.setBackgroundResource(R.drawable.calendar_item);


            }

        }
    }

    @Override
    public int getItemCount()
    {
        return numberWeek.size();
    }

    public interface  OnItemListener
    {
        void onItemClick(int position, LocalDate date);
    }


}

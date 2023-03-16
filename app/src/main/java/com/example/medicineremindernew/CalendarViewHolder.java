package com.example.medicineremindernew;

import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    private final ArrayList<LocalDate> days;
    public final View parentView;
    public final TextView dayOfMonth;
    public final TextView weekText;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    private final CalendarAdapter.OnItemListener onItemListener;
    public CalendarViewHolder(@NonNull View itemView, CalendarAdapter.OnItemListener onItemListener, ArrayList<LocalDate> days)
    {
        super(itemView);
        parentView = itemView.findViewById(R.id.parentView);
        dayOfMonth = itemView.findViewById(R.id.cellDayText);
        weekText = itemView.findViewById(R.id.weekText);
        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
        this.days = days;
    }
    MainActivity mainActivity = new MainActivity();

    @Override
    public void onClick(View itemView)
    {
        onItemListener.onItemClick(getAdapterPosition(),
                days.get(getAdapterPosition()));

        mainActivity.onCalendarItem(mainActivity.databaseHelper1, mainActivity.db2);

    }
}

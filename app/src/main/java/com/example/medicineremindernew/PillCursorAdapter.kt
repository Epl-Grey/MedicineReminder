package com.example.medicineremindernew

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cursoradapter.widget.CursorAdapter
import java.util.ArrayList

class PillCursorAdapter(context: Context?, cursor: Cursor?): CursorAdapter(context, cursor, 0){
    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
        return LayoutInflater.from(context).inflate(R.layout.row_layout, parent, false)
    }

    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
        val nameTextView: TextView = view!!.findViewById(R.id.name) // Pill name
        val timesTextView: TextView = view.findViewById(R.id.kl) // Times per day
        val timeTextView: TextView = view.findViewById(R.id.time) // Pill time
        val idTextView: TextView = view.findViewById(R.id.id_storage) // TextView for storing ID :)

        val id = cursor!!.getString(0)
        val name = cursor.getString(1)
        val times = cursor.getString(2)
        val time = cursor.getString(3)

        idTextView.text = id
        nameTextView.text = name
        timesTextView.text = times
        timeTextView.text = time
    }
}
package com.example.medicineremindernew

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.medicineremindernew.models.Pill

class PillAdapter (context: Context, dataArrayList: List<Pill?>?) :
    ArrayAdapter<Pill?>(context, R.layout.pill_list_layout, dataArrayList!!) {
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        var view = view
        val pill = getItem(position)
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.pill_list_layout, parent, false)
        }

        val pillName = view!!.findViewById<TextView>(R.id.name)
        val pillTime = view.findViewById<TextView>(R.id.time)

        pillName.text = pill!!.name
        pillTime.text = pill.times!![0]

        return view
    }
}
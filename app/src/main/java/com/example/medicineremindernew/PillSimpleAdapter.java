package com.example.medicineremindernew;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class PillSimpleAdapter extends ArrayAdapter<PillsView> {

    public PillSimpleAdapter(@NonNull Context context, ArrayList<PillsView> arrayList) {

        // pass the context and arrayList for the super
        // constructor of the ArrayAdapter class
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // convertView which is recyclable view
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.row_layout, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        PillsView currentNumberPosition = getItem(position);

        // then according to the position of the view assign the desired image for the same
        TextView nameText = currentItemView.findViewById(R.id.name);
        assert currentNumberPosition != null;
        nameText.setText(currentNumberPosition.getName());

        // then according to the position of the view assign the desired TextView 1 for the same
        TextView klText = currentItemView.findViewById(R.id.kl);
        klText.setText(currentNumberPosition.getKl());

        // then according to the position of the view assign the desired TextView 2 for the same
        TextView timeText = currentItemView.findViewById(R.id.time);
        timeText.setText(currentNumberPosition.getTime());

        TextView idText = currentItemView.findViewById(R.id.id_storage);
        idText.setText(currentNumberPosition.getId());
        // then return the recyclable view
        return currentItemView;
    }
}



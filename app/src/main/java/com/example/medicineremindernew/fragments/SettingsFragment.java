package com.example.medicineremindernew.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.medicineremindernew.AddingPill;
import com.example.medicineremindernew.LoginActivity;
import com.example.medicineremindernew.R;
import com.example.medicineremindernew.RegisterActivity;


public class SettingsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View viewP = inflater.inflate(R.layout.fragment_settings, container, false);

        Button addpill = viewP.findViewById(R.id.addButton);
        Intent intent = new Intent(getActivity(), AddingPill.class);
        addpill.setOnClickListener(view -> startActivity(intent));
        ImageButton back = viewP.findViewById(R.id.back);
        Intent bak = new Intent(getActivity(), HomeFragment.class);
        back.setOnClickListener(view -> startActivity(bak));

        TextView login_btn = viewP.findViewById(R.id.email);
        TextView register_btn = viewP.findViewById(R.id.theme);

        login_btn.setOnClickListener(view -> {
            Intent intent12 = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent12);
        });
        register_btn.setOnClickListener(view -> {
            Intent intent1 = new Intent(getActivity(), RegisterActivity.class);
            startActivity(intent1);
        });

        return viewP;
    }


}
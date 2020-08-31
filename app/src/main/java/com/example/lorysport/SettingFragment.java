package com.example.lorysport;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class SettingFragment extends Fragment implements View.OnClickListener {
    Button yellow;
    Button green;
    Button custom;
    MainActivity2 activity;
    int color;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = ((MainActivity2) getActivity());
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        color = activity.custom;
        custom = view.findViewById(R.id.custom);
        if(color!=1) {
            custom.setOnClickListener(this);
        }
        yellow = view.findViewById(R.id.yellow);
        if(color!=2) {
            yellow.setOnClickListener(this);
        }
        green = view.findViewById(R.id.green);
        if(color!=3) {
            green.setOnClickListener(this);
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.custom:
                color = 1;
                activity.custom=color;
                activity.recreate();
                activity.changefragment(6);
                break;
            case R.id.yellow:
                color = 2;
                activity.custom=color;
                activity.recreate();
                activity.changefragment(6);
                break;
            case R.id.green:
                color = 3;
                activity.custom=color;
                activity.recreate();
                activity.changefragment(6);
                break;

        }
    }
}
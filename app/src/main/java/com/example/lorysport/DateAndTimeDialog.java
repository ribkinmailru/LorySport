package com.example.lorysport;

import android.app.Dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.GregorianCalendar;

public class DateAndTimeDialog extends DialogFragment implements View.OnClickListener {
        MainActivity2 activity;
        AlertDialog dialogs;
        methods met1;
    TimePicker time;
    DatePicker date;
    int i = 0;


        public interface methods{
            void onok(int year, int month, int day, int hour, int minutes,long c);
        }

        public void setlist(methods met1){
            this.met1 = met1;
        }


        @NonNull
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            final View v1 = View.inflate(getActivity(), R.layout.dateandtime, null);
            final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity())
                    .setView(v1);

            dialogs = dialog.create();

            Button btnReopenId = v1.findViewById(R.id.bs);
            Button btnCancelId = v1.findViewById(R.id.bs1);
            time = v1.findViewById(R.id.tim);
            time.setVisibility(View.GONE);
            date = v1.findViewById(R.id.dat);
            btnReopenId.setOnClickListener(this);
            btnCancelId.setOnClickListener(this);
            activity = ((MainActivity2) getActivity());

            return dialogs;
        }


        @Override
        public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.bs1:
                        if(i==0) {
                            time.setVisibility(View.VISIBLE);
                            date.setVisibility(View.GONE);
                        }
                        if(i==1) {
                            int year = date.getYear();
                            int months = date.getMonth();
                            int days = date.getDayOfMonth();
                            GregorianCalendar calendar = new GregorianCalendar(year, months, days);
                            long c = calendar.getTimeInMillis();
                            int hour = 0;
                            int minutes = 0;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                                hour = time.getHour();
                                minutes = time.getMinute();

                            }else{
                                hour = time.getCurrentHour();
                                minutes = time.getCurrentMinute();
                            }
                            met1.onok(year,months,days,hour,minutes,c);
                            dialogs.dismiss();
                        }
                        i++;
                        break;
                    case R.id.bs:
                        dialogs.dismiss();
                        break;
                }

            }


        @Override
        public void onDetach() {
            super.onDetach();
            dialogs = null;
            activity = null;
        }
    }

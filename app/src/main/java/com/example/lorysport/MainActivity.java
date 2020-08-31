package com.example.lorysport;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class MainActivity extends Fragment {
    Date mil;
    long timeInMillis;
    long min;
    long max;
    TimePicker times;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        ArrayList<Long> next;
        ArrayList<Long> prev;
        GregorianCalendar calendar = new GregorianCalendar();
        DateFormat forLong = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        final MainActivity2 activity = ((MainActivity2) getActivity());
        TextView title = getActivity().findViewById(R.id.textView3);
        title.setText(R.string.profile);
        View view = inflater.inflate(R.layout.activity_main, container, false);
        String millis = forLong.format(calendar.getTime());
        try {
            mil = forLong.parse(millis);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        timeInMillis = mil.getTime();
        AsyncTask<Context,Void,SQLiteDatabase> mm = new mm();
        mm.execute(getContext());
        SQLiteDatabase dbs = null;
        try {
            dbs = mm.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        AsyncTask<SQLiteDatabase,Void,Cursor> curs = new curs();
        curs.execute(dbs);
        Cursor cursors = null;
        try {
            cursors = curs.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        next = new ArrayList<>();
        prev = new ArrayList<>();

        cursors.moveToFirst();
        long s;
        do {
            try {
                s = cursors.getLong(0);
                if (s < timeInMillis) {
                    prev.add(s);
                }
                if (s >= timeInMillis) {
                    next.add(s);
                }
            }catch (RuntimeException ignored){}
        }while (cursors.moveToNext());


    if(next.size()!=0) {
        min = next.get(0);
    }
    if(prev.size()!=0) {
        max= prev.get(0);
        }
     for (int k = 0; k<prev.size(); k++) {
            if (max < prev.get(k)) {
                max = prev.get(k);
            }
        }
     for (int t = 0; t<next.size(); t++){
                if (min > next.get(t)){
                    min = next.get(t);
                }
            }



        Date datenext = new Date(min);
        Date dateprev = new Date(max);




        DateFormat dateFormate = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        String trin = dateFormate.format(datenext);
        String previous = dateFormate.format(dateprev);
        TextView nexts = view.findViewById(R.id.textView8);
        TextView prevs = view.findViewById(R.id.textView9);
        ImageView im = view.findViewById(R.id.notif);
        nexts.setText(trin);
        prevs.setText(previous);


        if(min==0){
            nexts.setText(R.string.notrains);

        }
        if(max==0){
            prevs.setText(R.string.notrains);
        }
        if(min==timeInMillis){
            nexts.setText(R.string.today);

        }
        if(min==timeInMillis+86400000){
            nexts.setText(R.string.tomorrow);

        }
        if(max==timeInMillis-86400000){
            prevs.setText(R.string.yesterday);
        }
        cursors.close();
        dbs.close();
        final FloatingActionButton but = view.findViewById(R.id.button3);
        final FloatingActionButton butnp = view.findViewById(R.id.buttons);

        times = view.findViewById(R.id.times);
        times.setVisibility(View.GONE);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                times.setVisibility(View.VISIBLE);
                but.setVisibility(View.VISIBLE);
                butnp.setVisibility(View.VISIBLE);
                times.setIs24HourView(true);
            }
        });
        but.setVisibility(View.GONE);
        butnp.setVisibility(View.GONE);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = times.getCurrentHour();
                int minutes = times.getCurrentMinute();
                long c;
                if(min!=0) {
                    c = min + hour * 3600000L + minutes * 60000L;
                }
                else {
                    c = timeInMillis + hour * 3600000L + minutes * 60000L;
//
                }


                times.setVisibility(View.GONE);
                but.setVisibility(View.GONE);
                butnp.setVisibility(View.GONE);
                Toast.makeText(activity, R.string.notif, Toast.LENGTH_SHORT).show();

            }
        });
        butnp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                times.setVisibility(View.GONE);
                but.setVisibility(View.GONE);
                butnp.setVisibility(View.GONE);
            }
        });

        return view;
    }

    private static class mm extends AsyncTask<Context,Void,SQLiteDatabase>{

        @Override
        protected SQLiteDatabase doInBackground(Context... voids) {
            SQLiteOpenHelper date = new DateDatabase(voids[0]);
            return date.getReadableDatabase();
        }
    }


    private static class curs extends AsyncTask<SQLiteDatabase,Void,Cursor>{

        @Override
        protected Cursor doInBackground(SQLiteDatabase... dbs) {
            return dbs[0].query("DATES",
                    new String[]{"MILLIS"},
                    null,null,null,null,null);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        times = null;
    }
}
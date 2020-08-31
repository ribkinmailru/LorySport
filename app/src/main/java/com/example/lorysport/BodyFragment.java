package com.example.lorysport;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Calendar;


public class BodyFragment extends Fragment implements View.OnClickListener {
    int days;
    double result;
    int[] alldays;
    String countnow;
    long t;
    long millis;
    MainActivity2 activity;
    int wh;


    public BodyFragment(int wh){
        this.wh = wh;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = (MainActivity2) getActivity();
        View view = inflater.inflate(R.layout.body_exfrag, container, false);
        TextView title = activity.findViewById(R.id.textView3);
        title.setText(R.string.params);
        TextView up = view.findViewById(R.id.textView5);
        TextView down = view.findViewById(R.id.textView6);
        String date = activity.downday();
        String dayofweek = activity.upday();
        up.setText(dayofweek);
        millis = activity.getmillis();
        down.setText(date);
        ImageButton next = view.findViewById(R.id.imageButton);
        ImageButton prev = view.findViewById(R.id.imageButton1);
        LinearLayout linees = view.findViewById(R.id.linee);

        switch (activity.custom) {
            case 3:
                next.setBackgroundResource(R.drawable.selector_button_right_green);
                prev.setBackgroundResource(R.drawable.selector_button_left_green);
                linees.setBackgroundResource(R.drawable.shapetext_green);
                break;
            case 2:
                next.setBackgroundResource(R.drawable.selector_button_right);
                prev.setBackgroundResource(R.drawable.selector_button_left);
                linees.setBackgroundResource(R.drawable.shapelayout);
                break;
        }
        next.setOnClickListener(this);
        prev.setOnClickListener(this);
        final int[] whatg;
        final String[] counts;
        final double [] gg;
        if(wh==1){
            whatg = new int[12];
            counts = new String[12];
            alldays = new int[12];
            gg = new double[12];
        }else{
            whatg = new int[7];
            counts = new String[7];
            alldays = new int[7];
            gg = new double[7];
        }
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                SQLiteOpenHelper exe = new BodyDatabase(getActivity());
                final SQLiteDatabase db = exe.getReadableDatabase();
                Cursor cursor = db.query("BODY",
                        new String[]{"COUNT", "POSITION"},
                        "MILLIS=? AND WHAT=?",
                        new String[]{Long.toString(millis), Integer.toString(wh)},
                        null,null,null);
                cursor.moveToFirst();
                do {
                    try {
                        int g = Integer.parseInt(cursor.getString(1));
                        counts[g] = cursor.getString(0);
                        whatg[g] = g;
                    }catch (CursorIndexOutOfBoundsException ignored){}
                }while(cursor.moveToNext());
                int t=0;
                if(wh==1) {
                    while (t < 12) {
                        if (t == whatg[t]) {
                            countlust(db, t);
                            alldays[t] = days;
                            culculate(db, t);
                            gg[t] = result;
                        }
                        t++;
                    }
                }else{
                    while (t < 7) {
                        if (t == whatg[t]) {
                            countlust(db, t);
                            alldays[t] = days;
                            culculate(db, t);
                            gg[t] = result;
                        }
                        t++;
                    }
                }
                db.close();
                cursor.close();
            }
        });
        thread.start();
        final String day1 = getString(R.string.day1);
        final String day2 = getString(R.string.day2);
        final String day3 = getString(R.string.day3);

        String[] stringss = new String[5];

        final String last1 = getString(R.string.last1);
        final String last2 = getString(R.string.last2);
        stringss[0] = day1;
        stringss[1] = day2;
        stringss[2] = day3;
        stringss[3] = last1;
        stringss[4] = last2;
        final String[] name;
        final String[] params;
        String kg = getString(R.string.kg);
        String sm = getString(R.string.sm);
        if(wh==1) {
            final String[] name1 = {"Вес", "Рост", "Охват шеи",
                    "Охват плеч", "Охват груди", "Охват талии", "Охват бедер", "Охват голени",
                    "Охват руки", "Охват предплечья", "Охват запястья", "Процент жира"};
            final String[] params1 = {kg, sm, sm,
                    sm, sm, sm, sm, sm, sm, sm, sm, "%"};
            name = name1;
            params = params1;
        }else{
            final String[] name1 = {"Вес", "Рост", "Охват шеи",
                    "Охват плеч", "Охват груди", "Охват талии", "Охват бедер"};
            final String[] params1 = {kg, sm, sm,
                    sm, sm, sm, sm};
            name=name1;
            params=params1;
        }
        RecyclerView recycler = view.findViewById(R.id.reccs);
        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(name, params, counts, activity.custom, gg, alldays, stringss);
        recycler.setHasFixedSize(true);
        adapter.inputtype=4;

        adapter.setListener3(new CaptionedImagesAdapter.Listener3() {
            @Override
            public void onClick3(int position, EditText edittext, TextView text) {
                String edit = edittext.getText().toString();
                SQLiteOpenHelper exe = new BodyDatabase(getActivity());
                SQLiteDatabase dbs = exe.getWritableDatabase();
                Cursor cursor = dbs.query("BODY",
                            new String[]{"_id"},
                            "MILLIS=? AND POSITION=? AND WHAT=?",
                            new String[]{Long.toString(millis), Integer.toString(position), Integer.toString(wh)}, null, null, null);

                cursor.moveToFirst();
                ContentValues content = new ContentValues();
                if(cursor.getCount()==0){
                    content.put("MILLIS",millis);
                    content.put("POSITION", position);
                    content.put("COUNT", edit);
                    content.put("WHAT", wh);
                    dbs.insert("BODY", null, content);
                }
                if(cursor.getCount()!=0){
                    content.put("COUNT", edit);
                    dbs.update("BODY", content, "_id=?", new String[]{cursor.getString(0)});
                }
                try {
                    if (edit.equals("")) {
                        dbs.delete("BODY", "_id=?", new String[]{cursor.getString(0)});
                    }
                }catch (CursorIndexOutOfBoundsException ignored){}
                SQLiteDatabase ss = exe.getReadableDatabase();
                countlust(ss,position);
                culculate(ss,position);
                StringBuilder tex = new StringBuilder();
                if(!edit.equals("") && result!=0) {
                    if (result > 0) {
                        text.setTextColor(ContextCompat.getColor(text.getContext(), R.color.green));
                        tex.append("+");
                    }
                    if (result < 0) {
                        text.setTextColor(ContextCompat.getColor(text.getContext(), R.color.red));
                    }
                    if (days == 1) {
                        tex.append(result).append(" ").append(last1).append(" ").append(days).append(" ").append(day1);
                        text.setVisibility(View.VISIBLE);
                    }
                    if (days > 1 && days < 5) {
                        tex.append(result).append(" ").append(last2).append(" ").append(days).append(" ").append(day2);
                        text.setVisibility(View.VISIBLE);
                    }
                    if (days > 21 && days < 25) {
                        tex.append(result).append(" ").append(last2).append(" ").append(days).append(" ").append(day2);
                        text.setVisibility(View.VISIBLE);
                    }
                    if (days == 21) {
                        tex.append(result).append(" ").append(last1).append(" ").append(days).append(" ").append(day1);
                        text.setVisibility(View.VISIBLE);
                    }
                    if (days >= 5) {
                        tex.append(result).append(" ").append(last2).append(" ").append(days).append(" ").append(day3);
                        text.setVisibility(View.VISIBLE);
                    }

                }

                text.setText(tex);
                cursor.close();
                dbs.close();
                ss.close();
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapter);

        return view;
    }




    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton1:
                activity.calendar.add(Calendar.DATE, -1);
                activity.changeBody(wh);
                break;
            case R.id.imageButton:
                activity.calendar.add(Calendar.DATE, +1);
                activity.changeBody(wh);
                break;
        }
    }




    public void countlust(SQLiteDatabase db, int position){
        Cursor cursorday = db.query("BODY",
                new String[]{"MILLIS"}, "POSITION=? AND WHAT=?", new String[]{Integer.toString(position), Integer.toString(wh)}, null, null, null);
        Cursor cursor = db.query("BODY",
                new String[]{"COUNT"},
                "MILLIS=? AND POSITION=? AND WHAT=?",
                new String[]{Long.toString(millis), Integer.toString(position), Integer.toString(wh)},
                null,
                null,
                null);

        cursor.moveToFirst();
    long[] privious = new long[30];
    cursorday.moveToFirst();
    int h = 0;
        long d = 0;
        do {
            if (cursorday.getCount() > 0) {
                try {
                     d = cursorday.getLong(0);
                }catch (CursorIndexOutOfBoundsException ignored){}
                if (d < millis && d > millis - 2592000000L) {
                    privious[h] = d;
                    h++;

                }
            }
        } while (cursorday.moveToNext());
        try {
            countnow = cursor.getString(0);
        }catch (CursorIndexOutOfBoundsException ignored){}
        t = privious[0];
        for (int y = 0; y < 30; y++) {
            if (t < privious[y]) {
                t = privious[y];
            }
        }

        int countofday = 0;
        long k = millis;
        if (t != 0) {
            while (k != t) {
                k = k - 86400000L;
                countofday++;
            }
        }
        days = countofday;
        cursorday.close();
        cursor.close();
   }

   public void culculate(SQLiteDatabase db, int position){
    Cursor cursor = db.query("BODY",
                new String[]{"COUNT"},
                "POSITION=? AND MILLIS=? AND WHAT=?",
                new String[]{Integer.toString(position),Long.toString(t),Integer.toString(wh)},null,null,null);
    cursor.moveToFirst();
    try {
        result = Double.parseDouble(countnow) - Double.parseDouble(cursor.getString(0));
    }catch (NullPointerException | CursorIndexOutOfBoundsException | NumberFormatException ex){
        result = 0;
    }
    cursor.close();
   }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        activity = null;
    }
}




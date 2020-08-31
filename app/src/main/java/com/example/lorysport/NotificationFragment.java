package com.example.lorysport;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TimePicker;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class NotificationFragment extends Fragment implements View.OnClickListener {
    String[] dates;
    FrameLayout alltime;
    DatePicker nowdate;
    TimePicker nowtime;
    ImageView add;
    FloatingActionButton ok;
    MainActivity2 activity;
    Animation anim;
    CaptionedImagesAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceSatate){
    View view = inflater.inflate(R.layout.notificationfragment,container,false);
        activity = ((MainActivity2) getActivity());
        add = activity.findViewById(R.id.add);
        addd(1);
        setDate();
            RecyclerView recycler = view.findViewById(R.id.notifs);
            adapter = new CaptionedImagesAdapter(dates);
            adapter.inputtype = 5;
            adapter.setListener5(new CaptionedImagesAdapter.Listener5() {
                @Override
                public void onClick5(int position, String bs, View linee) {
                    SQLiteOpenHelper notif = new NotifDatabase(getContext());
                    SQLiteDatabase db = notif.getReadableDatabase();
                    Cursor cursor = db.query("NOTIF",
                            new String[]{"_id"},
                            "DATE=?",
                            new String[]{bs},null,null,null);
                    cursor.moveToFirst();
                    activity.startnotif(0, true, cursor.getInt(0));
                    SQLiteDatabase write = notif.getWritableDatabase();
                    write.delete("NOTIF", "_id=?",new String[]{Integer.toString(cursor.getInt(0))});
                    setDate();
                    adapter.changeDatar(dates, position);
                    cursor.close();
                    db.close();
                    write.close();
                }
            });
            LinearLayoutManager manager = new LinearLayoutManager(getContext());
            recycler.setLayoutManager(manager);
            recycler.setAdapter(adapter);


        alltime = view.findViewById(R.id.alltime);
        alltime.setVisibility(View.GONE);

        nowdate = view.findViewById(R.id.date);
        nowtime = view.findViewById(R.id.time);
        nowtime.setIs24HourView(true);

        ok = view.findViewById(R.id.ok);
        FloatingActionButton no = view.findViewById(R.id.no);
        ok.setOnClickListener(this);
        no.setOnClickListener(this);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateAndTimeDialog dialog = new DateAndTimeDialog();
                dialog.show(getFragmentManager(), "tag");
                dialog.setlist(new DateAndTimeDialog.methods() {
                    @Override
                    public void onok(int year, int month, int day, int hour, int minutes, long c) {
                        c = c + hour * 3600000L + minutes * 60000L;
                        DateFormat forLong = new SimpleDateFormat("yyyy.MM.dd k:mm", Locale.getDefault());
                        Date today = new Date(c);
                        String datess = forLong.format(today);
                        SQLiteOpenHelper notif = new NotifDatabase(getContext());
                        SQLiteDatabase wdb = notif.getWritableDatabase();
                        ContentValues content = new ContentValues();
                        content.put("MILLIS", c);
                        content.put("DATE", datess);
                        wdb.insert("NOTIF", null ,content);
                        wdb.close();
                        setDate();
                        SQLiteOpenHelper notifs = new NotifDatabase(getContext());
                        SQLiteDatabase db = notifs.getReadableDatabase();
                        Cursor cursor = db.query("NOTIF", new String[]{"_id"},
                               null,null,null,null,null);
                        cursor.moveToLast();
                            adapter.changeDatas(dates);
                            try {
                                activity.startnotif(c, false, cursor.getInt(0));
                            }catch (CursorIndexOutOfBoundsException ignored){}
                        cursor.close();
                    }
                });
            }
        });

    return view;
    }


    public void setDate(){
        SQLiteOpenHelper not = new NotifDatabase(getContext());
        SQLiteDatabase db = not.getReadableDatabase();
        Cursor cursor = db.query("NOTIF",
                new String[]{"DATE, MILLIS, _id"},
                null,null,null,null,null);
        ArrayList<Integer> all = null;
            cursor.moveToFirst();
            String date;
            int x = 0;
            dates = new String[cursor.getCount()];
            all = new ArrayList<>();
            Log.d("tag", Integer.toString(all.size()));
            do {
                try {
                date = cursor.getString(0);
                if(cursor.getLong(1)<System.currentTimeMillis()){
                    all.add(cursor.getInt(2));
                }else {
                    dates[x] = date;
                    x++;
                }
                }catch (CursorIndexOutOfBoundsException ignored){}
            } while (cursor.moveToNext());
        cursor.close();
        db.close();
        SQLiteDatabase dbs = not.getWritableDatabase();
        for(x = 0; x<all.size(); x++){
            dbs.delete("NOTIF", "_id=?", new String[]{Integer.toString(all.get(x))});
        }
        db.close();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        alltime = null;
        nowdate = null;
        nowtime = null;
        add = null;
        ok = null;
        activity = null;
    }


    @Override
    public void onStop() {
        super.onStop();
        addd(2);
    }


    public void addd(int i){
        if(i==1) {
            anim = AnimationUtils.loadAnimation(activity, R.anim.delete);
            add.setVisibility(View.VISIBLE);
            if (activity.i != activity.counttodel) {
                add.startAnimation(anim);
            }
        }

        if(i==2) {
            if (activity.counttodel == 0) {
                anim = AnimationUtils.loadAnimation(activity, R.anim.apeardelete);
                add.startAnimation(anim);
                add.setVisibility(View.GONE);
            }
        }


    }

    @Override
    public void onClick(View v) {

    }
}

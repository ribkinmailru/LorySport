package com.example.lorysport;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;


public class Schedule extends Fragment implements View.OnClickListener {
    CaptionedImagesAdapter adapter;
    MainActivity2 activity;
    ImageView delete;
    Animation anim;
    RecyclerView recycler;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        activity = (MainActivity2) getActivity();

        delete = activity.findViewById(R.id.delete);
        anim = AnimationUtils.loadAnimation(activity,  R.anim.delete);
        delete.setVisibility(View.VISIBLE);
        if(activity.i!=activity.counttodel) {
            delete.startAnimation(anim);
        }
        activity.counttodel = activity.i;
        TextView title = activity.findViewById(R.id.textView3);
        title.setText(R.string.Shedule);

        int custom = activity.custom;
        final View view = inflater.inflate(R.layout.activity_schedule, container, false);
        recycler = view.findViewById(R.id.recc);
        LinearLayout linee = view.findViewById(R.id.layout_action);
        delete = activity.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogFragment dialog = new CustomDialogFragment(1);
                dialog.show(activity.getSupportFragmentManager(), "custom");
            }
        });

        TextView den = view.findViewById(R.id.textView5);
        TextView view3 = view.findViewById(R.id.textView6);
        LinearLayout linees = view.findViewById(R.id.linee);

        ImageButton prev = view.findViewById(R.id.imageButton1);
        prev.setOnClickListener(this);
        ImageButton next = view.findViewById(R.id.imageButton);
        ImageView plus = view.findViewById(R.id.imageView);
        plus.setColorFilter(getResources().getColor(R.color.blue));
        switch (custom) {
            case 3:
                next.setBackgroundResource(R.drawable.selector_button_right_green);
                prev.setBackgroundResource(R.drawable.selector_button_left_green);
                linees.setBackgroundResource(R.drawable.shapetext_green);
                plus.setColorFilter(getResources().getColor(R.color.pink));
                break;
            case 2:
                next.setBackgroundResource(R.drawable.selector_button_right);
                prev.setBackgroundResource(R.drawable.selector_button_left);
                linees.setBackgroundResource(R.drawable.shapelayout);
                plus.setColorFilter(getResources().getColor(R.color.red));
                break;
        }
        next.setOnClickListener(this);
        linee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim = AnimationUtils.loadAnimation(activity, R.anim.apeardelete);
                delete.startAnimation(anim);
                delete.setVisibility(View.GONE);
                activity.changefragment(1);
            }
        });


        String date = activity.downday();
        String dayofweek = activity.upday();
        den.setText(dayofweek);
        view3.setText(date);
        try {
            getExe(activity.notUpperCase(), recycler, 1, 0, custom, view);
        }catch (CursorIndexOutOfBoundsException ignored){}

        final CalendarView calendar = view.findViewById(R.id.calendar);
        calendar.setVisibility(View.GONE);
        calendar.setDate(activity.getmillis());


        linees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.setVisibility(View.VISIBLE);
            }

        });

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                calendar.setVisibility(View.GONE);
                activity.calendar.set(year, month, dayOfMonth);
                activity.changefragment(4);

            }
        });
        return view;
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton1:
                activity.calendar.add(Calendar.DATE, -1);
                activity.changefragment(4);
                break;
            case R.id.imageButton:
                activity.calendar.add(Calendar.DATE, +1);
                activity.changefragment(4);
                break;
        }
    }

    public final void getExe(String currentDate, final RecyclerView recycler, int y, int pos, final int custom, final View view) {
        AsyncTask<Context,Void,ArrayList<Object>> mm = new mm(1, currentDate);
        mm.execute(activity);
        ArrayList <Object> array = new ArrayList<>();
        try {
            array = mm.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        SQLiteDatabase db = (SQLiteDatabase) array.get(0);
        final Cursor cursor = (Cursor) array.get(1);
        final String[] EXEID = new String[cursor.getCount()];
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                cursor.moveToFirst();
                int i = 0;
                String ids;
                do {
                    try {
                        ids = cursor.getString(0);
                        EXEID[i] = ids;
                        i++;
                    }catch (CursorIndexOutOfBoundsException ignored){}
                } while (cursor.moveToNext());
            }
        });
        thread.start();


        AsyncTask<Context,Void,SQLiteDatabase> mk = new getdb();
        mk.execute(activity);
        SQLiteDatabase db1 = null;
        try {
            db1 = mk.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        String[] nameArray = new String[cursor.getCount()];
        int[] imageArray = new int[cursor.getCount()];
        String[] download = new String[cursor.getCount()];
        boolean[] favorite = new boolean[cursor.getCount()];
        boolean fav;
        String down;
        String name;
        int image;
        Cursor cursor1 = null;
        int d = 0;
        while (d < cursor.getCount()) {
            mm = new mm(2, EXEID[d], db1);
            mm.execute(activity);
            ArrayList<Object> arrayList = new ArrayList<>();
            try {
                arrayList = mm.get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            cursor1 = (Cursor)arrayList.get(0);
            cursor1.moveToFirst();
            fav = (cursor1.getInt(2) == 1);
            down = cursor1.getString(3);
            name = cursor1.getString(0);
            image = cursor1.getInt(1);
            nameArray[d] = name;
            imageArray[d] = image;
            favorite[d] = fav;
            download[d] = down;
            d++;
        }
        if(cursor1!=null) {
            cursor1.close();
        }
        cursor.close();
        db.close();
        if(y == 2){
            adapter.changeShadule(custom, nameArray, imageArray, favorite, download, activity, pos);
        }
        if(y == 1 && nameArray.length!=0) {
            adapter = new CaptionedImagesAdapter(custom, nameArray, imageArray, favorite, download, activity);
            adapter.setListener2(new CaptionedImagesAdapter.Listener2() {
                @Override
                public void onClick2(View view, int pos, LinearLayout frima) {
                    if (frima.getVisibility() == View.VISIBLE) {
                        frima.setVisibility(View.GONE);
                    } else {
                        frima.setVisibility(View.VISIBLE);
                        recycler.smoothScrollToPosition(pos + 2);
                    }
                }
            });
            adapter.setListener5(new CaptionedImagesAdapter.Listener5() {
                @Override
                public void onClick5(int position, String bs, View linee) {
                    SQLiteOpenHelper date = new DateDatabase(activity);
                    SQLiteDatabase db = date.getWritableDatabase();
                    SQLiteDatabase dbs = date.getReadableDatabase();
                    Cursor cursor = dbs.query("DATES",
                            new String[]{"_id"},
                            "DATE=?",
                            new String[]{activity.notUpperCase()}, null, null, null);
                    cursor.moveToPosition(position);
                    db.delete("DATES", "_id=?", new String[]{cursor.getString(0)});
                    getExe(activity.notUpperCase(), recycler, 2, position, custom, view);
                    cursor.close();
                    db.close();
                }
            });
            adapter.inputtype = 3;
            recycler.setHasFixedSize(true);
            GridLayoutManager layoutManager = new GridLayoutManager(activity, 1);
            recycler.setLayoutManager(layoutManager);
            recycler.setAdapter(adapter);
            NestedScrollView myScroll = view.findViewById(R.id.scrl);
            myScroll.scrollTo(0, 0);
            db1.close();
        }
    }


    private static class mm extends AsyncTask<Context,Void, ArrayList<Object>>{
        final int index;
        Cursor cursor;
        String currentDate;
        String EXED;
        final ArrayList<Object> array = new ArrayList<>();
        SQLiteDatabase db1;


        public mm(int index, String currentDate){
            this.index=index;
            this.currentDate = currentDate;
        }

        public mm(int index, String currentDate,SQLiteDatabase db1){
            this.index=index;
            this.EXED = currentDate;
            this.db1 = db1;
        }

        protected ArrayList<Object> doInBackground(Context... voids) {
            if(index==1){
                SQLiteOpenHelper exe = new DateDatabase(voids[0]);
                db1 = exe.getReadableDatabase();
                cursor = db1.query("DATES",
                        new String[]{"EXEID"},
                        "DATE=?",
                        new String[]{currentDate},
                        null, null, null);
                array.add(db1);
                array.add(cursor);
            }
            if(index==2){
                Cursor cursor1 = db1.query("EXE",
                        new String[]{"NAME", "IMAGEID", "FAVORITE", "DOWNLOAD"},
                        "_id=?",
                        new String[]{EXED},
                        null, null, null, null);
                array.add(cursor1);
            }
            return array;
        }
    }


    private static class getdb extends AsyncTask<Context,Void,SQLiteDatabase>{

        @Override
        protected SQLiteDatabase doInBackground(Context... voids) {
            SQLiteOpenHelper idexe = new ExeDatabase(voids[0]);
            return idexe.getReadableDatabase();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

            if(activity.counttodel==0) {
                anim = AnimationUtils.loadAnimation(activity, R.anim.apeardelete);
                delete.startAnimation(anim);
                delete.setVisibility(View.GONE);
            }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if(adapter!=null) {
            adapter.setListener2(null);
            adapter=null;
        }
        activity = null;
        delete.setOnClickListener(null);
        delete = null;
        anim = null;
        recycler.setAdapter(null);
        recycler = null;


    }
}




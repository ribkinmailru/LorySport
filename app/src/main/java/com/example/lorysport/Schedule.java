package com.example.lorysport;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;


public class Schedule extends Fragment implements View.OnClickListener {
    MainActivity2 activity;
    ImageView delete;
    Animation anim;
    RecyclerView recycler;
    Realm realm;



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Realm.init(getContext());
        realm = Realm.getDefaultInstance();
        activity = (MainActivity2) getActivity();

        delete = activity.findViewById(R.id.delete);
        anim = AnimationUtils.loadAnimation(activity,  R.anim.delete);
        delete.setVisibility(View.VISIBLE);
            delete.startAnimation(anim);

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
                ((MainActivity2) getActivity()).addexe = true;
                activity.changefragment(1, null);
            }
        });


        String date = AppManager.downday();
        String dayofweek = AppManager.upday();
        den.setText(dayofweek);
        view3.setText(date);
        getExe();

        final CalendarView calendar = view.findViewById(R.id.calendar);
        calendar.setVisibility(View.GONE);
        calendar.setDate(AppManager.getmillis());


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
                AppManager.calendar.set(year, month, dayOfMonth);
                activity.changefragment(4, null);

            }
        });
        return view;
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton1:
                AppManager.calendar.add(Calendar.DATE, -1);
                activity.changefragment(4, null);
                break;
            case R.id.imageButton:
                AppManager.calendar.add(Calendar.DATE, +1);
                activity.changefragment(4, null);
                break;
        }
    }

    public final void getExe() {
        String date = AppManager.forLong.format(AppManager.calendar.getTime());
        RealmQuery<Train> query = realm.where(Train.class).equalTo("time", date);
        Train s = query.findFirst();
        try {
            RealmList<Exercise> ss = s.getExe();
            ArrayList<Exercise> exes = new ArrayList();
            exes.addAll(ss);
            ExersisesAdapter adapter = new ExersisesAdapter(exes, getContext());
            LinearLayoutManager manage = new LinearLayoutManager(getContext());
            recycler.setLayoutManager(manage);
            recycler.setAdapter(adapter);
        }catch (NullPointerException ignored){}

        }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        anim = AnimationUtils.loadAnimation(activity, R.anim.apeardelete);
        delete.startAnimation(anim);
        delete.setVisibility(View.GONE);
        activity = null;
        delete.setOnClickListener(null);
        delete = null;
        anim = null;
        recycler.setAdapter(null);
        recycler = null;



    }
}




package com.example.lorysport;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class HealthFrag extends Fragment implements View.OnClickListener {
    private Health health;
    private Realm realm;
    private long date;
    private double[] lastparams;
    private long[] lasttime;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.body_exfrag, container, false);
        realm = Realm.getDefaultInstance();
        date = AppManager.getmillis();
        String kg = getString(R.string.kg);
        String sm = getString(R.string.sm);
        String[] name =  getResources().getStringArray(R.array.health);
        String[] paramss = new String[]{kg, sm, sm,
                sm, sm, sm, sm};
        getparams();
        getlast(name.length);
        final RealmList<Double> params;
        if (health != null) {
            params = health.getParams();
        } else {
            params = new RealmList<>();
            for (int x = 0; x < 12; x++) {
                params.add(0.0);
            }
        }
        BodyAdapter adapter = new BodyAdapter(params, lastparams, name, paramss,getContext());
        RecyclerView recycler = view.findViewById(R.id.reccs);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(manager);
        recycler.setHasFixedSize(true);
        recycler.setAdapter(adapter);
        adapter.setListener1(new BodyAdapter.Listener1() {
            @Override
            public void onClick1(int position, EditText edit, TextView text) {
                realm.beginTransaction();
                double param = 0.0;
                if (!edit.getText().toString().equals("")) {
                    param = Double.parseDouble(edit.getText().toString());
                    text.setVisibility(View.VISIBLE);
                } else {
                    text.setVisibility(View.GONE);
                }
                try {
                    RealmList<Double> params = health.getParams();
                    params.set(position, param);
                } catch (NullPointerException ignored) {
                }
                if (health == null) {
                    health = new Health(date);
                    health.setParams(params);
                }
                realm.copyToRealmOrUpdate(health);
                realm.commitTransaction();

                String s;
                if (param - lastparams[position] > 0) {

                    text.setTextColor(getResources().getColor(R.color.green));
                    s = "+ " + new DecimalFormat("#0.00").format(param - lastparams[position])
                            + " " + getString(R.string.last1) + " " + AppManager.form.format(param - lastparams[position]);
                } else {
                    text.setTextColor(getResources().getColor(R.color.red));
                    s = new DecimalFormat("#0.00").format(param - lastparams[position])
                            + " " + getString(R.string.last1) + " " + AppManager.form.format(param - lastparams[position]);
                }
                text.setText(s);
            }
            });
        TextView up = view.findViewById(R.id.textView5);
        TextView down = view.findViewById(R.id.textView6);
        String date = AppManager.downday();
        String dayofweek = AppManager.upday();
        up.setText(dayofweek);
        down.setText(date);
        ImageButton next = view.findViewById(R.id.imageButton);
        ImageButton prev = view.findViewById(R.id.imageButton1);
        LinearLayout linees = view.findViewById(R.id.linee);
        next.setOnClickListener(this);
        prev.setOnClickListener(this);

        return view;

    }

    public void getparams() {
        realm.beginTransaction();
        RealmQuery<Health> query = realm.where(Health.class).equalTo("time", date);
        health = query.findFirst();
        realm.commitTransaction();
    }

    public void getlast(int b) {
            realm.beginTransaction();
            RealmResults<Health> query = realm.where(Health.class).between("time", date - 30 * 86400000L, date - 86400000L).findAll();
            query.sort("time");
            lastparams = new double[b];
            lasttime = new long[b];
            for (Health s : query) {
                RealmList<Double> d = s.getParams();
                long l = s.getTime();
                long t = AppManager.getmillis() - l;
                int countdays = 0;
                while (t > 0) {
                    t = t - 86400000L;
                    countdays++;
                }
                for (int i = 0; i < b - 1; i++) {
                    if (d.get(i) != 0.0) {
                        lastparams[i] = d.get(i);
                        lasttime[i] = countdays;
                    }
                }
            }
            realm.commitTransaction();
        }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton1:
                AppManager.calendar.add(Calendar.DATE, -1);
                ((MainActivity2) getActivity()).changefragment(10, null);
                break;
            case R.id.imageButton:
                AppManager.calendar.add(Calendar.DATE, +1);
                ((MainActivity2) getActivity()).changefragment(10, null);
                break;
        }
    }
}

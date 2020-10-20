package com.example.lorysport;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import io.realm.Realm;
import io.realm.RealmQuery;

public class ExesFragment extends Fragment {
    ArrayList<Exercise> exe;
    FirebaseFirestore fire;
    Realm realm;
    public int id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        realm = Realm.getDefaultInstance();
        View view = inflater.inflate(R.layout.exes_fragment, container,false);
        RecyclerView recycler = view.findViewById(R.id.rec_exe);
        LinearLayoutManager linear = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(linear);
        getexe(recycler);

        return view;
    }

    public void getexe(final RecyclerView recyclerView){
        exe = new ArrayList<>();
        fire = FirebaseFirestore.getInstance();
        fire.collection("exe").whereEqualTo("position", 0).whereEqualTo("subposition", 0).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot s : queryDocumentSnapshots) {
                    exe.add(s.toObject(Exercise.class));
                    Log.d("tag", s.toObject(Exercise.class).name);
                }
                final ExersisesAdapter adapter = new ExersisesAdapter(exe,getContext());
                if(MainActivity2.i==3) {
                    adapter.setListener(new ExersisesAdapter.Listener() {
                        @Override
                        public void onClick(LinearLayout frima, int position) {
                            if (frima.getVisibility() == View.GONE) {
                                frima.setVisibility(View.VISIBLE);
                            } else {
                                frima.setVisibility(View.GONE);
                            }
                        }
                    });
                }
                else{
                    adapter.setListener(new ExersisesAdapter.Listener() {
                        @Override
                        public void onClick(LinearLayout frima, int position) {
                            realm.beginTransaction();
                            String date = AppManager.forLong.format(AppManager.calendar.getTime());
                            RealmQuery<Train> query = realm.where(Train.class).equalTo("time", date);
                            Train s = query.findFirst();
                            if(s==null) {
                                s = new Train(date);
                            }
                            s.addexe(exe.get(position));
                            realm.copyToRealmOrUpdate(s);
                            realm.commitTransaction();
                        }
                    });
                }
                recyclerView.setAdapter(adapter);

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}


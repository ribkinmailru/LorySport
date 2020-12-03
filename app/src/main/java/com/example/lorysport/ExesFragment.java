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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import io.realm.Realm;
import io.realm.RealmQuery;

public class ExesFragment extends Fragment {
    ArrayList<Exercise> exe;
    ArrayList<String> references;
    FirebaseFirestore fire;
    Realm realm;
    public int id;
    private boolean addexe;

    public ExesFragment(boolean addexe){
        this.addexe = addexe;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        realm = Realm.getDefaultInstance();
        View view = inflater.inflate(R.layout.exes_fragment, container,false);
        RecyclerView recycler = view.findViewById(R.id.rec_exe);
        references = new ArrayList<>();
        LinearLayoutManager linear = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(linear);
        getexe(recycler);

        return view;
    }

    public void getexe(final RecyclerView recyclerView){
        exe = new ArrayList<>();
        fire = FirebaseFirestore.getInstance();
        fire.collection("exe").whereEqualTo("position", AppManager.position).whereEqualTo("subposition", AppManager.subposition).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot s : queryDocumentSnapshots.getDocuments()) {
                    references.add(s.getReference().getPath());
                    exe.add(s.toObject(Exercise.class));
                    Log.d("tag", s.toObject(Exercise.class).name);
                }
                final ExersisesAdapter adapter = new ExersisesAdapter(exe,getContext());
                if(!addexe) {
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
                    adapter.setSend(new ExersisesAdapter.Listener() {
                        @Override
                        public void onClick(LinearLayout frima, int position) {
                            ((MainActivity2) getActivity()).changefragment(11,references.get(position));
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
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            Fragment fragment = new Schedule();
                            ft.replace(R.id.frame,fragment,"tag");
                            ft.commit();
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


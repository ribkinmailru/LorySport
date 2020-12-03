package com.example.lorysport;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class MessageFragment extends Fragment {
    private ArrayList<String> numbers;
    private ArrayList<User> users;
    private ArrayList<Message> messages;
    ValueEventListener listener;
    ContactAdapter adapter;

    public MessageFragment(ArrayList<String> numbers){
        this.numbers = numbers;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message,container,false);
        RecyclerView recycler = view.findViewById(R.id.message_rec);
        LinearLayoutManager linear = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(linear);
        users = new ArrayList<>();
        messages = new ArrayList<>();
        findusers(recycler);


        return view;
    }


    private void findusers(final RecyclerView recyclerView){
        FirebaseFirestore.getInstance().collection("users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot s : queryDocumentSnapshots) {
                        for (String r : numbers) {
                            if (r.equals(s.toObject(User.class).getNumber())) {
                                users.add(s.toObject(User.class));
                            }
                    }
                }
                adapter = new ContactAdapter(users,true,messages,getContext());
                adapter.setListener(new ContactAdapter.Listener() {
                    @Override
                    public void onClick(User user, int position) {

                    }
                });
                recyclerView.setAdapter(adapter);

            }
        });
    }

}
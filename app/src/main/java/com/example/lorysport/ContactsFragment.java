package com.example.lorysport;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class ContactsFragment extends Fragment {
    ArrayList<String> contacts;
    FirebaseFirestore ref;
    ArrayList<User> contactss;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        MainActivity2 activity = ((MainActivity2) getActivity());
        ImageView search = activity.findViewById(R.id.search);
        final EditText search_field = activity.findViewById(R.id.search_fiead);
        search.setVisibility(View.VISIBLE);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_field.setVisibility(View.VISIBLE);

            }
        });
        contacts = MainActivity2.user.getContacts();
        contactss = new ArrayList<>();
        ref = FirebaseFirestore.getInstance();
        for (String s : contacts){
            ref.collection("users").whereEqualTo("number", s).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for (QueryDocumentSnapshot s : queryDocumentSnapshots) {
                        contactss.add(s.toObject(User.class));
                        Log.d("contacts", contactss.get(0).getNumber());

                    }
                }
            });
            }
        RecyclerView recycler = view.findViewById(R.id.contacts);
        final ContactAdapter adapter = new ContactAdapter(contactss);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(adapter);
        search_field.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(search_field.getText().toString().length()>5) {
                    contactss = new ArrayList<>();
                    ref.collection("users").whereGreaterThanOrEqualTo("number", search_field.getText().toString()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (QueryDocumentSnapshot s : queryDocumentSnapshots) {
                                contactss.add(s.toObject(User.class));
                            }
                            adapter.changedata(contactss);
                        }
                    });
                }
            }
        });




            return view;


    }
}
package com.example.lorysport;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class ContactsFragment extends Fragment {
    private ArrayList<String> contacts;
    private FirebaseFirestore ref;
    private ArrayList<User> contactss;
    private ContactAdapter adapter;
    private String refForExersize;

    public ContactsFragment(){
    }
    public ContactsFragment(String refForExersize){
        this.refForExersize = refForExersize;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        final MainActivity2 activity = ((MainActivity2) getActivity());
        final ImageView search = activity.findViewById(R.id.search);
        final EditText search_field = activity.findViewById(R.id.search_fiead);
        if (refForExersize == null) {
            search.setVisibility(View.VISIBLE);
            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    search_field.setVisibility(View.VISIBLE);
                    activity.findViewById(R.id.textView3).setVisibility(View.GONE);
                }
            });
        }else{
            search.setVisibility(View.GONE);
        }
        contacts = AppManager.user.getContacts();
        contactss = new ArrayList<>();
        ref = FirebaseFirestore.getInstance();
        final RecyclerView recycler = view.findViewById(R.id.contacts);
        if(contacts!=null) {
            ref.collection("users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for (QueryDocumentSnapshot s : queryDocumentSnapshots) {
                        for (String r : contacts) {
                            if (r.equals(s.toObject(User.class).getNumber())) {
                                contactss.add(s.toObject(User.class));
                            }
                        }
                    }
                    adapter = new ContactAdapter(contactss, getActivity(), refForExersize);
                    recycler.setAdapter(adapter);
                    adapter.setLayout(new ContactAdapter.Layout() {
                        @Override
                        public void onClick(User user) {
                            AppManager.contact = user;
                            if(refForExersize==null) {
                                activity.changefragment(7, null);
                            }else{
                                activity.changefragment(7, refForExersize);
                            }

                        }
                    });
                }
            });
        }
        if(contacts==null){
            TextView text = view.findViewById(R.id.contactisempty);
            text.setVisibility(View.VISIBLE);
        }


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(layoutManager);

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
                    final ArrayList<User> contactsss = new ArrayList<>();
                    ref.collection("users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (QueryDocumentSnapshot s : queryDocumentSnapshots) {
                                User user = s.toObject(User.class);
                                boolean back = false;
                                if(AppManager.user.contacts!=null && !AppManager.user.contacts.contains(user.number)){
                                    back = true;
                                }else if(AppManager.user.contacts == null){
                                    back = true;
                                }
                                if(user.number.contains(search_field.getText().toString()) && !user.number.equals(AppManager.user.number) &&
                                back) {
                                    contactsss.add(user);
                                }
                            }
                            if(adapter==null){
                                adapter = new ContactAdapter(contactsss, getContext(),refForExersize);
                                adapter.exist = true;
                                recycler.setAdapter(adapter);
                            }else {
                                adapter.changedata(contactsss);
                            }
                            adapter.setListener(new ContactAdapter.Listener() {
                                @Override
                                public void onClick(User user, int position) {
                                    AppManager.user.addcontact(user.number);
                                    ref.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).update("contacts", AppManager.user.contacts);
                                    adapter.users.remove(position);
                                    adapter.users.trimToSize();
                                    adapter.notifyItemRemoved(position);
                                }
                            });
                        }
                    });
                }else if(search_field.getText().toString().length()==0){
                    search_field.setVisibility(View.GONE);
                    activity.findViewById(R.id.textView3).setVisibility(View.VISIBLE);
                }
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().findViewById(R.id.search).setVisibility(View.GONE);
        getActivity().findViewById(R.id.search_fiead).setVisibility(View.GONE);
        getActivity().findViewById(R.id.textView3).setVisibility(View.VISIBLE);
    }


}
package com.example.lorysport;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class PersonalMessageFragment extends Fragment {
    private FirebaseDatabase date;
    public MessageAdapter adapter;
    ArrayList<Message> messages;
    private Chat last;
    DatabaseReference key;
    public boolean messager;
    private ValueEventListener listener;
    private String ref;
    private CustomEditText messag;

    public PersonalMessageFragment(String ref){
        this.ref = ref;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_message, container, false);
        date = FirebaseDatabase.getInstance();
        messages = new ArrayList<>();
        final RecyclerView recycler = view.findViewById(R.id.messagelist);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setStackFromEnd(true);

        recycler.setLayoutManager(manager);
        messag = view.findViewById(R.id.message);
        messag.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER){
                    AppManager.hide(messag,getContext());
                    messag.clearFocus();
                    return true;
                }else if(keyCode == KeyEvent.KEYCODE_BACK) {
                    messag.clearFocus();
                    AppManager.hide(messag, getContext());
                    return true;
                }
                return false;
            }
        });
        final ImageView send = view.findViewById(R.id.send);
        ImageView sendphoto = view.findViewById(R.id.sendphoto);
        sendphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImage();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = messag.getText().toString().trim();
                if(s.length()>0) {
                    sendmessage(AppManager.contact.getNumber(), s, messag,AppManager.contact, recycler,false,false);
                }



            }
        });
        getActivity().findViewById(R.id.cont).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.notcont).setVisibility(View.GONE);
        setDatas(AppManager.contact);
        findmessages(recycler, AppManager.contact);
        AppManager.persononline(AppManager.contact.getNumber(),(TextView)getActivity().findViewById(R.id.status),getContext());
        return view;
    }


    public void findmessages(final RecyclerView recyclerView, final User user){
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d: dataSnapshot.getChildren()){
                    if(messager) {
                        adapter.updatemessage(d.getValue(Message.class));
                        recyclerView.smoothScrollToPosition(adapter.messages.size());
                    }
                }
                messager = true;

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        date.getReference().child("chats").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot s : dataSnapshot.getChildren()) {
                    Chat chat = s.getValue(Chat.class);
                    try {
                        if (chat.c1.equals(user.number) && chat.c2.equals(AppManager.user.number)
                                || chat.c1.equals(AppManager.user.number) && chat.c2.equals(user.number)) {
                            for (DataSnapshot b : s.child("message").getChildren()) {
                                Message r = b.getValue(Message.class);
                                messages.add(r);
                            }
                            adapter = new MessageAdapter(messages);
                            recyclerView.setAdapter(adapter);
                            last = chat;
                            key = s.getRef();
                        }
                    } catch (NullPointerException ignored){}
                }
                if(key!=null) {
                    key.child("message").orderByKey().limitToLast(1).addValueEventListener(listener);

                }
                if(ref!=null){
                    sendmessage(AppManager.contact.getNumber(), ref, messag,AppManager.contact, recyclerView,false,true);
                }

        }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });}



    private void sendmessage(String phone, String message, CustomEditText messs, final User user, final RecyclerView recyclerView,
                             boolean photo, boolean exersize){
        Message mess = new Message(AppManager.user.number,AppManager.contact.number, System.currentTimeMillis(),message, photo, exersize);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("chats");
        if(key!=null) {
            key.child("message").push().setValue(mess);
        }else{
            Log.d("someusername","Be bu");
            last = new Chat(AppManager.user.number,phone);
            key = ref.push().getRef();
            (key).setValue(last);
            (key).child("message").push().setValue(mess).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    findmessages(recyclerView, user);
                }
            });
        }
        messs.setText("");
    }

    private void setDatas(User user){
        if(user.avatar!=null){
            Picasso.get().load(user.avatar).into((CircleImageView) getActivity().findViewById(R.id.contact));
        }else{
            Uri uri = Uri.parse("android.resource://com.example.lorysport/" + R.drawable.user);
            Picasso.get().load(uri).into((CircleImageView) getActivity().findViewById(R.id.contact));
        }
        TextView name = getActivity().findViewById(R.id.personname);
        name.setText(user.name);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        messag = null;
        getActivity().findViewById(R.id.cont).setVisibility(View.GONE);
        getActivity().findViewById(R.id.notcont).setVisibility(View.VISIBLE);
        if(key!=null) {
            key.child("message").orderByKey().limitToLast(1).removeEventListener(listener);
        }
    }

    private void setReaded(DataSnapshot b, Message message, User user){
        if(message.author.equals(user.number)) {
            HashMap<String, Object> mm = new HashMap<>();
            mm.put("readed", true);
            b.getRef().updateChildren(mm);
        }
    }

    private void getImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && data.getData()!=null){
            Uri uri = data.getData();
            Fragment fragment = new ShowPhotoFragment(uri.toString(), true, key);
            ((MainActivity2)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.frame,fragment,"tag").commit();
        }
    }
}
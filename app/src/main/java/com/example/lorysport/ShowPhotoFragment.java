package com.example.lorysport;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class ShowPhotoFragment extends Fragment {
    private String uri;
    private boolean send;
    private DatabaseReference key;

    public ShowPhotoFragment(String uri,boolean send,DatabaseReference key){
        this.uri=uri;
        this.send = send;
        this.key = key;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_show_photo,container,false);
        final ImageView image = view.findViewById(R.id.photo);
        Picasso.get().load(uri).into(image);
        if(send){
            FloatingActionButton ok = view.findViewById(R.id.sendphoto);
            ok.setVisibility(View.VISIBLE);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StorageReference mStorage = FirebaseStorage.getInstance().getReference();
                    String currenttime = Long.toString(System.currentTimeMillis());
                    final StorageReference mRef  = mStorage.child("messages").child(AppManager.user.getNumber())
                            .child(AppManager.contact.getNumber()).child(currenttime);
                    Bitmap bd = ((BitmapDrawable) image.getDrawable()).getBitmap();
                    ByteArrayOutputStream bs = new ByteArrayOutputStream();
                    bd.compress(Bitmap.CompressFormat.JPEG,100,bs);
                    UploadTask up = mRef.putBytes(bs.toByteArray());
                    Task<Uri> task = up.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            return mRef.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            Message mess = new Message(AppManager.user.number, AppManager.contact.number, System.currentTimeMillis(),
                                    task.getResult().toString(), true,false);
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("chats");
                            if (key != null) {
                                key.child("message").push().setValue(mess);
                            } else {
                                Log.d("someusername", "Be bu");
                                Chat last = new Chat(AppManager.user.number, AppManager.contact.number);
                                key = ref.push().getRef();
                                (key).setValue(last);
                                (key).child("message").push().setValue(mess);
                            }
                        }
                });
            }
            });
        }
        return view;
    }
}
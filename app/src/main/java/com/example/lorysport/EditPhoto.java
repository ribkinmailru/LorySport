package com.example.lorysport;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class EditPhoto extends Fragment {
    CropImageView views;
    private FirebaseAuth mAuth;
    private StorageReference mStorage;
    private Uri uri;
    private boolean avatar;

    public EditPhoto(Uri uri, boolean avatar){
        this.uri = uri;
        this.avatar=avatar;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        (getActivity()).findViewById(R.id.toolbar).setVisibility(View.GONE);
        View view = inflater.inflate(R.layout.activity_edit_photo, container,false);
        views = view.findViewById(R.id.ima);
        mAuth = FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference();
        views.setImageUriAsync(uri);
        if(avatar) {
            views.setCropShape(CropImageView.CropShape.OVAL);
            views.setAspectRatio(1, 1);
            views.setGuidelines(CropImageView.Guidelines.ON_TOUCH);
            views.setScaleType(CropImageView.ScaleType.FIT_CENTER);
            FloatingActionButton flo = view.findViewById(R.id.ggh);
            flo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bitmap bd = views.getCroppedImage();
                    AppManager.avaarray = bd;
                    AppManager.user.avatar = null;
                    ByteArrayOutputStream bs = new ByteArrayOutputStream();
                    bd.compress(Bitmap.CompressFormat.JPEG, 100, bs);
                    uploadimage(bs.toByteArray());

                }
            });
        }else{
            views.setCropShape(CropImageView.CropShape.RECTANGLE);
            views.setAspectRatio(1,1);
            views.setGuidelines(CropImageView.Guidelines.ON_TOUCH);
            views.setScaleType(CropImageView.ScaleType.FIT_CENTER);
            FloatingActionButton flo = view.findViewById(R.id.ggh);
            flo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bitmap bd = views.getCroppedImage();
                    ByteArrayOutputStream bs = new ByteArrayOutputStream();
                    bd.compress(Bitmap.CompressFormat.JPEG,100,bs);
                    uploadimage(bs.toByteArray());
                }
            });
        }
        return view;
    }

    private void uploadimage(byte[] bits){
        String currenttime = Long.toString(System.currentTimeMillis());
        final StorageReference mRef  = mStorage.child("images").child(AppManager.user.getNumber()).child(currenttime);
        UploadTask up = mRef.putBytes(bits);
        Task<Uri> task = up.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return mRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                FirebaseFirestore fire = FirebaseFirestore.getInstance();
                if (avatar) {
                    fire.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).update("avatar", task.getResult().toString())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }else{
                    if(AppManager.user.photos==null){
                        AppManager.user.photos = new ArrayList<>();
                    }
                    AppManager.user.photos.add(task.getResult().toString());
                    fire.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).update("photos", AppManager.user.photos)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }
            }


        });
        ((MainActivity2) getActivity()).changefragment(6, null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        (getActivity()).findViewById(R.id.toolbar).setVisibility(View.VISIBLE);
    }
}
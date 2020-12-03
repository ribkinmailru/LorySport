package com.example.lorysport;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;
import static android.app.Activity.RESULT_OK;


public class MainActivity extends Fragment implements View.OnKeyListener,View.OnFocusChangeListener,View.OnClickListener {
    StorageReference mStorage;
    CircleImageView avatar;
    private FirebaseFirestore mFire;
    private CustomEditText name;
    public static final String PERMISSION_STRING
            = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String PERMISSION_STRING_READ
            = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private boolean avatars;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        mStorage = FirebaseStorage.getInstance().getReference();
        mFire = FirebaseFirestore.getInstance();
        TextView title = getActivity().findViewById(R.id.textView3);

        title.setText(R.string.profile);
        View view = inflater.inflate(R.layout.activity_main, container, false);
        avatar = view.findViewById(R.id.hhhh);
        loadavatar();
        final CustomEditText status = view.findViewById(R.id.status);
        status.setText(AppManager.user.status);
        status.setText(getResources().getString(R.string.status));
        status.setOnFocusChangeListener(this);

        name = view.findViewById(R.id.pname);
        name.setText(AppManager.user.name);
        name.setOnFocusChangeListener(this);


        if(ActivityCompat.checkSelfPermission(getActivity(), PERMISSION_STRING) ==
                PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getActivity(), PERMISSION_STRING_READ) == PackageManager.PERMISSION_GRANTED){

        }else{
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }


        name.setOnKeyListener(this);
        status.setOnKeyListener(this);

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatars = true;
                getImage();
            }
        });
        Button addphoto = view.findViewById(R.id.addphoto);
        addphoto.setOnClickListener(this);
        if(AppManager.user.photos!=null) {
            LinearLayoutManager linear = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,false);
            RecyclerView recycler = view.findViewById(R.id.photos);
            recycler.setLayoutManager(linear);
            PhotosAdapter photos = new PhotosAdapter(AppManager.user.photos,getContext());
            photos.setAdd(new PhotosAdapter.Listeneradd() {
                @Override
                public void onClick() {
                    avatars = false;
                    getImage();
                }
            });
            photos.setWatch(new PhotosAdapter.Listenerwatch() {
                @Override
                public void onClick() {
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    Fragment frag = new WatchPhoto(AppManager.user.photos);
                    ft.replace(R.id.frame,frag);
                    ft.commit();
                }
            });
            recycler.setAdapter(photos);
        }else{

        }
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        avatar = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && data!=null && data.getData()!=null && resultCode == RESULT_OK ){
            Uri uri = data.getData();
            ((MainActivity2)getActivity()).editphoto(uri,avatars);
        }
    }


    private void getImage(){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 1);
    }

    private void loadavatar(){
        if(AppManager.user.avatar!=null) {
            Picasso.get().load(AppManager.user.avatar).into(avatar);
        }else if (AppManager.avaarray!=null) {
        avatar.setImageBitmap(AppManager.avaarray);
        }else
        {
            avatar.setImageDrawable(getResources().getDrawable(R.drawable.user));
        }
    }


    private void changename(String name){
        mFire.collection("users").document(AppManager.user.number).update("name", name);
    }
    private void changestatus(String status){
        mFire.collection("users").document(AppManager.user.number).update("status", status);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            v.clearFocus();
            return true;

        }
        else if (keyCode == KeyEvent.KEYCODE_ENTER) {
            AppManager.hide(v, getContext());
            v.clearFocus();
            return true;
        }

        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        CustomEditText cast = (CustomEditText)v;
        if(hasFocus){
            cast.setCursorVisible(true);
            cast.setBackground(getResources().getDrawable(R.drawable.nameback));
        }else{
            cast.setCursorVisible(false);
            cast.setBackground(getResources().getDrawable(R.color.white));
        }
        if(v.getId()==R.id.name){
            changename(AppManager.firstUpperCase(cast.getText().toString()));
        }else{
            changestatus(AppManager.firstUpperCase(cast.getText().toString()));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addphoto:
            avatars = false;
            getImage();
            break;
        }
    }
}
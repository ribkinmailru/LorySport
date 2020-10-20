package com.example.lorysport;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class MainActivity extends Fragment {
    StorageReference mStorage;
    CropImageView cookie;
    LinearLayout nast;
    Uri uri;
    ImageView avatar;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        mStorage = FirebaseStorage.getInstance().getReference().child(FirebaseAuth.getInstance().getUid());
        TextView title = getActivity().findViewById(R.id.textView3);
        title.setText(R.string.profile);
        View view = inflater.inflate(R.layout.activity_main, container, false);
        avatar = view.findViewById(R.id.hhhh);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImage();
            }
        });

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
            uri = data.getData();
            Intent intent = new Intent(getActivity(), EditPhoto.class);
            intent.putExtra("uri", uri);
            startActivityForResult(intent,2);
        }
        if(requestCode == 2){
            byte[] asd = data.getByteArrayExtra("bitmap");
            Bitmap bits = BitmapFactory.decodeByteArray(asd,0,asd.length);
            avatar.setImageBitmap(bits);

        }
    }

    private void getImage(){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 1);
    }

    private void uploadimage(String image){
        Bitmap bitmap = ((BitmapDrawable) avatar.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] byteArray = baos.toByteArray();
        final StorageReference mRef  = mStorage.child(image);
        UploadTask up = mRef.putBytes(byteArray);
        Task<Uri> task = up.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return null;
            }
        });
    }

}
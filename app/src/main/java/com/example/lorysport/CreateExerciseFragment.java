package com.example.lorysport;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import de.hdodenhof.circleimageview.CircleImageView;
import static android.app.Activity.RESULT_OK;
public class CreateExerciseFragment extends Fragment {

    private CircleImageView imageView;
    private final int Pick_image = 1;
    FloatingActionButton but;
    FloatingActionButton no;
    final String pathtosave = Environment.getExternalStorageDirectory().toString();
    EditText name;
    EditText description;
    MainActivity2 activity;
    View view;
    int custom;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.createfragment, container, false);
        but = view.findViewById(R.id.button);
        no = view.findViewById(R.id.buttons);
        imageView = view.findViewById(R.id.imageView2);
        name = view.findViewById(R.id.editTextTextPersonName);
        description = view.findViewById(R.id.editTextTextPersonName2);
        activity = ((MainActivity2) getActivity());
        custom = activity.custom;
        if (custom == 2) {
            but.setColorFilter(Color.argb(255, 255, 255, 255));
            no.setColorFilter(Color.argb(255, 255, 255, 255));
        }
        if (custom == 3) {
            but.setColorFilter(Color.argb(255, 255, 255, 255));
            no.setColorFilter(Color.argb(255, 255, 255, 255));
        }


        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavePicture(imageView, pathtosave);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photo = new Intent(Intent.ACTION_PICK);
                photo.setType("image/*");
                startActivityForResult(photo, Pick_image);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        if (requestCode == Pick_image) {
            if (resultCode == RESULT_OK) {
                try {
                    final Uri imageUri = imageReturnedIntent.getData();
                    final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    imageView.setImageBitmap(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
    }


    private void SavePicture(ImageView iv, String folderToSave) {
        OutputStream fOut = null;


    }
}

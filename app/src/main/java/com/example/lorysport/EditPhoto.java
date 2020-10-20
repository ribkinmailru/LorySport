package com.example.lorysport;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;

public class EditPhoto extends AppCompatActivity {
    CropImageView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_photo);
        Bundle intent = getIntent().getExtras();
        Uri uri = (Uri) intent.get("uri");
        view = findViewById(R.id.ima);
        view.setImageUriAsync(uri);
        view.setCropShape(CropImageView.CropShape.OVAL);
        view.setAspectRatio(1,1);
        view.setGuidelines(CropImageView.Guidelines.ON_TOUCH);
        view.setScaleType(CropImageView.ScaleType.FIT_CENTER);
        FloatingActionButton flo = findViewById(R.id.ggh);
        flo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iData = new Intent();
                Bitmap bd = view.getCroppedImage();
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                bd.compress(Bitmap.CompressFormat.JPEG,100,bs);
                iData.putExtra("bitmap", bs.toByteArray());
                setResult(Activity.RESULT_OK, iData);
                finish();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
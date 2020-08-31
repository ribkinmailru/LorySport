package com.example.lorysport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, MainActivity2.class);

        SQLiteOpenHelper exe = new ExeDatabase(this);
        SQLiteDatabase dbe = exe.getReadableDatabase();
        SQLiteOpenHelper body = new BodyDatabase(this);
        SQLiteDatabase dbb = body.getReadableDatabase();
        SQLiteOpenHelper date = new DateDatabase(this);
        SQLiteDatabase dbd = date.getReadableDatabase();
        SQLiteOpenHelper Notif = new NotifDatabase(this);
        SQLiteDatabase dbn = Notif.getReadableDatabase();
        dbe.close();
        dbb.close();
        dbd.close();
        dbn.close();

        startActivity(intent);
        finish();
    }
}
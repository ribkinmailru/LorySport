package com.example.lorysport;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BodyDatabase extends SQLiteOpenHelper {
    private static final String DB_NAME = "Body";
    private static final int DB_VERSION = 1;

    BodyDatabase(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }


    public void onCreate(SQLiteDatabase db) {
        updateMydatabase(db, 0, DB_VERSION);
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        updateMydatabase(db, oldVersion, newVersion);
    }


    public void updateMydatabase (SQLiteDatabase db, int oldVersion, int newVersion){
        if(oldVersion<1){
            db.execSQL("CREATE TABLE BODY (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    +"COUNT TEXT, "
                    +"MILLIS INTEGER, "
                    +"WHAT INTEGER, "
                    +"POSITION INTEGER);");

        }
    }
}


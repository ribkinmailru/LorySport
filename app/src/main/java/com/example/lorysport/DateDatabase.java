package com.example.lorysport;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DateDatabase extends SQLiteOpenHelper {
    private static final String DB_NAME = "Date";
    private static final int DB_VERSION = 1;

    DateDatabase(Context context){
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
            db.execSQL("CREATE TABLE DATES(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    +"DATE INTEGER, "
                    +"EXEID INTEGER,"
            +"MILLIS INTEGER);");

        }
    }
}

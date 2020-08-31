package com.example.lorysport;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

 public class NotifDatabase extends  SQLiteOpenHelper{
        private static final String DB_NAME = "Notif";
        private static final int DB_VERSION = 1;

    public NotifDatabase(Context context){
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
                db.execSQL("CREATE TABLE NOTIF(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        +"DATE TEXT, "
                        +"MILLIS INTEGER,"
                        +"NAME TEXT);");

            }
        }
    }


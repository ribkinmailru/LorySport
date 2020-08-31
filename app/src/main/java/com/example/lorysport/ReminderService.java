package com.example.lorysport;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;

import androidx.annotation.Nullable;

public class ReminderService extends IntentService {

    long c;
    boolean delete;
    int id;



    public ReminderService() {
        super("ReminderService");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        c = intent.getLongExtra("tag",0);
        id = intent.getIntExtra("ids", 0);
        delete = intent.getBooleanExtra("del", false);
        AlarmManager alarmManager =(AlarmManager) getSystemService(Context.ALARM_SERVICE);

        if (!delete){
            Intent intent1 = new Intent(this, ReminderBroadcast.class);
            intent1.putExtra("tag", c);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), id, intent1, 0);
            alarmManager.set(AlarmManager.RTC_WAKEUP, c, pendingIntent);

        }else{
                Intent intent1 = new Intent(this, ReminderBroadcast.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), id, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

                pendingIntent.cancel();

        }
    }
}
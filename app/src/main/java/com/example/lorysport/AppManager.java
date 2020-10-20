package com.example.lorysport;

import android.graphics.Bitmap;

import java.text.ChoiceFormat;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class AppManager {
    private AppManager instance;
    public static GregorianCalendar calendar = new GregorianCalendar();
    public static DateFormat dateFormate = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
    public static DateFormat forLong = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
    private AppManager(){

    }

    public AppManager getInstance(){
        if(instance==null){
            instance = new AppManager();
        }
        return instance;
    }


    public static String upday() {
        DateFormat day = new SimpleDateFormat("EEEE", Locale.getDefault());
        return day.format(calendar.getTime()).toUpperCase();
    }

    public static String downday() {
        return dateFormate.format(calendar.getTime()).toUpperCase();
    }

    public static String notUpperCase() {
        return dateFormate.format(calendar.getTime());
    }

    public static long getmillis(){
        String milli = forLong.format(calendar.getTime());
        Date dater = null;
        try {
            dater = forLong.parse(milli);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert dater != null;
        return dater.getTime();
    }

       public static ChoiceFormat form = new ChoiceFormat("1#день |1<дня |4<дней");
       public static ChoiceFormat last = new ChoiceFormat("1#за последний |1<за последние");

}

package com.example.lorysport;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.multidex.MultiDexApplication;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.ChoiceFormat;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class AppManager extends MultiDexApplication {
    private AppManager instance;
    public static GregorianCalendar calendar = new GregorianCalendar();
    public static DateFormat dateFormate = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
    public static DateFormat forLong = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
    public static User user;
    public static User contact;
    public static int position;
    public static int subposition;
    public static Bitmap avaarray;
    public static int newmessage;

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

    public static String firstUpperCase(String word){
        if(word == null || word.isEmpty()) return ""; //или return word;
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    public static String downday() {
        return dateFormate.format(calendar.getTime()).toUpperCase();
    }
    public static String formessage(Date date){
        DateFormat formessage = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return formessage.format(date);
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
       public static void hide (View view, Context ctx){
           InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
           imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
       }

       public static void online(){
           FirebaseDatabase date = FirebaseDatabase.getInstance();
           date.getReference("online").push().setValue(user.getNumber());

       }
       public static void offline(){
           FirebaseDatabase date = FirebaseDatabase.getInstance();
           date.getReference("online").orderByValue().equalTo(user.getNumber()).getRef().removeValue();
       }

       public static void persononline(String phone, final TextView status, final Context ctx){
           FirebaseDatabase date = FirebaseDatabase.getInstance();
           date.getReference("online").orderByValue().equalTo(phone).addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   if (dataSnapshot.getValue()!=null) {
                       status.setText(R.string.online);
                       status.setTextColor(ctx.getResources().getColor(R.color.green));
                   }
                   else{
                       status.setText(R.string.offline);
                       status.setTextColor(ctx.getResources().getColor(R.color.red));
                   }
               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
           });
       }
}

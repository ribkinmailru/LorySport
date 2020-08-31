package com.example.lorysport;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.navigation.NavigationView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.customview.widget.ViewDragHelper;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MainActivity2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    int i = 1;
    int custom;
    SharedPreferences sPref;
    FragmentTransaction ft;
    int ch;
    final GregorianCalendar calendar = new GregorianCalendar();
    final DateFormat dateFormate = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
    final DateFormat forLong = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
    ImageView delete;
    ImageView add;
    int position;
    int subposition;
    int counttodel;
    DrawerLayout drawer;
    ActionBarDrawerToggle togle;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);
        TextView textview = findViewById(R.id.textView3);
        Toolbar toolbar = findViewById(R.id.toolbar);
        delete = findViewById(R.id.delete);
        delete.setVisibility(View.GONE);
        add = findViewById(R.id.add);
        add.setVisibility(View.GONE);

        setSupportActionBar(toolbar);
        if (savedInstanceState != null) {
            custom = savedInstanceState.getInt("custom");
        }
        sPref = getPreferences(MODE_PRIVATE);
        custom = sPref.getInt("custom", 1);
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        togle = new ActionBarDrawerToggle(this,
                drawer, toolbar, R.string.app_name, R.string.app_name);
        drawer.addDrawerListener(togle);

        Field mDragger = null;
        try {
            mDragger = drawer.getClass().getDeclaredField(
                    "mLeftDragger");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        mDragger.setAccessible(true);
        ViewDragHelper draggerObj = null;
        try {
            draggerObj = (ViewDragHelper) mDragger
                    .get(drawer);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Field mEdgeSize = null;
        try {
            mEdgeSize = draggerObj.getClass().getDeclaredField(
                    "mEdgeSize");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        mEdgeSize.setAccessible(true);
        int edge = 0;
        try {
            edge = mEdgeSize.getInt(draggerObj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            mEdgeSize.setInt(draggerObj, edge * 5);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        togle.syncState();
        togle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.blue));

        if (custom == 3) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
            togle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.pink));
            setTheme(R.style.custom3);
        }
        if (custom == 2) {
            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.toolbar));
            textview.setTextColor(getResources().getColor(R.color.white));
            togle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
            ImageView delete = findViewById(R.id.delete);
            delete.setColorFilter(getResources().getColor(R.color.white));
            setTheme(R.style.custom2);
        }

        NavigationView nav = findViewById(R.id.nav_view);
        nav.setNavigationItemSelectedListener(this);
        ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new MainActivity();
        ft.add(R.id.frame, fragment);
        ft.commit();
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        DrawerLayout drawer =  findViewById(R.id.drawer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        int id = item.getItemId();
        switch (id) {
            case R.id.profile:
                fragment = new MainActivity();
                i = 6;
                break;
            case R.id.mytrains:
                fragment = new ExeandTrains();
                i = 2;
                break;
            case R.id.exersizes:
                fragment = new tablayoutfargment();
                i = 3;
                break;
            case R.id.schedule:
                fragment = new Schedule();
                i = 4;
                break;
            case R.id.params:
                fragment = new BodyFragment(1);
                i = 5;
                break;
            case R.id.healthy:
                fragment = new BodyFragment(0);
                i = 0;
                break;
            case R.id.run:
                Intent intent = new Intent(this, odometrFragment.class);
                startActivity(intent);
                i = 10;
                break;
            case R.id.stats:
                fragment = new NotificationFragment();

                i = 7;
                break;
            case R.id.setting:
                fragment = new SettingFragment();
                i = 8;
                break;

        }

            if(i!=4) {
                counttodel = 0;
            }
            if(item.getItemId()!=ch & i!=10) {
                    ft = getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.animator.sheduleleft, R.animator.slideinright);
                ft.replace(R.id.frame, fragment, "tag");
                ft.commit();
                ft = null;
            }
        ch = id;
            fragment = null;
        return true;
    }

    public void onBackPressed() {
        Fragment fragment1;
        DrawerLayout drawer = findViewById(R.id.drawer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (i == 6) {
            super.onBackPressed();
        }
        if (i != 0 && getSupportFragmentManager().getBackStackEntryCount()==0) {
            fragment1 = new MainActivity();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame, fragment1, "tag");
            ft.commit();
            i = 6;
            ch = 6;
        }
        if(i==3){
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(getSupportFragmentManager().getBackStackEntryCount()!=0) {
            getSupportFragmentManager().popBackStack();
        }else{

        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("custom", custom);
    }


    protected void onDestroy() {
        super.onDestroy();
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt("custom", custom);
        ed.apply();
    }

    public final String upday() {
        DateFormat day = new SimpleDateFormat("EEEE", Locale.getDefault());
        return day.format(calendar.getTime()).toUpperCase();
    }

    public final String downday() {
        return dateFormate.format(calendar.getTime()).toUpperCase();
    }

    public final String notUpperCase() {
        return dateFormate.format(calendar.getTime());
    }


    public final void changefragment(int t) {
        Fragment fragment1 = null;
        ft = getSupportFragmentManager().beginTransaction();
        if(t==1) {
            fragment1 = new tablayoutfargment();
            ft.setCustomAnimations(R.animator.appdel, R.animator.tabapp);
        }
        if(t==2){
            fragment1 = new SubType();
            ft.addToBackStack("tag");
        }
        if(t==3){
            fragment1 = new addexeFragment();
            ft.addToBackStack("tag");
        }
        if(t==4){
            fragment1 = new Schedule();
        }
        if(t==5){
            fragment1 = new CreateExerciseFragment();
        }
        if(t==6){
            fragment1 = new MainActivity();
        }
        ft.replace(R.id.frame, fragment1, "tag");
        ft.commit();
        ft = null;
    }

    public final long getmillis(){
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


    public final void startnotif(long s, boolean delete, int ids){
        createNotificationChannel();
        Intent intent = new Intent(this, ReminderService.class);
        intent.putExtra("tag", s);
        intent.putExtra("del", delete);
        intent.putExtra("ids", ids);
        startService(intent);
    }


    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name = "LemReminderChanek";
            String description = "Chanel for Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel chanel = new NotificationChannel("notifyLemybit", name, importance);
            chanel.setDescription(description);

            NotificationManager maneger = getSystemService(NotificationManager.class);
            maneger.createNotificationChannel(chanel);
        }
    }

    public void changeBody(int wh){
        Fragment fragment1 = null;
        ft = getSupportFragmentManager().beginTransaction();
        if(wh==1){
            fragment1 = new BodyFragment(1);
        }else{
            fragment1 = new BodyFragment(0);
        }
        ft.replace(R.id.frame, fragment1, "tag");
        ft.commit();
        ft = null;
    }

}




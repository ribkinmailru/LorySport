package com.example.lorysport;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener,AppBarLayout.OnOffsetChangedListener {


    public static int i = 1;
    private boolean listforctats, listofmessages2;
    public boolean addexe;
    int custom;
    FragmentTransaction ft;
    int counttodel;
    private FlowingDrawer mDrawer;
    private ArrayList<String> numbers;
    private Toolbar toolbar;
    private boolean mIsTheTitleVisible          = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView textview = findViewById(R.id.textView3);
        AppBarLayout appbar = findViewById(R.id.bar);
        appbar.addOnOffsetChangedListener(this);
        toolbar = findViewById(R.id.toolbar);
        ImageView search = findViewById(R.id.search);
        ImageView messages = findViewById(R.id.messages);
        messages.setOnClickListener(this);
        search.setVisibility(View.GONE);
        EditText search_field = findViewById(R.id.search_fiead);
        search_field.setVisibility(View.GONE);
        ImageView delete = findViewById(R.id.delete);
        delete.setVisibility(View.GONE);
        ImageView add = findViewById(R.id.add);
        add.setVisibility(View.GONE);
        mDrawer = findViewById(R.id.drawerlayout);
        mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
        setupMenu();
        setSupportActionBar(toolbar);
        numbers = new ArrayList<>();
        findmessage();

        changefragment(6, null);
        search.setVisibility(View.GONE);
        search_field.setVisibility(View.GONE);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

    }

    private void setupMenu() {
        FragmentManager fm = getSupportFragmentManager();
        MenuListFragment mMenuFragment = (MenuListFragment) fm.findFragmentById(R.id.id_container_menu);
        if (mMenuFragment == null) {
            mMenuFragment = new MenuListFragment();
            fm.beginTransaction().add(R.id.id_container_menu, mMenuFragment).commit();
        }
    }

    public void onBackPressed() {
        Fragment fragment1;
        if (mDrawer.isMenuVisible()) {
            mDrawer.closeMenu();
        } if (getSupportFragmentManager().findFragmentById(R.id.frame).getClass().equals(MainActivity.class)) {
            super.onBackPressed();
        } if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            fragment1 = new MainActivity();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame, fragment1, "tag");
            ft.commit();
        } if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
            getSupportFragmentManager().popBackStack();
        }if(getSupportFragmentManager().findFragmentById(R.id.frame).getClass().equals(PersonalMessageFragment.class)){
            PersonalMessageFragment fragment = (PersonalMessageFragment) getSupportFragmentManager().findFragmentById(R.id.frame);
            if(!fragment.getView().findViewById(R.id.message).hasFocus()) {
                getSupportFragmentManager().popBackStack();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
            getSupportFragmentManager().popBackStack();
        } else {

        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        AppManager.online();
    }

    @Override
    protected void onStop() {
        super.onStop();
        AppManager.offline();
    }

    public final void changefragment(int t, String ref) {
        Fragment fragment1 = null;
        ft = getSupportFragmentManager().beginTransaction();
        switch (t) {
            case 1:
                fragment1 = new tablayoutfargment();
                ft.setCustomAnimations(R.animator.appdel, R.animator.tabapp);
                break;
            case 2:
                fragment1 = new SubType();
                ft.addToBackStack("tag");
                break;
            case 3:
                fragment1 = new ExesFragment(addexe);
                ft.addToBackStack("tag");
                break;
            case 4:
                fragment1 = new Schedule();
                break;
            case 5:
                fragment1 = new CreateExerciseFragment();
                break;
            case 6:
                fragment1 = new MainActivity();
                break;
            case 7:
                fragment1 = new PersonalMessageFragment(ref);
                ft.addToBackStack("tag");
                break;
            case 8:
                fragment1 = new MessageFragment(numbers);
                break;
            case 9:
                fragment1 = new BodyFrag();
                break;
            case 10:
                fragment1 = new HealthFrag();
                break;
            case 11:
                fragment1 = new ContactsFragment(ref);
                break;
        }
        ft.replace(R.id.frame, fragment1, "tag");
        ft.commit();
        ft = null;
    }

    public void editphoto(Uri uri, boolean avatar) {
        Fragment fragment1 = new EditPhoto(uri, avatar);
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame, fragment1, "tag");
        ft.commit();
        ft = null;
    }


    public final void startnotif(long s, boolean delete, int ids) {
        createNotificationChannel();
        Intent intent = new Intent(this, ReminderService.class);
        intent.putExtra("tag", s);
        intent.putExtra("del", delete);
        intent.putExtra("ids", ids);
        startService(intent);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "LemReminderChanek";
            String description = "Chanel for Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel chanel = new NotificationChannel("notifyLemybit", name, importance);
            chanel.setDescription(description);

            NotificationManager maneger = getSystemService(NotificationManager.class);
            maneger.createNotificationChannel(chanel);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.messages:
                changefragment(8, null);
                break;
        }
    }

    public void findmessage() {
        final ChildEventListener event = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(listforctats) {
                    HashMap<String, Object> map = (HashMap<String, Object>) dataSnapshot.getValue();
                    boolean readed = (boolean) map.get("readed");
                    String author = (String) map.get("author");
                    Log.d("countmessage", "da");
                    Log.d("countmessage", Boolean.toString(readed));

                    if (!readed && !author.equals(AppManager.user.number)) {
                        AppManager.newmessage++;
                        TextView countpfmessages = findViewById(R.id.countofmessages);
                        countpfmessages.setText(Integer.toString(AppManager.newmessage));
                        Log.d("countmessage", Integer.toString(AppManager.newmessage));
                        makenotif();
                    }
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        FirebaseDatabase.getInstance().getReference().child("chats").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot s : dataSnapshot.getChildren()) {
                    Chat chat = s.getValue(Chat.class);
                    Log.d("tarrr",AppManager.user.number);
                        if (chat.c1.equals(AppManager.user.number) || chat.c2.equals(AppManager.user.number)) {
                            if (chat.c1.equals(AppManager.user.number)) {
                                numbers.add(chat.c2);
                            } else {
                                numbers.add(chat.c1);
                            }
                            s.getRef().child("message").orderByKey().limitToLast(1).addChildEventListener(event);
                        }
                        addlistenerchat();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void addlistenerchat() {
        FirebaseDatabase.getInstance().getReference().child("chats").orderByKey().limitToLast(1).addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Chat chat = dataSnapshot.getValue(Chat.class);
                        if (chat.c1.equals(AppManager.user.number) || chat.c2.equals(AppManager.user.number)) {
                            for (DataSnapshot d : dataSnapshot.child("message").getChildren()) {
                                Message m = d.getValue(Message.class);
                                if (!m.author.equals(AppManager.user.number) && !m.readed) {
                                    AppManager.newmessage++;
                                    TextView countpfmessages = findViewById(R.id.countofmessages);
                                    countpfmessages.setVisibility(View.VISIBLE);
                                    countpfmessages.setText(Integer.toString(AppManager.newmessage));
                                    makenotif();
                                }
                            }

                        }

                }


            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void makenotif(){
        Uri noofivcation = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), noofivcation);
        r.play();
    }

    public void appbaravalable(boolean yes){
        AppBarLayout applayout = findViewById(R.id.bar);
        if(!yes) {
            NestedScrollView scroll = findViewById(R.id.scroll);
            ViewCompat.setNestedScrollingEnabled(scroll, false);
        }else{
            NestedScrollView scroll = findViewById(R.id.scroll);
            ViewCompat.setNestedScrollingEnabled(scroll, true);
        }
        applayout.setExpanded(yes,true);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxscroll = appBarLayout.getTotalScrollRange();
        float percantage = (float)Math.abs(verticalOffset)/(float) maxscroll;
        handleToolbarTitleVisibility(percantage);
    }


    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= 0.7f) {

            if (!mIsTheTitleVisible) {
                startAlphaAnimation(toolbar, 200, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(toolbar, 200, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

        public static void startAlphaAnimation (View v, long duration, int visibility) {
            AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                    ? new AlphaAnimation(0f, 1f)
                    : new AlphaAnimation(1f, 0f);

            alphaAnimation.setDuration(duration);
            alphaAnimation.setFillAfter(true);
            v.startAnimation(alphaAnimation);
        }
}




package com.example.lorysport;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;




public class addexeFragment extends Fragment implements CustomDialogFragment.methods{

    Animation anim;
    String[] nameArray;
    int[] imageArray;
    String[] download;
    boolean[] favorite;
    CaptionedImagesAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.addexe_fragment, container, false);
        final MainActivity2 activity = ((MainActivity2) getActivity());
        assert activity != null;
        final long millis = activity.getmillis();
        final String date = activity.notUpperCase();
        anim = AnimationUtils.loadAnimation(activity, R.anim.delete);
        final int positio = activity.subposition;
        final int positions = activity.position;
        TextView title = activity.findViewById(R.id.textView3);
        title.setText(R.string.exersizes);
        FloatingActionButton fab = view.findViewById(R.id.floatingActionButton);
        fab.setColorFilter(getResources().getColor(R.color.white));
        fab.startAnimation(anim);
        fab.setVisibility(View.VISIBLE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.changefragment(5);
            }
        });
            findall(positio, positions);
        adapter = new CaptionedImagesAdapter(activity.custom, nameArray, imageArray, favorite, download, activity);
        adapter.inputtype = 3;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        final RecyclerView recycler = view.findViewById(R.id.chouse);
        recycler.setLayoutManager(layoutManager);
        recycler.setHasFixedSize(true);
        recycler.setAdapter(adapter);
        if (activity.i != 3) {
            fab.setVisibility(View.GONE);
            adapter.setListener2(new CaptionedImagesAdapter.Listener2() {
                @Override
                public void onClick2(View view, int position, LinearLayout frima) {
                    SQLiteOpenHelper exe = new ExeDatabase(getActivity());
                    SQLiteDatabase db = exe.getReadableDatabase();
                    Cursor cursor = db.query("EXE",
                            new String[]{"_id"},
                            "SUBTYPE=? AND TYPE=?",
                            new String[]{Integer.toString(positio), Integer.toString(positions)},
                            null, null, null, null);
                    cursor.moveToPosition(position);
                    int indificator = cursor.getInt(0);
                    ContentValues content = new ContentValues();
                    SQLiteOpenHelper dates = new DateDatabase(activity);
                    SQLiteDatabase dbs = dates.getWritableDatabase();
                    content.put("DATE", date);
                    content.put("EXEID", indificator);
                    content.put("MILLIS", millis);
                    dbs.insert("DATES", null, content);
                    cursor.close();
                    dbs.close();
                    db.close();
                    activity.changefragment(4);
                }
            });
        } else {
            adapter.setListener2(new CaptionedImagesAdapter.Listener2() {
                @Override
                public void onClick2(View v, int position, LinearLayout frima) {
                    if (frima.getVisibility() == View.VISIBLE) {
                        frima.setVisibility(View.GONE);
                    } else {
                        frima.setVisibility(View.VISIBLE);
                        recycler.smoothScrollToPosition(position + 2);
                    }
                }
            });
        }

        adapter.setListener4(new CaptionedImagesAdapter.Listener4() {
            @Override
            public void onClick4(int position, ImageButton cardView, String name) {
                cardView.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_star_24));
                SQLiteOpenHelper exe = new ExeDatabase(getContext());
                SQLiteDatabase db = exe.getWritableDatabase();
                SQLiteDatabase write = exe.getReadableDatabase();
                Cursor cursor = write.query("EXE",
                        new String[]{"FAVORITE"},
                        "NAME=?",
                        new String[]{name}, null, null, null);
                cursor.moveToFirst();
                boolean f = (cursor.getInt(0) == 1);
                if (!f) {
                    ContentValues content = new ContentValues();
                    content.put("FAVORITE", 1);
                    db.update("EXE", content, "NAME=?", new String[]{name});
                } else {
                    cardView.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_star_border_24));
                    ContentValues content = new ContentValues();
                    content.put("FAVORITE", 0);
                    db.update("EXE", content, "NAME=?", new String[]{name});
                }
                cursor.close();
                db.close();
            }
        });
        adapter.setListener5(new CaptionedImagesAdapter.Listener5() {
            @Override
            public void onClick5(final int position, String bs, View v) {
                if (download[position] == null) {
                    CustomDialogFragment dialog = new CustomDialogFragment(2, bs, position);
                    dialog.show(getFragmentManager(), "show");
                    dialog.setlist(new CustomDialogFragment.methods() {
                        @Override
                        public void onOk(int pose) {
                            findall(positio, positions);
                            adapter.changeDatass(nameArray, imageArray, favorite, download, position);
                        }
                    });
                }

            }
        });
        return view;
    }


    public final void findall(int positio, int positions) {
        Log.d("pos", Integer.toString(positions));
        Log.d("sub", Integer.toString(positio));
        SQLiteOpenHelper exe = new ExeDatabase(getActivity());
        SQLiteDatabase db = exe.getReadableDatabase();
        Cursor cursor = db.query("EXE",
                new String[]{"NAME", "IMAGEID", "DOWNLOAD", "FAVORITE"},
                "SUBTYPE=? AND TYPE=?",
                new String[]{Integer.toString(positio), Integer.toString(positions)},
                null, null, null, null);
        nameArray = new String[cursor.getCount()];
        imageArray = new int[cursor.getCount()];
        download = new String[cursor.getCount()];
        favorite = new boolean[cursor.getCount()];
        int i = 0;
        cursor.moveToFirst();
        do {
            try {
                String name = cursor.getString(0);
                int image = cursor.getInt(1);
                boolean fav = (cursor.getInt(3) == 1);
                String down = cursor.getString(2);
                favorite[i] = fav;
                nameArray[i] = name;
                imageArray[i] = image;
                download[i] = down;
                i++;
            }catch (CursorIndexOutOfBoundsException ignored){}
        } while (cursor.moveToNext());
        cursor.close();
        db.close();
    }

    @Override
    public void onOk(int pos) {
    }

    }



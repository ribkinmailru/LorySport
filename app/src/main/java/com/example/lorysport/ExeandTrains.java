package com.example.lorysport;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

public class ExeandTrains extends Fragment {
    MainActivity2 activity;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_trains,container,false);
        activity = ((MainActivity2) getActivity());
        TextView title = activity.findViewById(R.id.textView3);
        title.setText(R.string.Favorite);

        SQLiteOpenHelper exedb = new ExeDatabase(activity);
        SQLiteDatabase db = exedb.getReadableDatabase();

        Cursor cursor = db.query("EXE",
                new String[]{"NAME", "IMAGEID", "FAVORITE", "_id", "DOWNLOAD"},
                "FAVORITE = ?",
                new String[]{Integer.toString(1)},
                null, null, null);

        int b = cursor.getCount();
        String[] nameArray = new String[b];
        int[] imageArray = new int[b];
        boolean[] isFavorite = new boolean[b];
        int[] idofposition = new int[b];
        String[] download = new String[b];
        int i = 0;
        try {
            cursor.moveToFirst();
            do {
                String name = cursor.getString(0);
                int image = cursor.getInt(1);
                boolean Favorite = (cursor.getInt(2) == 1);
                int iop = cursor.getInt(3);
                String down = cursor.getString(4);
                nameArray[i] = name;
                imageArray[i] = image;
                isFavorite[i] = Favorite;
                idofposition[i] = iop;
                download[i] = down;
                i++;
            } while (cursor.moveToNext());
        } catch (Exception ignored) {
        }

        final RecyclerView recycler = view.findViewById(R.id.recycler1);
        final CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(activity.custom, nameArray, imageArray, isFavorite, download, getActivity());
        adapter.inputtype=3;

        GridLayoutManager layoutManager = new GridLayoutManager(activity, 1);
        recycler.setLayoutManager(layoutManager);
        cursor.close();
        db.close();
        adapter.setListener1(new CaptionedImagesAdapter.Listener1() {
            public void onClick1(int position, View view) {
                boolean fav = false;
                oncreateadapter(inflater, container, position, fav, adapter);
                adapter.notifyDataSetChanged();
                recycler.invalidate();

            }
        });

        recycler.setAdapter(adapter);
        recycler.setHasFixedSize(true);
        return view;
    }


    public void oncreateadapter(LayoutInflater inflater, ViewGroup container, int position, boolean fav, CaptionedImagesAdapter adapter) {
        {
            SQLiteOpenHelper exedb = new ExeDatabase(activity);
            SQLiteDatabase asdd = exedb.getReadableDatabase();
            Cursor cursor = asdd.query("EXE",
                    new String[]{"_id"},
                    "FAVORITE = ?",
                    new String[]{Integer.toString(1)},
                    null, null, null);
            ArrayList<Integer> ids = new ArrayList<>();
            cursor.moveToFirst();
            try {
                do {
                    int c = cursor.getInt(0);
                    ids.add(c);
                } while (cursor.moveToNext());
                cursor.close();
            } catch (Exception ignored) {
            }
            asdd.close();
            ContentValues values = new ContentValues();
            values.put("FAVORITE", fav);

            SQLiteDatabase db = exedb.getWritableDatabase();
            db.update("EXE",
                    values,
                    "_id = ?",
                    new String[]{Integer.toString(ids.get(position))});
            db.close();
            SQLiteDatabase asd = exedb.getReadableDatabase();
            Cursor cursor1 = asd.query("EXE",
                    new String[]{"NAME", "IMAGEID", "FAVORITE", "_id"},
                    "FAVORITE = ?",
                    new String[]{Integer.toString(1)},
                    null, null, null);
            int b = cursor1.getCount();
            String[] nameArray = new String[b];
            int[] imageArray = new int[b];
            boolean[] isFavorite = new boolean[b];
            int[] idofposition = new int[b];
            int i = 0;
            try {
                cursor1.moveToFirst();
                do {
                    String name = cursor1.getString(0);
                    int image = cursor1.getInt(1);
                    boolean Favorite = (cursor1.getInt(2) == 1);
                    int iop = cursor1.getInt(3);
                    nameArray[i] = name;
                    imageArray[i] = image;
                    isFavorite[i] = Favorite;
                    idofposition[i] = iop;
                    i++;
                } while (cursor1.moveToNext());
            } catch (Exception ignored) {
            }
            View RootView = inflater.inflate(R.layout.fragment_trains, container, false);
            RecyclerView recycler = RootView.findViewById(R.id.recycler1);
            adapter.changeDatas(nameArray, imageArray, isFavorite);
            recycler.setAdapter(adapter);
            cursor1.close();
            asd.close();
        }
    }
}





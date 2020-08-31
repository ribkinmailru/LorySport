package com.example.lorysport;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SubType extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sub_type,container,false);
        final MainActivity2 activity = ((MainActivity2) getActivity());
        TextView title = ((MainActivity2) getActivity()).findViewById(R.id.textView3);
        title.setText(R.string.subtype);
        int t = activity.position;
        String[] nameArray = null;
        int[] imageArray = null;
        switch (t){
            case 0:
                nameArray = new
                        String[]{getString(R.string.k1),
                        getString(R.string.k2),
                        getString(R.string.k4)};


                imageArray = new int[]{
                        R.drawable.kvadr,
                        R.drawable.bis,
                        R.drawable.golen
                };
                break;

            case 1:
                nameArray = new
                        String[]{getString(R.string.c1),
                        getString(R.string.c2),
                        getString(R.string.c3)};


                imageArray = new int[]{
                        R.drawable.c1,
                        R.drawable.c2,
                        R.drawable.c3
                };
                break;

            case 2:
                nameArray = new
                        String[]{getString(R.string.d1),
                        getString(R.string.d2),
                        getString(R.string.d3)};


                imageArray = new int[]{
                        R.drawable.pdd,
                        R.drawable.pdd2,
                        R.drawable.pdd3
                };
                break;
            case 4:
                nameArray = new
                        String[]{getString(R.string.b1),
                        getString(R.string.b2),
                        getString(R.string.b3)};


                imageArray = new int[]{
                        R.drawable.b1,
                        R.drawable.b2,
                        R.drawable.b3
                };
                break;
        }




        RecyclerView recyclerView = view.findViewById(R.id.recyclertype2);
        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(nameArray, imageArray);
        adapter.inputtype=2;
        adapter.setListener1(new CaptionedImagesAdapter.Listener1() {
            @Override
            public void onClick1(int position, View view) {
                activity.subposition = position;
                activity.changefragment(3);
            }
        });
        GridLayoutManager layoutManager = new GridLayoutManager(activity,3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return view;
        }
    }

package com.example.lorysport;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
public class BodyByldingFragment extends Fragment {


MainActivity2 activity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bodybuild, container, false);
        activity = ((MainActivity2) getActivity());

        int[] image  = {R.drawable.legss, R.drawable.chesssst, R.drawable.shoulders,
               R.drawable.trapezi, R.drawable.back, R.drawable.biseps,
                R.drawable.trisepz, R.drawable.predpleche, R.drawable.press};


        String[] nameArray = {getString(R.string.legs),
                getString(R.string.chest),
                getString(R.string.shoulder),
                getString(R.string.trapezi),
                getString(R.string.delty),
                getString(R.string.biseps),
                getString(R.string.triseps),
                getString(R.string.predpleche),
                getString(R.string.press)};



        RecyclerView recyclerView = view.findViewById(R.id.recyclertype);
        recyclerView.setHasFixedSize(true);
        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(nameArray, image);
        adapter.inputtype=2;
        adapter.setListener1(new CaptionedImagesAdapter.Listener1() {
            @Override
            public void onClick1(int position, View view){
                if(position == 5 | position == 6) {
                activity.subposition = 0;
                activity.changefragment(3);

                }else {
                    activity.changefragment(2);
                }
                activity.position = position;
            }
        });
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return view;

    }
}
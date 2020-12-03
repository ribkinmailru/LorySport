package com.example.lorysport;


import android.content.res.Resources;
import android.content.res.TypedArray;
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
        Resources res = getResources();

        TypedArray image  = res.obtainTypedArray(R.array.bodybilding_photo);
        String[] nameArray = res.getStringArray(R.array.types);
        RecyclerView recyclerView = view.findViewById(R.id.recyclertype);
        recyclerView.setHasFixedSize(true);
        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(nameArray, image,null);
        adapter.setListener1(new CaptionedImagesAdapter.Listener1() {
            @Override
            public void onClick1(int position, View view){
                if(position == 5 | position == 6) {
                AppManager.subposition = 0;
                activity.changefragment(3, null);

                }else {
                    activity.changefragment(2, null);
                }
                AppManager.position = position;
            }
        });
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return view;

    }
}
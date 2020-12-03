package com.example.lorysport;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class WatchPhoto extends Fragment {
    private ArrayList<String> uris;

    public WatchPhoto(ArrayList<String> uris){
        this.uris = uris;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_watch_photo, container, false);
        getActivity().findViewById(R.id.toolbar).setVisibility(View.GONE);
        WatchPhoto.SectionPagerAdapter pagerAdapter = new WatchPhoto.SectionPagerAdapter(getChildFragmentManager());
        ViewPager pager = view.findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);
        TabLayout tab = view.findViewById(R.id.tabs);
        tab.setupWithViewPager(pager);
        for(int i=0; i < tab.getTabCount(); i++) {
            View tabs = ((ViewGroup) tab.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tabs.getLayoutParams();
            p.setMargins(0, 0, 5, 0);
            tab.requestLayout();
        }
            return view;
    }

    public class SectionPagerAdapter extends FragmentStatePagerAdapter {
        public SectionPagerAdapter(FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return new ShowPhotoFragment(uris.get(position),false,null);
        }


        @Override
        public int getCount() {
            return uris.size();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().findViewById(R.id.toolbar).setVisibility(View.VISIBLE);
    }
}
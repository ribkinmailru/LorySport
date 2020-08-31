package com.example.lorysport;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;


public class tablayoutfargment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tablayoutfargment, container, false);
        SectionPagerAdapter pagerAdapter = new SectionPagerAdapter(getChildFragmentManager());
        ViewPager pager = view.findViewById(R.id.pager5);
        pager.setAdapter(pagerAdapter);

        int custom = ((MainActivity2) getActivity()).custom;
        TextView title = ((MainActivity2) getActivity()).findViewById(R.id.textView3);
        title.setText(R.string.type);
        TabLayout tab = view.findViewById(R.id.tabs5);
        tab.setupWithViewPager(pager);
        if(custom == 2 ) {
            View tabs = ((ViewGroup) tab.getChildAt(0)).getChildAt(0);
            tabs.setBackground(getResources().getDrawable(R.drawable.tab_background_selector_red));
            View tabss = ((ViewGroup) tab.getChildAt(0)).getChildAt(1);
            tabss.setBackground(getResources().getDrawable(R.drawable.tab_background_selector_red));
        }
        if(custom == 3 ) {
            View tabs = ((ViewGroup) tab.getChildAt(0)).getChildAt(0);
            tabs.setBackground(getResources().getDrawable(R.drawable.tab_backgroud_pink));
            View tabss = ((ViewGroup) tab.getChildAt(0)).getChildAt(1);
            tabss.setBackground(getResources().getDrawable(R.drawable.tab_backgroud_pink));
        }
        return view;
    }

    public class SectionPagerAdapter extends FragmentPagerAdapter {
        public SectionPagerAdapter(FragmentManager fm){
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new BodyByldingFragment();
                case 1:
                    return new BodyByldingFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }


        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return getString(R.string.body);
                case 1:
                    return getString(R.string.fitness);
            }
            return null;
        }
    }



}
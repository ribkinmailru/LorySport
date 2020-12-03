package com.example.lorysport;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

public class MenuListFragment extends Fragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_fragment, container,
                false);
        NavigationView vNavigation = view.findViewById(R.id.nav_view);
        vNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                FragmentTransaction ft;
                Fragment fragment = null;
                MainActivity2 activity = ((MainActivity2)getActivity());
                FlowingDrawer mDrawer = activity.findViewById(R.id.drawerlayout);
                mDrawer.closeMenu();
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.profile:
                        fragment = new MainActivity();
                        activity.appbaravalable(true);
                        break;
                    case R.id.mytrains:
                        activity.addexe = false;
                        fragment = new ExesFragment(activity.addexe);
                        break;
                    case R.id.exersizes:
                        activity.addexe = false;
                        fragment = new tablayoutfargment();
                        break;
                    case R.id.schedule:
                        fragment = new Schedule();
                        break;
                    case R.id.params:
                        fragment = new BodyFrag();
                        break;
                    case R.id.healthy:
                        fragment = new HealthFrag();
                        break;
                    case R.id.run:
                        Intent intent = new Intent(getActivity(), odometrFragment.class);
                        startActivity(intent);
                        break;
                    case R.id.stats:
                        fragment = new NotificationFragment();

                        break;
                    case R.id.setting:
                        fragment = new SettingFragment();
                        break;
                    case R.id.contacts:
                        fragment = new ContactsFragment();
                        break;

                }
                if(!fragment.getClass().equals(activity.getSupportFragmentManager().findFragmentById(R.id.frame).getClass())) {
                    if(!(fragment instanceof MainActivity)){
                        activity.appbaravalable(false);
                    }
                    ft = activity.getSupportFragmentManager().beginTransaction();
                    ft.setCustomAnimations(R.animator.sheduleleft, R.animator.slideinright);
                    ft.replace(R.id.frame, fragment, "tag");
                    ft.commit();
                    ft = null;
                }
                return true;
            }
            });
        return view;
    }
}

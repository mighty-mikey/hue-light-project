package com.minds.great.hueLightProject.userInterface.fragments.tabFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.minds.great.hueLightProject.userInterface.fragments.lightListFragment.LightsListFragment;
import com.minds.great.hueLightProject.userInterface.fragments.MoodListFragment;

class TabAdapter extends FragmentPagerAdapter {
    private final int NUMBER_OF_TABS = 2;
    private final int FIRST_POSITION = 0;

    TabAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == FIRST_POSITION){
            return LightsListFragment.newInstance();
        }else{
            return MoodListFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return NUMBER_OF_TABS;
    }
}

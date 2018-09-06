package com.corroy.mathieu.moodtracker.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.corroy.mathieu.moodtracker.Fragments.MoodFragment;

public class PageAdapter extends FragmentPagerAdapter {

    public PageAdapter(FragmentManager fm) {
        super(fm);
    }

    // Return view numbers
    @Override
    public int getCount(){
        return 5;
    }

    // Return the elements position
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0 :
                return MoodFragment.newInstance("sad", "faded_red");
            case 1 :
                return MoodFragment.newInstance("disappointed", "warm_grey");
            case 2 :
                return MoodFragment.newInstance("normal", "cornflower_blue_65");
            case 3 :
                return MoodFragment.newInstance("happy", "light_sage");
            case 4 :
                return MoodFragment.newInstance("super_happy", "banana_yellow");
            default :
                return null;
        }
    }
}
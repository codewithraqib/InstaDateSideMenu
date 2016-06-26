package com.example.raqib.instadate;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

    private static final int NUM_PAGES = 10;

    public ScreenSlidePagerAdapter(FragmentManager fm) {
        super(fm);
    }



    @Override
    public Fragment getItem(int position) {
        SlidingFragment slidingFragment = new SlidingFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        slidingFragment.setArguments(bundle);
        return slidingFragment;
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
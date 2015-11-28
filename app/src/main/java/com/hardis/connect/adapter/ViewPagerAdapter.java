package com.hardis.connect.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hardis.connect.fragment.CovoiturageFeedsFragment;
import com.hardis.connect.fragment.FeedsFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private final String[] TITLES = {"Co-voiturage", "Evenement", "Hardis Life"};

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return CovoiturageFeedsFragment.newInstance();
            case 1:
                return FeedsFragment.newInstance();
            case 2:
                return FeedsFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }
}

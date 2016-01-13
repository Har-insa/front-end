package com.hardis.connect.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.hardis.connect.fragment.CovoiturageFeedsFragment;
import com.hardis.connect.fragment.FeedsFragment;
import com.hardis.connect.fragment.MyBookingFeedsFragment;
import com.hardis.connect.fragment.MyOffersFeedsFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private final String[] TITLES = {"Covoiturage", "Mes offres", "Mes réservations"};

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
                return MyOffersFeedsFragment.newInstance();
            case 2:
                return MyBookingFeedsFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }
}

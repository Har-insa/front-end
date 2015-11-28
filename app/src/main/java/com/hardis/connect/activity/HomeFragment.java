package com.hardis.connect.activity;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.astuetz.PagerSlidingTabStrip;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.hardis.connect.R;
import com.hardis.connect.adapter.ViewPagerAdapter;
import com.hardis.connect.fragment.CovoiturageFeedsFragment;
import com.hardis.connect.fragment.FeedsFragment;


public class HomeFragment extends Fragment {


    ViewPager pager;
    PagerSlidingTabStrip tabs;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String restaurantName){
        HomeFragment home = new HomeFragment();

        return home;

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.home_fragment, container, false);


        pager = (ViewPager) rootView.findViewById(R.id.pager_restaurant);

        pager.setAdapter(new ViewPagerAdapter(getChildFragmentManager()));

        tabs = (PagerSlidingTabStrip) rootView.findViewById(R.id.tabs_restaurant);
        tabs.setViewPager(pager);

        return rootView;
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}

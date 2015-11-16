package com.hardis.connect.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.hardis.connect.R;
import com.hardis.connect.fragment.FragmentDrawer;


public class MainActivity extends ActionBarActivity implements FragmentDrawer.FragmentDrawerListener{
    private FragmentDrawer drawerFragment;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        drawerFragment = (FragmentDrawer)getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        displayView(0);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //* Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        //if(id == R.id.action_search){
        //Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
        //return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    //Drawer Menu Item click handler

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    public void displayView(int position) {

        Fragment fragment = null;
        String title = getString(R.string.app_name);

        switch (position) {
            case 0://Accueil
                fragment = new HomeFragment();
                //title = getString("Hardis");
                break;
            case 2:
                break;
            case 3:

            case 4:
                break;
            case 5:
                break;
            case 7:
                break;
            case 8:
                break;
            case 10:
                break;
            case 11:
                break;
            case 13:
                break;
            case 14:
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            //fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }

}

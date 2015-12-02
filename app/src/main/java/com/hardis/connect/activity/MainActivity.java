package com.hardis.connect.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.hardis.connect.R;
import com.hardis.connect.controller.AgencyController;
import com.hardis.connect.controller.CovoiturageController;
import com.hardis.connect.controller.VolleyCallBack;
import com.hardis.connect.fragment.FragmentDrawer;
import com.hardis.connect.model.Covoiturage;
import com.hardis.connect.util.MessageUser;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements FragmentDrawer.FragmentDrawerListener{
    private FragmentDrawer drawerFragment;
    private Toolbar mToolbar;
    private FloatingActionButton fab1;
    private FloatingActionButton fab2;
    private FloatingActionButton fab3;
    private FloatingActionMenu menu1;
    private List<FloatingActionMenu> menus = new ArrayList<>();
    private Handler mUiHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        drawerFragment = (FragmentDrawer)getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
//handle the FAB : Menu + buttons
        menu1 = (FloatingActionMenu) findViewById(R.id.menu1);

        menu1.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu1.toggle(true);
            }
        });
        menus.add(menu1);
        menu1.hideMenuButton(false);

        int delay = 400;
        for (final FloatingActionMenu menu : menus) {
            mUiHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    menu.showMenuButton(true);
                }
            }, delay);
            delay += 150;
        }

        menu1.setClosedOnTouchOutside(true);

//buttons
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);

        fab1.setOnClickListener(clickListener);
        fab2.setOnClickListener(clickListener);
        fab3.setOnClickListener(clickListener);

        // display the first navigation drawer view on app launch
        displayView(0);

    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String text = "";

            switch (v.getId()) {
                case R.id.fab1:
                    //createNewOffer();
                    startActivity(new Intent(MainActivity.this, CreateCovoiturageOfferActivity.class));
                    menu1.close(true);
                    break;
                case R.id.fab2:
                    text = fab2.getLabelText();
                    Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.fab3:
                    text = fab3.getLabelText();
                    Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
                    break;
            }


        }
    };

    private void createNewOffer() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        View view = getLayoutInflater().inflate(R.layout.offre_covoiturage, null);
        final Spinner depart = (Spinner)view.findViewById(R.id.depart);
        final Spinner destination = (Spinner)view.findViewById(R.id.destination);
        Button create = (Button)view.findViewById(R.id.create);
        final DatePicker datePicker = (DatePicker)view.findViewById(R.id.datePicker);
        final TimePicker timePicker = (TimePicker)view.findViewById(R.id.timePicker);

        ArrayAdapter<String> dataAdapterR = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, AgencyController.getAgencies(getApplicationContext()));
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        depart.setAdapter(dataAdapterR);
        destination.setAdapter(dataAdapterR);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int departureAgencyId = AgencyController.getAgencyByName(depart.getSelectedItemPosition());
                int destinationAgencyId = AgencyController.getAgencyByName(destination.getSelectedItemPosition());

                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int day = datePicker.getDayOfMonth();
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();

                String departureDate = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(day) + "T"
                        + String.valueOf(hour) + ":" + String.valueOf(minute) + ":00";

                Log.v("departuredate", departureDate);

                if (departureAgencyId == -1 || destinationAgencyId == -1) {
                    Toast.makeText(MainActivity.this, MessageUser.get("1106"), Toast.LENGTH_SHORT).show();
                } else {
                    Covoiturage covoiturage = new Covoiturage();
                    covoiturage.setDepartureAgency(departureAgencyId);
                    covoiturage.setArrivalAgency(destinationAgencyId);
                    covoiturage.setDepartureTime(departureDate);
                    /*CovoiturageController.createCovoiturage(covoiturage, getApplicationContext(), new VolleyCallBack() {
                        @Override
                        public void onSuccess(String result) {

                            Log.v("create", "succ");
                        }

                        @Override
                        public void onFailed(String result) {
                            Log.v("create", "failed");
                        }
                    });*/

                }
            }
        });


        dialog.setView(view).create();
        dialog.show();

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

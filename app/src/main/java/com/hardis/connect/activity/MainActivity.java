package com.hardis.connect.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.hardis.connect.R;
import com.hardis.connect.controller.AgencyController;
import com.hardis.connect.controller.CovoiturageController;
import com.hardis.connect.controller.VolleyCallBack;
import com.hardis.connect.fragment.FragmentDrawer;
import com.hardis.connect.model.Covoiturage;
import com.hardis.connect.util.MessageUser;


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

        //handle the FAB
        FloatingActionButton FAB = (FloatingActionButton) findViewById(R.id.fab);

        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showOptions();
                createNewOffer();

            }
        });
        // display the first navigation drawer view on app launch
        displayView(0);

    }

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
                    CovoiturageController.createCovoiturage(covoiturage, getApplicationContext(), new VolleyCallBack() {
                        @Override
                        public void onSuccess(String result) {

                            Log.v("create", "succ");
                        }

                        @Override
                        public void onFailed(String result) {
                            Log.v("create", "failed");
                        }
                    });

                }
            }
        });


        dialog.setView(view).create();
        dialog.show();

    }


    private void showOptions(){
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        CharSequence items[] = new CharSequence[] {"Offre de covoiturage", "Evenement", "Post"};
        adb.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface d, int n) {
                // ...
            }

        });
        adb.setNegativeButton("Annuler", null);
        adb.setPositiveButton("Confirmer", null);
        adb.setTitle("Créer un :");
        adb.show();
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

package com.hardis.connect.activity;

/**
 * Created by Hassan on 12/1/2015.
 */

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.hardis.connect.R;
import com.hardis.connect.controller.AgencyController;
import com.hardis.connect.controller.CovoiturageController;
import com.hardis.connect.model.Covoiturage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class CreateCovoiturageOfferActivity extends AppCompatActivity {


    private FloatingActionButton fab_book;
    private FloatingActionButton fab_cancel;
    private FloatingActionMenu menu_book;
    private List<FloatingActionMenu> menus = new ArrayList<>();
    private Handler mUiHandler = new Handler();
    private Spinner depart;
    private Spinner destination;
    private Spinner capacite;
    private EditText dateDepart;
    private EditText heureDepart;
    private EditText heureArrivee;
    private EditText title;
    private EditText rdv;
    private Calendar myCalendar = Calendar.getInstance();
    private int b;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_covoiturage_offer);

        depart = (Spinner) findViewById(R.id.depart);
        destination = (Spinner) findViewById(R.id.destination);

        final List<String> agenciesName= AgencyController.getAgencies(getApplicationContext());
        final List<String> agenciesName1=AgencyController.getAgencies(getApplicationContext());

        agenciesName.add(0,"Sélectionnez l'agence de départ");
        agenciesName1.add(0,"Sélectionnez l'agence de destination");

        List<String> capacites = new ArrayList<String>();
        capacites.add("Sélectionnez la capacité");
        capacites.add("1");
        capacites.add("2");
        capacites.add("3");
        capacites.add("4");
        capacites.add("5");
        capacites.add("6");

        ArrayAdapter<String> departSpinnerAdapterR = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, agenciesName);
        departSpinnerAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        depart.setSelection(0);
        depart.setAdapter(departSpinnerAdapterR);


        ArrayAdapter<String> arriveeSpinnerAdapterR = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,agenciesName1);
        arriveeSpinnerAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        destination.setSelection(0);
        destination.setAdapter(arriveeSpinnerAdapterR);

        ArrayAdapter<String> capaciteAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,capacites);
        capaciteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

//handle the FAB : Menu + buttons
        menu_book = (FloatingActionMenu) findViewById(R.id.menu_book);

        menu_book.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu_book.toggle(true);
            }
        });
        menus.add(menu_book);
        menu_book.hideMenuButton(false);

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

        menu_book.setClosedOnTouchOutside(true);

//buttons
        fab_book = (FloatingActionButton) findViewById(R.id.fab_book);
        fab_cancel = (FloatingActionButton) findViewById(R.id.fab_cancel);


        fab_book.setOnClickListener(clickListener);
        fab_cancel.setOnClickListener(clickListener);

        title = (EditText)findViewById(R.id.titre);
        rdv = (EditText)findViewById(R.id.rdv);


        capacite = (Spinner) findViewById(R.id.capacite);



        capacite.setAdapter(capaciteAdapter);
        capacite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.maha));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        dateDepart = (EditText) findViewById(R.id.date_depart);
        heureDepart = (EditText) findViewById(R.id.heure_depart);
        heureArrivee = (EditText) findViewById(R.id.heure_arrivee);



        dateDepart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateCovoiturageOfferActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        heureDepart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new TimePickerDialog(CreateCovoiturageOfferActivity.this, time,
                        myCalendar.get(Calendar.HOUR_OF_DAY),
                        myCalendar.get(Calendar.MINUTE), true).show();
                b=0;
            }
        });

        heureArrivee.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new TimePickerDialog(CreateCovoiturageOfferActivity.this, time,
                        myCalendar.get(Calendar.HOUR_OF_DAY),
                        myCalendar.get(Calendar.MINUTE), true).show();
                b=1;
            }
        });

        rdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CreateCovoiturageOfferActivity.this,MapsActivity.class);
                startActivityForResult(i,1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==1){
            if(resultCode== RESULT_OK) {
                address= data.getStringExtra("address");
                rdv.setText(address);
            }
        }

    }

    private void createAndShowAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nouvelle offre de covoiturage");
        builder.setMessage("Voulez-vous publier cette offre?");
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //TODO
                int departAgency= AgencyController.getAgencyByName(depart.getSelectedItemPosition());
                int destinationAgency=AgencyController.getAgencyByName(destination.getSelectedItemPosition());

                String departDate= dateDepart.getText().toString();
                String departHour=heureDepart.getText().toString();
                String arriveeHour= heureArrivee.getText().toString();


                if(departAgency == -1 || destinationAgency == -1 || departDate =="" || departHour=="" || arriveeHour=="") {
                    Toast.makeText(getApplicationContext(),"Remplir tous les champs",Toast.LENGTH_LONG);
                }
                else {
                    Covoiturage covoiturage = new Covoiturage();
                    covoiturage.setDepartureAgency(departAgency);
                    covoiturage.setArrivalAgency(destinationAgency);
                    covoiturage.setTitle(title.getText().toString());
                    covoiturage.setDepartureTime(departDate + "T" + departHour + ":00");
                    covoiturage.setArrivalDate(departDate + "T" + arriveeHour + ":00");
                    int capacity = Integer.valueOf(capacite.getSelectedItem().toString());
                    covoiturage.setCapacite(capacity);
                    //covoiturage.setAddress(address);
                    //Log.v("adr",address);
                    CovoiturageController.addTravel(covoiturage, getApplicationContext());
                    finish();
                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }

            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //TODO
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    //handling date
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDateLabel();
        }

    };

    private void updateDateLabel() {

        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

        dateDepart.setText(sdf.format(myCalendar.getTime()));
    }

    // handling time

    TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            myCalendar.set(Calendar.MINUTE, minute);
            updateTimeLabel(hourOfDay, minute);
        }
    };

    private void updateTimeLabel(int hourOfDay,int minute) {
        if( b==0)
            heureDepart.setText(String.format("%02d:%02d",hourOfDay,minute));
        else if (b==1)
            heureArrivee.setText(String.format("%02d:%02d",hourOfDay,minute));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fab_book:
                    createAndShowAlertDialog();
                    menu_book.close(true);
                    break;
                case R.id.fab_cancel:
                    menu_book.close(true);
                    finish();
                    break;
            }


        }
    };
}
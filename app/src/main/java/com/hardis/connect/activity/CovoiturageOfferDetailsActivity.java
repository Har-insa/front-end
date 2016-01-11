package com.hardis.connect.activity;

/**
 * Created by Hassan on 12/1/2015.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.hardis.connect.R;
import com.hardis.connect.controller.BookingController;
import com.hardis.connect.model.Covoiturage;
import com.hardis.connect.util.GlobalMethodes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CovoiturageOfferDetailsActivity extends AppCompatActivity {


    private FloatingActionButton fab_book;
    private FloatingActionButton fab_cancel;
    private FloatingActionMenu menu_book;
    private List<FloatingActionMenu> menus = new ArrayList<>();
    private Handler mUiHandler = new Handler();

    private TextView userName;
    private TextView title;
    private TextView departAgency;
    private TextView arrivalAgency;
    private TextView departDate;
    private TextView arriveeDate;
    private TextView capacite;
    private TextView timeStamp;
    private Button chat;

    private Covoiturage covoiturage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_offre_covoiturage);

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

        userName = (TextView) findViewById(R.id.userName);
        title = (TextView) findViewById(R.id.title);
        timeStamp = (TextView) findViewById(R.id.timeStamp);
        departAgency =(TextView) findViewById(R.id.depart_value);
        arrivalAgency =(TextView) findViewById(R.id.arrive_value);
        capacite = (TextView) findViewById(R.id.dispo_value);
        departDate = (TextView) findViewById(R.id.date_value);
        arriveeDate = (TextView) findViewById(R.id.datearrivee_value);
        chat = (Button) findViewById(R.id.button2);
       /* chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(CovoiturageOfferDetailsActivity.this,MessagingActivity.class));
            }
        });*/

        Bundle bundle = getIntent().getExtras();
        if(bundle== null) return;
        covoiturage = (Covoiturage) getIntent().getSerializableExtra("covoiturage");
        userName.setText(covoiturage.getUserName());
        if(covoiturage.getTitle()!="")
            title.setText(covoiturage.getTitle());
        timeStamp.setText(covoiturage.getTimeStamp());
        departAgency.setText(covoiturage.getDepartureAgencyName());
        arrivalAgency.setText(covoiturage.getArrivalAgencyName());
        capacite.setText(String.valueOf(covoiturage.getCapacite()));
        departDate.setText(covoiturage.getDepartureTime().replace("T"," "));
        arriveeDate.setText(covoiturage.getArrivalDate().replace("T"," "));
    }


    private void createAndShowAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Réservation");
        builder.setMessage("Voulez-vous réserver une place ?");
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //TODO
                BookingController.bookTravel(covoiturage.getId(),covoiturage.getEmail(),getApplicationContext());
                dialog.dismiss();
                finish();
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
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
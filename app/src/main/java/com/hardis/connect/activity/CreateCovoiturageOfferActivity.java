package com.hardis.connect.activity;

/**
 * Created by Hassan on 12/1/2015.
 */

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.hardis.connect.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class CreateCovoiturageOfferActivity extends ActionBarActivity {


    private FloatingActionButton fab_book;
    private FloatingActionButton fab_cancel;
    private FloatingActionMenu menu_book;
    private List<FloatingActionMenu> menus = new ArrayList<>();
    private Handler mUiHandler = new Handler();
    private EditText dateDepart;
    private EditText heureDepart;
    private Calendar myCalendar = Calendar.getInstance();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.create_new_covoiturage_offer);

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


            dateDepart = (EditText) findViewById(R.id.date_depart);
            heureDepart = (EditText) findViewById(R.id.heure_depart);



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
                }
            });
        }


        private void createAndShowAlertDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Nouveau offre");
            builder.setMessage("Publier cet offre?");
            builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //TODO
                    dialog.dismiss();
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

        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

        dateDepart.setText(sdf.format(myCalendar.getTime()));
    }


    // handling time

    TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            myCalendar.set(Calendar.MINUTE, minute);
            updateTimeLabel();
        }
    };

    private void updateTimeLabel() {

        heureDepart.setText(myCalendar.get(Calendar.HOUR_OF_DAY) + ":"+myCalendar.get(Calendar.MINUTE));
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
package com.hardis.connect.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hardis.connect.R;
import com.hardis.connect.controller.CovoiturageController;
import com.hardis.connect.controller.VolleyCallBack;
import com.hardis.connect.model.Covoiturage;
import com.hardis.connect.model.CovoiturageOffreItem;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyOffers extends AppCompatActivity {

    private List<Covoiturage> covoiturages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_offers);

        getData();
    }

    public void getData() {

        CovoiturageController.getMyOffers(getApplicationContext(), new VolleyCallBack() {
            @Override
            public void onSuccess(String result) {
                covoiturages =CovoiturageController.getMesOffres();
            }

            @Override
            public void onFailed(String result) {

            }
        });
    }
}

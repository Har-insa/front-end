package com.hardis.connect;

import android.app.Application;
import android.content.SharedPreferences;

import com.hardis.connect.util.MessageUser;
import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by a on 13/11/2015.
 */
public class HardisConnect extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MessageUser.init();

        Parse.initialize(this, "T8DMGrbZh7i69YfOMg2OiJrhMEBcR02RncN8Z4TF", "FFzlkrhCamOTZbSYTxM4vYYMzckuxGk0A5ZXheyN");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}

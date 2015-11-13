package com.hardis.connect;

import android.app.Application;

import com.hardis.connect.util.MessageUser;

/**
 * Created by a on 13/11/2015.
 */
public class HardisConnect extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MessageUser.init();
    }
}

package com.hardis.connect.util;

/**
 * Created by Hassan El Moutaraji on 25/08/2015.
 */
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class GlobalMethodes {

    public static String username;
    public static boolean isNumeric(char n) {
        return (n=='0' || n=='1'|| n=='2'|| n=='3'|| n=='4'|| n=='5'|| n=='6'|| n=='7'|| n=='8'|| n=='9');

    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    //1 minute = 60 seconds
    //1 hour = 60 x 60 = 3600
    //1 day = 3600 x 24 = 86400
    public static long[] printDifference(Date startDate, Date endDate){

        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        Log.v("elapseddays",String.valueOf(elapsedDays));
        Log.v("elapsedHours",String.valueOf(elapsedHours));
        Log.v("elapsedMinutes",String.valueOf(elapsedMinutes));
        Log.v("elapsedSeconds",String.valueOf(elapsedSeconds));


        long [] result = new long[4];

        result[0] = elapsedDays;
        result[1] = elapsedHours;
        result[2] = elapsedMinutes;
        result[3] = elapsedSeconds;

        return result;
    }


}

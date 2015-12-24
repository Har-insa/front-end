package com.hardis.connect.util;

import android.util.Log;

import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;

import org.json.JSONObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class GlobalMethodes {

    public static String username;
    public static int id;
    public static String fullname;

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

    public static void sendNotification(String title,String content,String recipientId){
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("alert", content);
            jsonObject.put("title", title);

            Log.v("recipientId",recipientId);
            ParsePush push = new ParsePush();
            ParseQuery query = ParseInstallation.getQuery();
            query.whereEqualTo("deviceType","android");
            query.whereEqualTo("userName",recipientId);
            push.setQuery(query);
            push.setData(jsonObject);
            push.sendInBackground();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

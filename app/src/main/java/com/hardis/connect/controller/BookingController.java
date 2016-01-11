package com.hardis.connect.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.hardis.connect.util.AllUrls;
import com.hardis.connect.util.GlobalMethodes;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by a on 21/12/2015.
 */
public class BookingController {

    public static void bookTravel(int idTravel,final String owner,final Context context) {
        JsonObjectRequest request = null;
        try {
            SharedPreferences pref = context.getSharedPreferences("Hardis", 0);
            int idUser  =pref.getInt("id", 0);
            Log.v("Hoang",String.valueOf(idUser));
            final String fullName = pref.getString("fullName", null);
            Log.v("Hoang",fullName);

            JSONObject jsonObj = new JSONObject();
            jsonObj.put("IdTravel",idTravel);
            jsonObj.put("IdUser",idUser);

            request = new JsonObjectRequest(Request.Method.POST, AllUrls.book_offer_url, jsonObj,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.v("owner",owner);
                            GlobalMethodes.sendNotification("Réservation",fullName+" veut réserver une place",owner);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d("Response", error.getMessage());
                        }
                    }
            )
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences pref = context.getSharedPreferences("Hardis", 0);
                String token =pref.getString("token",null);
                params.put("Authorization", token);
                return params;
            }
            };
        }
        catch(JSONException e)
        {
        }
        RequestController.getInstance(context).addToRequestQueue(request);
    }



    public static void waitingBooking(final int idUser,final Context context) {

        JsonObjectRequest request = null;

            request = new JsonObjectRequest(Request.Method.GET, AllUrls.book_offer_url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.v("waiting",response.toString());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d("Response", error.getMessage());
                        }
                    }
            )
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    SharedPreferences pref = context.getSharedPreferences("Hardis", 0);
                    String token =pref.getString("token",null);
                    params.put("Authorization", token);
                    return params;
                }

                @Override
                public Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id_user",String.valueOf(1));
                    return params;
                }
            };

        RequestController.getInstance(context).addToRequestQueue(request);
    }
}

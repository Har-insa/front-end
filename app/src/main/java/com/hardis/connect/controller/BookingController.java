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

    public static void bookTravel(int idTravel,int idUser,final String owner,final Context context) {
        JsonObjectRequest request = null;
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("IdTravel",idTravel);
            jsonObj.put("IdUser",idUser);

            request = new JsonObjectRequest(Request.Method.POST, AllUrls.book_offer_url, jsonObj,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            GlobalMethodes.sendNotification("Réservation",GlobalMethodes.fullname+" veut réserver une place",owner);
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
}

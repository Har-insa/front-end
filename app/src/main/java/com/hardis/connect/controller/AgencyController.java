package com.hardis.connect.controller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;
import com.hardis.connect.util.AllUrls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by a on 12/11/2015.
 */
public class  AgencyController {


    public static String[] getAgencies(Context context) {
        final String [] agencies = new String[5];
        StringRequest request = new StringRequest(Request.Method.GET, AllUrls.get_agencies_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.v("maha",response.toString());
                            JSONArray data = new JSONArray(response);
                            for(int i=0;i<data.length();i++) {
                                String name = data.getJSONObject(i).getString("Name");
                                agencies[i] = name;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Response", error.getMessage());
            }
        }
        );

        RequestController.getInstance(context).addToRequestQueue(request);
        return agencies;
    }
}
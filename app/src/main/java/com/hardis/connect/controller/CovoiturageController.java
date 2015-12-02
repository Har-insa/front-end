package com.hardis.connect.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.hardis.connect.model.Covoiturage;
import com.hardis.connect.util.AllUrls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by a on 28/11/2015.
 */
public class CovoiturageController {

    private final static List<Covoiturage> covoiturages = new ArrayList<>();

    public static List<Covoiturage> getOffresCovoiturage(final Context context,final VolleyCallBack callBack) {

        StringRequest request = new StringRequest(Request.Method.GET, AllUrls.get_offres_covoiturage_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray data = new JSONArray(response);
                            for(int i=0;i<data.length();i++) {
                                int capacity = data.getJSONObject(i).getInt("Capacity");
                                String username = data.getJSONObject(i).getJSONObject("Publication").getJSONObject("User").getString("FirstName")+
                                        data.getJSONObject(i).getJSONObject("Publication").getJSONObject("User").getString("Lastname");
                                String departureAgency=data.getJSONObject(i).getJSONObject("DepartureAgency").getString("Name");
                                String arrivalAgency=data.getJSONObject(i).getJSONObject("ArrivalAgency").getString("Name");
                                String departureDate= data.getJSONObject(i).getString("DepartureTime");
                                Covoiturage covoiturage=new Covoiturage();
                                covoiturage.setCapacite(capacity);
                                covoiturage.setDepartureAgencyName(departureAgency);
                                covoiturage.setArrivalAgencyName(arrivalAgency);
                                covoiturage.setDepartureTime(departureDate);
                                covoiturage.setUserName(username);
                                covoiturages.add(covoiturage);
                            }
                            callBack.onSuccess("success");
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

        RequestController.getInstance(context).addToRequestQueue(request);
        return covoiturages;
    }

    public static void testAddTravel(Covoiturage covoiturage,Context context, final VolleyCallBack callBack) {
        JsonObjectRequest request = null;
        try {
            JSONObject jsonObj = new JSONObject();
            JSONObject publication = new JSONObject();
            JSONObject group = new JSONObject();
            JSONObject category = new JSONObject();
            JSONObject depart = new JSONObject();
            JSONObject arrival = new JSONObject();
            group.put("Id",1);
            category.put("Id",1);
            publication.put("Group",group);
            publication.put("Category",category);
            publication.put("Title","Test Title JSON Nested");
            publication.put("Description","Test description");
            jsonObj.put("Publication",publication);
            jsonObj.put("Capacity",9);
            depart.put("Id",1);
            arrival.put("Id",1);
            jsonObj.put("DepartureAgency",depart);
            jsonObj.put("ArrivalAgency",arrival);
            jsonObj.put("DepartureTime","2015-12-06T12:30:00");
            jsonObj.put("ArrivalTime","2015-12-07T15:30:00");


            request = new JsonObjectRequest(Request.Method.POST, "http://connext2.azurewebsites.net/api/travels/", jsonObj,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            callBack.onSuccess("success");
                            Log.v("hoang","success");

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d("Response", error.getMessage());
                            callBack.onFailed("failed");
                        }
                    }

            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    return params;
                }
            };
        }
        catch(JSONException e)
        {
        }
        RequestController.getInstance(context).addToRequestQueue(request);
    }

    public static List<Covoiturage> getCovoiturages() {
        return covoiturages;
    }

}

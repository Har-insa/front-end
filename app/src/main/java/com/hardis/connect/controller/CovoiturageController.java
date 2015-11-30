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

    public static void createCovoiturage(Covoiturage covoiturage, final Context context,final VolleyCallBack callBack) {
        StringRequest request = null;
        try {
           final JSONObject jsonBody = new JSONObject("{\"Publication\":{\"Group\":{\"Id\":1},\"Category\":{\"Id\":1},\"Title\":\"Covoiturage vers Maroc\",\"Description\":\"Trajet en voiture à plusieurs\"},\"Capacity\":8,\"DepartureAgency\":{\"Id\":2},\"ArrivalAgency\":{\"Id\":1},\"DepartureTime\":\"2015-12-06T12:30:00\",\"ArrivalTime\":\"2015-12-07T15:30:00\"}");


            request = new StringRequest(Request.Method.POST, AllUrls.add_covoiturage_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.v("covoiturage", "yees");
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.v("covoiturage", "no");
                    if (error instanceof TimeoutError) {
                        Log.e("Volley", "TimeoutError");
                    }else if(error instanceof NoConnectionError){
                        Log.e("Volley", "NoConnectionError");
                    } else if (error instanceof AuthFailureError) {
                        Log.e("Volley", "AuthFailureError");
                    } else if (error instanceof ServerError) {
                        Log.e("Volley", "ServerError");
                    } else if (error instanceof NetworkError) {
                        Log.e("Volley", "NetworkError");
                    } else if (error instanceof ParseError) {
                        Log.e("Volley", "ParseError");
                    }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    SharedPreferences pref = context.getSharedPreferences("Hardis", 0);
                    String token = pref.getString("token", null);
                    Log.v("token retrieved", token);
                    params.put("Authorization", token);
                    return params;
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    Log.v("jsonbody",jsonBody.toString());
                    return jsonBody.toString().getBytes();
                }
            };
        }
        catch(JSONException e)
        {

        }
        /*try {
            JSONObject jsonBody = new JSONObject("{\"Publication\":{\"Group\":{\"Id\":1},\"Category\":{\"Id\":1},\"Title\":\"Covoiturage vers Maroc\",\"Description\":\"Trajet en voiture à plusieurs\"},\"Capacity\":8,\"DepartureAgency\":{\"Id\":2},\"ArrivalAgency\":{\"Id\":1},\"DepartureTime\":\"2015-12-06T12:30:00\",\"ArrivalTime\":\"2015-12-07T15:30:00\"}");

            request = new JsonObjectRequest(Request.Method.POST, AllUrls.add_covoiturage_url, jsonBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.v("covoiturage","yees");
                            Log.v("covoi",response.toString());
                            callBack.onSuccess("success");

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d("Response", error.getMessage());
                            callBack.onFailed("failed");
                        }
                    }
            )
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    SharedPreferences pref = context.getSharedPreferences("Hardis", 0);
                    String token =pref.getString("token",null);
                    Log.v("token retrieved", token);
                    params.put("Authorization", token);
                    return params;
                }
            };
        }
        catch(JSONException e)
        {

        }*/
        RequestController.getInstance(context).addToRequestQueue(request);

    }

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

    public static List<Covoiturage> getCovoiturages() {
        return covoiturages;
    }

}

package com.hardis.connect.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by a on 28/11/2015.
 */
public class CovoiturageController {

    final static private List<Covoiturage> covoiturages = new ArrayList<>();

    public static List<Covoiturage> getOffresCovoiturage(final Context context, final VolleyCallBack callBack) {
        StringRequest request = new StringRequest(Request.Method.GET, AllUrls.get_offres_covoiturage_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray data = new JSONArray(response);
                            Log.v("response",response);
                            for(int i=0;i<data.length();i++) {
                                int capacity = data.getJSONObject(i).getInt("Capacity");
                                String username = data.getJSONObject(i).getJSONObject("Publication").getJSONObject("User").getString("FirstName")+
                                        " "+data.getJSONObject(i).getJSONObject("Publication").getJSONObject("User").getString("Lastname");
                                String title = data.getJSONObject(i).getJSONObject("Publication").getString("Title");
                                String dateCreation = data.getJSONObject(i).getJSONObject("Publication").getString("DateTimeCreation");
                                Log.v("tarikh",dateCreation);
                                String departureAgency=data.getJSONObject(i).getJSONObject("DepartureAgency").getString("Name");
                                String arrivalAgency=data.getJSONObject(i).getJSONObject("ArrivalAgency").getString("Name");
                                String departureDate= data.getJSONObject(i).getString("DepartureTime");
                                Covoiturage covoiturage=new Covoiturage();
                                covoiturage.setCapacite(capacity);
                                covoiturage.setDepartureAgencyName(departureAgency);
                                covoiturage.setArrivalAgencyName(arrivalAgency);
                                covoiturage.setDepartureTime(departureDate);
                                covoiturage.setUserName(username);
                                covoiturage.setTitle(title);
                                covoiturage.setDateCreation(dateCreation);
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

    public static void addTravel(Covoiturage covoiturage,final Context context) {
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
            publication.put("Title",covoiturage.getTitle());
            publication.put("Description", "Test description");
            jsonObj.put("Publication",publication);
            jsonObj.put("Capacity",covoiturage.getCapacite());
            depart.put("Id",covoiturage.getDepartureAgency());
            arrival.put("Id",covoiturage.getArrivalAgency());
            jsonObj.put("DepartureAgency",depart);
            jsonObj.put("ArrivalAgency",arrival);
            jsonObj.put("DepartureTime",covoiturage.getDepartureTime());
            jsonObj.put("ArrivalTime","2015-12-07T15:30:00");

            Log.v("json", jsonObj.toString());
            request = new JsonObjectRequest(Request.Method.POST,AllUrls.add_covoiturage_url, jsonObj,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.v("response","success");
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d("Response", error.getMessage());
                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    SharedPreferences pref = context.getSharedPreferences("Hardis", 0);
                    String token =pref.getString("token",null);
                    Log.v("token", token);
                    params.put("Authorization", token);
                    params.put("Accept-Encoding", "gzip, deflate, sdch");
                    params.put("Accept", "*/*");

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


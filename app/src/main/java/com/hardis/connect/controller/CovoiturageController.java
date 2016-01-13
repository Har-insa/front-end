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
import com.hardis.connect.HardisConnect;
import com.hardis.connect.model.Covoiturage;
import com.hardis.connect.model.User;
import com.hardis.connect.util.AllUrls;
import com.hardis.connect.util.GlobalMethodes;

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

    final static private List<Covoiturage> covoiturages = new ArrayList<>();
    final static private List<Covoiturage> mesOffres = new ArrayList<>();
    final static private List<Covoiturage> mesReservations= new ArrayList<>();
    final static private List<User> users = new ArrayList<>();

    public static List<Covoiturage> getOffresCovoiturage(final Context context, final VolleyCallBack callBack) {
        covoiturages.clear();
        StringRequest request = new StringRequest(Request.Method.GET, AllUrls.get_offres_covoiturage_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray data = new JSONArray(response);
                            Log.v("response",response);
                            for(int i=0;i<data.length();i++) {
                                int id = data.getJSONObject(i).getInt("Id");
                                int capacity = data.getJSONObject(i).getInt("Capacity");
                                String username = data.getJSONObject(i).getJSONObject("Publication").getJSONObject("User").getString("FirstName")+
                                        " "+data.getJSONObject(i).getJSONObject("Publication").getJSONObject("User").getString("Lastname");
                                String email = data.getJSONObject(i).getJSONObject("Publication").getJSONObject("User").getString("Email");
                                String title = data.getJSONObject(i).getJSONObject("Publication").getString("Title");
                                String dateCreation = data.getJSONObject(i).getJSONObject("Publication").getString("DateTimeCreation");
                                String departureAgency=data.getJSONObject(i).getJSONObject("DepartureAgency").getString("Name");
                                String arrivalAgency=data.getJSONObject(i).getJSONObject("ArrivalAgency").getString("Name");
                                String departureDate= data.getJSONObject(i).getString("DepartureTime");
                                String arrivalDate = data.getJSONObject(i).getString("ArrivalTime");
                                Covoiturage covoiturage=new Covoiturage();
                                covoiturage.setId(id);
                                covoiturage.setEmail(email);
                                covoiturage.setCapacite(capacity);
                                covoiturage.setDepartureAgencyName(departureAgency);
                                covoiturage.setArrivalAgencyName(arrivalAgency);
                                covoiturage.setDepartureTime(departureDate);
                                covoiturage.setArrivalDate(arrivalDate);
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

    public static List<Covoiturage> getMyOffers(final Context context, final VolleyCallBack callBack) {
        mesOffres.clear();
        Map<String, String> params = new HashMap<String, String>();
        SharedPreferences pref = context.getSharedPreferences("Hardis", 0);
        int id =pref.getInt("id", 0);
        StringRequest request = new StringRequest(Request.Method.GET, AllUrls.get_my_offres_covoiturage_url+"?id_user="+id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray data = new JSONArray(response);
                            Log.v("getMyOffers",response);
                            for(int i=0;i<data.length();i++) {
                                int id = data.getJSONObject(i).getInt("Id");
                                int capacity = data.getJSONObject(i).getInt("Capacity");
                                String username = data.getJSONObject(i).getJSONObject("Publication").getJSONObject("User").getString("FirstName")+
                                        " "+data.getJSONObject(i).getJSONObject("Publication").getJSONObject("User").getString("Lastname");
                                String email = data.getJSONObject(i).getJSONObject("Publication").getJSONObject("User").getString("Email");
                                String title = data.getJSONObject(i).getJSONObject("Publication").getString("Title");
                                String dateCreation = data.getJSONObject(i).getJSONObject("Publication").getString("DateTimeCreation");
                                String departureAgency=data.getJSONObject(i).getJSONObject("DepartureAgency").getString("Name");
                                String arrivalAgency=data.getJSONObject(i).getJSONObject("ArrivalAgency").getString("Name");
                                String departureDate= data.getJSONObject(i).getString("DepartureTime");
                                String arrivalDate = data.getJSONObject(i).getString("ArrivalTime");
                                Covoiturage covoiturage=new Covoiturage();
                                covoiturage.setId(id);
                                covoiturage.setEmail(email);
                                covoiturage.setCapacite(capacity);
                                covoiturage.setDepartureAgencyName(departureAgency);
                                covoiturage.setArrivalAgencyName(arrivalAgency);
                                covoiturage.setDepartureTime(departureDate);
                                covoiturage.setArrivalDate(arrivalDate);
                                covoiturage.setUserName(username);
                                covoiturage.setTitle(title);
                                covoiturage.setDateCreation(dateCreation);
                                mesOffres.add(covoiturage);
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
        return mesOffres;
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
            //jsonObj.put("ArrivalTime","2015-12-07T15:30:00");
            jsonObj.put("ArrivalTime",covoiturage.getArrivalDate());

            Log.v("json", jsonObj.toString());
            request = new JsonObjectRequest(Request.Method.POST,AllUrls.add_covoiturage_url, jsonObj,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.v("response","success");
                            Log.v("response",response.toString());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d("Response", error.getMessage());
                            Log.v("Response",error.getClass().toString());
                            error.printStackTrace();
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
                    return params;
                }
            };
        }
        catch(JSONException e)
        {
        }
        RequestController.getInstance(context).addToRequestQueue(request);
    }

    public static List<User> getPendingRequest(final int idTravel,final Context context,final VolleyCallBack callBack) {
        users.clear();
        StringRequest request = new StringRequest(Request.Method.GET, AllUrls.get_pending_requests_url+"/"+idTravel,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray data = new JSONArray(response);
                            Log.v("pendingrequests",response);
                            for(int i=0;i<data.length();i++) {
                                int idAction=data.getJSONObject(i).getInt("IdAction");
                                if(idAction==1) {
                                    int requestId = data.getJSONObject(i).getInt("Id");
                                    String firstName = data.getJSONObject(i).getJSONObject("User").getString("FirstName");
                                    String lastName = data.getJSONObject(i).getJSONObject("User").getString("Lastname");
                                    String userName = data.getJSONObject(i).getJSONObject("User").getString("Email");
                                    User user = new User(firstName, lastName, userName, requestId);
                                    users.add(user);
                                }
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
        return users;
    }

    public static void acceptRequest(int idRequest, final int idAction,final Context context) {
        Log.v("idRequest", String.valueOf(idRequest));
        StringRequest request = new StringRequest(Request.Method.PUT, AllUrls.accept_request_requests_url+"/"+idRequest,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            Log.v("acceptRequest",response);
                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Response", error.getMessage());
                Log.v("acceptRequest", "error");
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
            public Map<String, String> getParams() throws AuthFailureError {
                Log.v("idAction",String.valueOf(idAction));
                Map<String, String> params = new HashMap<String, String>();
                params.put("idAction",String.valueOf(idAction));
                return params;
            }
        };

        RequestController.getInstance(context).addToRequestQueue(request);
    }

    public static List<Covoiturage> getMyBookings(final Context context, final VolleyCallBack callBack){
        mesReservations.clear();
        StringRequest request = new StringRequest(Request.Method.GET, AllUrls.book_offer_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            SharedPreferences pref = context.getSharedPreferences("Hardis", 0);
                            int id =pref.getInt("id", 0);
                            JSONArray data = new JSONArray(response);
                            Log.v("getMyBookings",response);
                            for(int i=0;i<data.length();i++) {
                                int idUser= data.getJSONObject(i).getInt("IdUser");
                                int idAction=data.getJSONObject(i).getInt("IdAction");
                                if(idAction==2 && idUser==id) {
                                    Covoiturage covoiturage=new Covoiturage();
                                    covoiturage.setId(data.getJSONObject(i).getInt("IdTravel"));
                                    mesReservations.add(covoiturage);
                                }
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
        return mesReservations;

    }

    public static List<Covoiturage> getMesOffres() {
        return mesOffres;
    }
    public static List<Covoiturage> getCovoiturages() {
        return covoiturages;
    }
    public static List<User> getUsers() {
        return users;
    }
    public static List<Covoiturage> getMesReservations() {
        return mesReservations;
    }
    public static Covoiturage getCovoiturageById(int id) {

        for (int i = 0; i < covoiturages.size(); i++) {
            if(covoiturages.get(i).getId()==id) return covoiturages.get(i);
        }
        return null;
    }

}


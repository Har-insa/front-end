package com.hardis.connect.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.hardis.connect.activity.AuthenticationActivity;
import com.hardis.connect.model.User;
import com.hardis.connect.util.AllUrls;
import com.hardis.connect.util.MessageUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by a on 13/11/2015.
 */
public class UserController {

    public static void addUser(final Context context,final User user,final VolleyCallBack callBack) {
        JsonObjectRequest request = null;
        try {
            JSONObject jsonBody = new JSONObject("{\"FirstName\":\""+user.getFirstName()+"\",\"LastName\":\""+user.getLastname()+"\",\"Email\":\""+user.getEmail()+"\",\"Password\":\""+user.getPassword()+"\",\"Agency\":{\"Id\":\""+user.getAgencyId()+"\"}}");

            request = new JsonObjectRequest(Request.Method.POST, AllUrls.add_user_url, jsonBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.v("token", response.toString());
                            SharedPreferences pref = context.getSharedPreferences("Hardis", 0);
                            SharedPreferences.Editor editor = pref.edit();
                            try {
                                editor.putString("token", response.getString("token"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            editor.commit();
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
            );
        }
        catch(JSONException e)
        {

        }
        RequestController.getInstance(context).addToRequestQueue(request);
    }

   public static void authenticateUser(final Context context,final User user, final VolleyCallBack callBack) {
        JsonObjectRequest request = null;
        try {
            JSONObject jsonBody = new JSONObject("{\"Email\":\""+user.getEmail()+"\",\"Password\":\""+user.getPassword()+"\"}");

            request = new JsonObjectRequest(Request.Method.POST, AllUrls.authenticate_user_url, jsonBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.v("token", response.toString());
                            SharedPreferences pref = context.getSharedPreferences("Hardis", 0);
                            SharedPreferences.Editor editor = pref.edit();
                            try {
                                editor.putString("token", response.getString("token"));
                                editor.putInt("id", response.getJSONObject("userInfo").getInt("Id"));
                                editor.putString("userName", response.getJSONObject("userInfo").getString("Email"));
                                editor.putString("fullName",response.getJSONObject("userInfo").getString("FirstName")+" " +response.getJSONObject("userInfo").getString("Lastname"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            editor.commit();
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

    public static User getUser(final int userId,final Context context,final VolleyCallBack callBack) {
        final User user;
        StringRequest request = new StringRequest(Request.Method.GET, AllUrls.get_user_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("getUser",response);
                        JSONObject data = null;
                        try {
                            data = new JSONObject(response);
                            for(int i=0;i<data.length();i++) {
                                String firstName = data.getString("FirstName");
                                String lastName = data.getString("Lastname");
                                String userName = data.getString("Email");
                                //user = new User(firstName,lastName,userName);
                                Log.v("result",firstName);
                                Log.v("result",lastName);
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
        public Map<String, String> getParams()  {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id_user", String.valueOf(userId));
        return params;
    }
    };

    RequestController.getInstance(context).addToRequestQueue(request);
    return null;
    }

}

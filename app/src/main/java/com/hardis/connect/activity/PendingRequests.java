package com.hardis.connect.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.hardis.connect.R;
import com.hardis.connect.controller.CovoiturageController;
import com.hardis.connect.controller.VolleyCallBack;
import com.hardis.connect.model.Covoiturage;
import com.hardis.connect.model.CovoiturageOffreItem;
import com.hardis.connect.model.User;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class PendingRequests extends AppCompatActivity {

    private ListView listView;
    private static SimpleAdapter mSchedule=null;
    private List listItem;
    private List<User> users;
    private HashMap<String, String> actualMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_offers);

        listView = (ListView) findViewById(R.id.listView);
        listItem = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        if(bundle== null) return;

        Covoiturage covoiturage = (Covoiturage) getIntent().getSerializableExtra("covoiturage");
        CovoiturageController.getPendingRequest(covoiturage.getId(), getApplicationContext(), new VolleyCallBack() {
            @Override
            public void onSuccess(String result) {
                users=CovoiturageController.getUsers();
                for (int i = 0; i < users.size(); i++) {
                    HashMap map = new HashMap<String, String>();
                    map.put("userName", users.get(i).getEmail());
                    map.put("fullName", users.get(i).getFirstName()+" "+ users.get(i).getLastname());
                    listItem.add(map);
                }
                mSchedule = new SimpleAdapter(getApplicationContext(), listItem, R.layout.pending_requests,
                        new String[]{"userName", "fullName"}, new int[]{R.id.userName, R.id.fullName});
                listView.setAdapter(mSchedule);
                mSchedule.notifyDataSetChanged();
            }

            @Override
            public void onFailed(String result) {

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> a, View v, final int position, long id) {

                actualMap = (HashMap<String, String>) listView.getItemAtPosition(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(PendingRequests.this);
                builder.setMessage("Are you sure you want to accept " + actualMap.get("username") + " ?")
                        .setCancelable(false)
                        .setNegativeButton("Accepter", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                int requestId=users.get(position).getRequestId();
                                launch(requestId,2,position);

                            }
                        })
                        .setNeutralButton("Refuser", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        int requestId=users.get(position).getRequestId();
                                        users.remove(position);
                                        mSchedule.notifyDataSetChanged();
                                        launch(requestId,3,position);
                                    }
                                }
                        )
                        .setPositiveButton("Annuler", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }

    private void launch(int idRequest, int idAction,int position) {
        CovoiturageController.acceptRequest(idRequest,idAction,getApplicationContext());
        users.remove(position);
        mSchedule.notifyDataSetChanged();
    }
}

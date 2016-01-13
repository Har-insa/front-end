package com.hardis.connect.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hardis.connect.R;
import com.hardis.connect.controller.AgencyController;
import com.hardis.connect.controller.CovoiturageController;
import com.hardis.connect.model.Covoiturage;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SearchView search;
    private Button apply;
    private String address="";
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        search = (SearchView) findViewById(R.id.searchView);
        apply = (Button) findViewById(R.id.button);

        int id = search.getContext().getResources().getIdentifier("android:id/search_src_text",null,null);
        textView = (TextView) search.findViewById(id);
        textView.setTextColor(Color.BLACK);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                address = query;
                search.setIconified(true);
                search.clearFocus();
                try {
                    if (!query.isEmpty()) {
                        LatLng latLng = getLocationFromAddress(query);
                        if (latLng != null) {
                            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                        } else {
                            Toast.makeText(getApplicationContext(), "Veuillez saisir une addresse valide!", Toast.LENGTH_LONG);
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Veuillez saisir une addresse!", Toast.LENGTH_LONG);
                    }
                }
                catch(Exception e)
                {

                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (address.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Veuillez choisir une addresse", Toast.LENGTH_LONG);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                    builder.setMessage("Voulez vous choisir " + address + " comme lieu de départ?");
                    builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("address", address);
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish();
                        }
                    });
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
        }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng LYON = new LatLng(45.764043,4.835658999999964);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LYON, 12.0f));


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    Geocoder geoCoder = new Geocoder(MapsActivity.this);
                    try {
                        List<Address> addressList = geoCoder.getFromLocation(latLng.latitude, latLng.longitude, 5);
                        if (addressList != null && addressList.size() > 0) {
                            final String query = addressList.get(0).getAddressLine(0);
                            confirm(query);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
    }

    public LatLng getLocationFromAddress(String strAddress){

            LatLng latLng = null;
            Geocoder geoCoder = new Geocoder(this);
            try {
            List<Address> addressList = geoCoder.getFromLocationName(strAddress, 1);
            if (addressList != null && addressList.size() > 0) {
            double lat = addressList.get(0).getLatitude();
            double lng = addressList.get(0).getLongitude();
            latLng = new LatLng(lat,lng);
            }
            } catch (IOException e) {
            e.printStackTrace();
            }
            return latLng;
            }

    public void confirm(final String query) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
        builder.setMessage("Voulez vous choisir "+query+" comme lieu de départ?");
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
               address = query;
                textView.setText(address);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

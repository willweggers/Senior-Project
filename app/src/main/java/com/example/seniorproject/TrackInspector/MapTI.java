package com.example.seniorproject.TrackInspector;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import com.example.seniorproject.DB.Inspection;
import com.example.seniorproject.DB.LocalDBHelper;
import com.example.seniorproject.MapActivitys;
import com.example.seniorproject.R;
import com.example.seniorproject.TempDB.TempDB;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static java.util.Locale.getDefault;



/*
 * Created by willw on 9/11/2017.
 */

public class MapTI extends MapActivitys {
    //Tag used for log
    private String TAG = "Google Drive Activity";
    //title for generated report
    private String documentTitle = "A title";
    //button that generate report and stores that report into root folder of google drive specified
    private Button endInspectionButton;
    private Button setCurrLoc;
    private Button addDefect;
    private ToggleButton toggleMarkerPlacement;
    private Button saveInspection;
    private Inspection inspection;
    private int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 99;
    public static boolean toggleMarkerOn = true;
    public static int currentNumberOfTrack = 0;
    private LocalDBHelper localDBHelper;
    public static int currentNumberOfSwitches = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_track_inspector);
        endInspectionButton = (Button) findViewById(R.id.endInspection);
        setCurrLoc = (Button) findViewById(R.id.setcurrentlocation);
        addDefect = (Button) findViewById(R.id.adddefect);
        toggleMarkerPlacement = (ToggleButton) findViewById(R.id.placemarkermode);
        saveInspection = (Button) findViewById(R.id.saveinspection);
        localDBHelper = LocalDBHelper.getInstance(this);

        //for map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
//        ActivityCompat.requestPermissions(MapTI.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                MY_PERMISSIONS_ACCESS_FINE_LOCATION);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, this);
        ifSavedInspection();
        setButtons();

    }


    public void setButtons(){
        endInspectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MapTI.this);
                builder.setMessage("Would you like to end inspection?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                               generateReport();
                                currentNumberOfSwitches=0;
                                currentNumberOfTrack=0;

                                localDBHelper.addInspection(HeaderData.inspection);
                                LocalDBHelper.storeDataInSharedPreference(getBaseContext(),"inspection_id", HeaderData.inspection.inspectionNum);
                                markers.clear();
                                numOfMarker = 0;

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                dialog.cancel();
                            }
                        });
                final AlertDialog alert = builder.create();
                alert.show();
               ;

            }
        });
        setCurrLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currLocMarker.remove();
                LatLng curLoc;
                if(LATITUDE != 0.0 && LONGITUDE != 0.0){
                    curLoc = new LatLng(LATITUDE, LONGITUDE);
                }
                //remove this later
                else{
                    curLoc = new LatLng(33.983076, -84.562506);
                }
                currLocMarker = mMap.addMarker(new MarkerOptions().position(curLoc).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)).title("Current Location."));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(curLoc));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));

            }
        });
        addDefect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currMarker != null) {
                    Intent intent = new Intent(MapTI.this, InspectionForm.class);
                    final double lat = currMarker.getPosition().latitude;
                    final double lng = currMarker.getPosition().longitude;
                    intent.putExtra("lat", lat);
                    intent.putExtra("long", lng);
                    startActivity(intent);

                    Thread thread = new Thread() {
                        @Override public void run() {
                            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                            String result = null;
                            try {
                                List<Address> list = geocoder.getFromLocation(lat, lng, 1);
                                if (list != null && list.size() > 0) {
                                    Address address = list.get(0);
                                    // sending back first address line and locality
                                    result = address.getLocality();
                                    //InspectionForm.locationSetString = result;
                                }
                            } catch (IOException e) {
                                Log.e(TAG, "Impossible to connect to Geocoder", e);
                            }
                        }
                    };
                    thread.start();

//

                }
                else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(MapTI.this);
                    builder.setMessage("A new marker needs to be added.")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                    dialog.cancel();
                                }
                            });

                                final AlertDialog alert = builder.create();
                                alert.show();

                }
            }
        });
        toggleMarkerPlacement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toggleMarkerOn) {
                    toggleMarkerPlacement.setChecked(false);
                    toggleMarkerOn = false;
                }
                else{
                    toggleMarkerPlacement.setChecked(true);
                    toggleMarkerOn = true;
                }


            }
        });
        saveInspection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TempDB tempDB = TempDB.getInstance(getBaseContext());
                tempDB.addInspection(inspection);
                Intent intent = new Intent(MapTI.this, MenuTI.class);
                markers.clear();
                numOfMarker = 0;
                startActivity(intent);
            }
        });

    }
    private void generateReport(){
        Intent intent = new Intent(MapTI.this, FormatReport.class);
        startActivity(intent);
    }
    private void ifSavedInspection(){
        if(getIntent().getBooleanExtra("savedinspection",false)){
            ArrayList<Marker> savedMarkers = new ArrayList<>();
            for(int i = 0; i < inspection.defectList.size();i++){
                LatLng curLatLng = new LatLng(inspection.defectList.get(i).latitude, inspection.defectList.get(i).longitude);
                savedMarkers.add(mMap.addMarker(new MarkerOptions().position(curLatLng)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
                        .title(inspection.defectList.get(i).description)));
            }
            markers = savedMarkers;

        }
        else{

        }

    }




}
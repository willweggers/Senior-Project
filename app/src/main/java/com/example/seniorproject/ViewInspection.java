package com.example.seniorproject;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import com.example.seniorproject.DB.Inspection;
import com.example.seniorproject.DB.LocalDBHelper;
import com.example.seniorproject.TempDB.TempDB;
import com.example.seniorproject.TrackInspector.FormatReport;
import com.example.seniorproject.TrackInspector.HeaderData;
import com.example.seniorproject.TrackInspector.InspectionForm;
import com.example.seniorproject.TrackInspector.MapTI;
import com.example.seniorproject.TrackInspector.MenuTI;
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

/**
 * Created by willw on 11/13/2017.
 */

public class ViewInspection extends ViewInspectionMapActivitys {
    //Tag used for log
    private String TAG = "Google Drive Activity";
    //title for generated report
    private String documentTitle = "A title";
    //button that generate report and stores that report into root folder of google drive specified
    private Button toreport;
    private Button toproposal;
    private Button toheader;


    private Inspection inspection;
    private int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 99;

    private LocalDBHelper localDBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_inspection);

        toheader = (Button) findViewById(R.id.toHeaderData);

        toheader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewInspection.this, ViewHeader.class));
                mMap.clear();
            }
        });

        localDBHelper = LocalDBHelper.getInstance(this);

        //for map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);
//        ActivityCompat.requestPermissions(MapTI.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                MY_PERMISSIONS_ACCESS_FINE_LOCATION);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, this);

    }



}

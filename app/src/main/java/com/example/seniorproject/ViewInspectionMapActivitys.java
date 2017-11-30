package com.example.seniorproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.seniorproject.Admin.MenuAdmin;
import com.example.seniorproject.DB.Defect;
import com.example.seniorproject.DB.LocalDBHelper;
import com.example.seniorproject.Manager.MenuManager;
import com.example.seniorproject.TrackInspector.InspectionForm;
import com.example.seniorproject.TrackInspector.MapTI;
import com.example.seniorproject.TrackInspector.MenuTI;
import com.google.android.gms.common.api.BooleanResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Created by willw on 11/28/2017.
 */

public class ViewInspectionMapActivitys extends FragmentActivity implements
        OnMapReadyCallback, android.location.LocationListener{
    //tag used for log errors
    private String TAG = "Google Drive Activity";
    //apicliend for drive

    public static final int REQUEST_CODE_RESOLUTION = 3;
    public GoogleMap mMap;

    public static double LATITUDE;
    public static double LONGITUDE;

    private Handler handler = new Handler();
    private LocalDBHelper localDBHelper;
    private String inspectionID;
    private LatLng latLngfirst;
    private ArrayList<Marker> markers;
    private Button toggle;
    private Button backtomenu;
    private boolean toggleOn;



    @Override
    public void onMapReady(GoogleMap googleMap) {
        checkGPSOn1();
        toggleOn = true;
        markers = new ArrayList<>();
        toggle = (Button) findViewById(R.id.toggleviewbutton);
        backtomenu = (Button) findViewById(R.id.backtomenu);
        backtomenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalDBHelper localDBHelper = LocalDBHelper.getInstance(getApplicationContext());
                String userloggedinto = LocalDBHelper.getDataInSharedPreference(getApplicationContext(),"username");
                String type = FindLocalDBInfo.findType(localDBHelper,userloggedinto);
                if(type.equals(AccountInfo.ADMIN_PREM)){
                    startActivity(new Intent(getApplicationContext(), MenuAdmin.class));
                }else if(type.equals(AccountInfo.MANAGER_PREM)) {
                    startActivity(new Intent(getApplicationContext(), MenuManager.class));
                }else if(type.equals(AccountInfo.TI_PREM)){
                    startActivity(new Intent(getApplicationContext(), MenuTI.class));
                }
                else{
                    startActivity(new Intent(getApplicationContext(),MainActivityLogin.class));
                }
            }
        });
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toggleOn){
                    toggle.setText("View Defect Off");
                    toggleOn = false;
                }
                else{
                    toggle.setText("View Defect On");
                    toggleOn = true;
                }

            }
        });
        inspectionID = LocalDBHelper.getDataInSharedPreference(getApplicationContext(),"inspectionID");
        mMap = googleMap;
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if(toggleOn) {
                    startActivity(new Intent(getApplicationContext(), ViewDefect.class));
                    LocalDBHelper.storeDataInSharedPreference(getApplicationContext(), "lineitem", (String) marker.getTag());
                    mMap.clear();
                }
                return false;
            }
        });
        localDBHelper = LocalDBHelper.getInstance(this);
        ArrayList<Defect> alldefects = localDBHelper.getAllDefects();
        for(int i = 0; i <alldefects.size();i++ ){
            if(alldefects.get(i).inspection_id_num.equals(inspectionID)){
                latLngfirst = new LatLng(alldefects.get(i).latitude, alldefects.get(i).longitude);
                final Marker marker1 = mMap.addMarker(new MarkerOptions().position(latLngfirst).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).title("Line Number: " + alldefects.get(i).lineItem + " | "
                        + "Description of defect: " + alldefects.get(i).description + " | "
                        + "Priority: " + alldefects.get(i).priority));

               marker1.setTag(alldefects.get(i).lineItem);
                markers.add(marker1);
            }
        }
        for(int i = 0; i < markers.size();i++){
            markers.get(i).showInfoWindow();
        }
        moveCamera(latLngfirst);



    }
    private void moveCamera(LatLng latLng){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f), 2000, null);
    }


    //method to show user what is happening on screen
    public void showMessage(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
    public void checkGPSOn1() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            enableGPS2();
        } else {
//            showMessage("GPS enabled");
        }
    }
    public void enableGPS2() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("GPS is disabled. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }
    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }
    @Override
    public void onLocationChanged(Location location) {
        //TODO Auto-generated method stub
        LATITUDE = location.getLatitude();
        LONGITUDE = location.getLongitude();


//        final EditText ed=(EditText)findViewById(R.id.editText);
//        ed.setText(Double.toString(latitude)+ "lon:"+Double.toString(longitude));


        //Log.i("Geo_Location", "Latitude: " + latitude + ", Longitude: " + longitude);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

}

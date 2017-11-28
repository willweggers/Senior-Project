package com.example.seniorproject;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seniorproject.TrackInspector.FormatReport;
import com.example.seniorproject.TrackInspector.InspectionForm;
import com.example.seniorproject.TrackInspector.MapTI;
import com.example.seniorproject.TrackInspector.MenuTI;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.tasks.OnSuccessListener;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created by willw on 9/15/2017.
 * Implements and specifiys base activities used when connecting to a google drive through gmail and google maps.
 */

public class MapActivitys extends FragmentActivity implements
        OnMapReadyCallback, android.location.LocationListener{
    //tag used for log errors
    private String TAG = "Google Drive Activity";
    //apicliend for drive

    public static final int REQUEST_CODE_RESOLUTION = 3;
    public GoogleMap mMap;
    public static Marker currMarker;
    public static Marker currLocMarker;
    public static double LATITUDE;
    public static double LONGITUDE;
//    public static double[] markerLatLong = new double[1000];
    public static ArrayList<Marker> markers = new ArrayList<>();
    public static ArrayList<LatLng> latlngMarkers = new ArrayList<>();
    public static int numOfMarker = 0;
    private LatLng curLoc;
    private Handler handler = new Handler();



    @Override
    public void onMapReady(GoogleMap googleMap) {
        checkGPSOn();
        mMap = googleMap;
//        markerLatLong[0] = LATITUDE;
//        markerLatLong[1] = LONGITUDE;
        if(LATITUDE != 0.0 && LONGITUDE != 0.0){
            curLoc = new LatLng(LATITUDE, LONGITUDE);
        }
        //remove this later
        else{
            curLoc = new LatLng(33.983076, -84.562506);
        }
        if(currLocMarker != null){
            currLocMarker.remove();
        }
        currLocMarker = mMap.addMarker(new MarkerOptions().position(curLoc).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)).title("Current Location."));
        moveCamera();
        if(markers.size() !=0){
            for(int i = 0; i < markers.size();i++){
                if(InspectionForm.descriptionString != null) {
                    if (InspectionForm.trackCheck.isChecked()) {
                        mMap.addMarker(new MarkerOptions().position(markers.get(i).getPosition()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).title(InspectionForm.allDefectsDescriptions.get(i)));
                    }
                    else if(InspectionForm.switchCheck.isChecked()){
                        mMap.addMarker(new MarkerOptions().position(markers.get(i).getPosition()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).title(InspectionForm.allDefectsDescriptions.get(i)));

                    }
                }
                else{
                    mMap.addMarker(new MarkerOptions().position(markers.get(i).getPosition()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                }
            }
        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latlng) {
                if(MapTI.toggleMarkerOn){

                    if(markers.size() > numOfMarker){
                        markers.get(numOfMarker).remove();
                        markers.remove(numOfMarker);

                    }
                    currMarker = mMap.addMarker(new MarkerOptions()
                            .position(latlng)
                            .icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                    markers.add(numOfMarker, currMarker);
//                    markerLatLong[0] = currMarker.getPosition().latitude;
//                    markerLatLong[1] = currMarker.getPosition().longitude;

                }
                else if(!MapTI.toggleMarkerOn){
                    //could put something here idk
                }

            }
        });
    }
    private void moveCamera(){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curLoc,15));
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f), 2000, null);
    }


    //method to show user what is happening on screen
    public void showMessage(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
    public void checkGPSOn() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            enableGPS();
        } else {
//            showMessage("GPS enabled");
        }
    }

//    public double[] getMarkerLoc()
//    {
//        return markerLatLong;
//    }

    public void setMarkerLoc(double[] pos)
    {
        LatLng sentLoc;
        sentLoc = new LatLng(pos[0], pos[1]);
        currMarker = mMap.addMarker(new MarkerOptions()
                .position(sentLoc)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        markers.add(numOfMarker, currMarker);
//        markerLatLong[0] = currMarker.getPosition().latitude;
//        markerLatLong[1] = currMarker.getPosition().longitude;
    }

    public void enableGPS() {
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

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
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
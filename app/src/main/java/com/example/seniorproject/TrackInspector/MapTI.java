package com.example.seniorproject.TrackInspector;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import com.example.seniorproject.MapActivitys;
import com.example.seniorproject.R;
import com.google.android.gms.maps.SupportMapFragment;



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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_track_inspector);
        endInspectionButton = (Button) findViewById(R.id.endInspection);
        setCurrLoc = (Button) findViewById(R.id.setcurrentlocation);
        addDefect = (Button) findViewById(R.id.adddefect);

        //for map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

            }
        });
        addDefect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapTI.this, InspectionForm.class);
                startActivity(intent);
            }
        });
    }
    private void generateReport(){
        Intent intent = new Intent(MapTI.this, FormatReport.class);
        startActivity(intent);
    }



}
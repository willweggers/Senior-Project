package com.example.seniorproject.TrackInspector;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.seniorproject.BaseDriveMapActivitys;
import com.example.seniorproject.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*
 * Created by willw on 9/11/2017.
 */

public class MapTI extends BaseDriveMapActivitys {
    //Tag used for log
    private String TAG = "Google Drive Activity";
    //title for generated report
    private String documentTitle = "A title";
    //button that generate report and stores that report into root folder of google drive specified
    private Button endInspectionButton;
    private Button setCurrLoc;
    private Button addDefect;
    // activity result key for camera
    static final int REQUEST_TAKE_PHOTO = 1;
    // absolute path for camera images
    String myPicPath;


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
                                Drive.DriveApi.newDriveContents(getGoogleApi())
                                        .setResultCallback(driveContentsCallback);
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
                Intent intent = new Intent(MapTI.this, DefectReport.class);
                startActivity(intent);
            }
        });
    }


    //https://developers.google.com/drive/android/create-file
    //above link for specifics on what is going on below
    final private ResultCallback<DriveApi.DriveContentsResult> driveContentsCallback = new
            ResultCallback<DriveApi.DriveContentsResult>() {
                @Override
                public void onResult(DriveApi.DriveContentsResult result) {
                    if (!result.getStatus().isSuccess()) {
                        showMessage("Error while trying to create new file contents");
                        return;
                    }
                    final DriveContents driveContents = result.getDriveContents();

                    // Perform I/O off the UI thread.
                    new Thread() {
                        @Override
                        public void run() {
                            // write content to DriveContents
                            OutputStream outputStream = driveContents.getOutputStream();
                            Writer writer = new OutputStreamWriter(outputStream);
                            try {
                                //what is written
                                //might use something that can help format the report properly
                                writer.write("hey");
                                writer.close();
                            } catch (IOException e) {
                                Log.e(TAG, e.getMessage());
                            }

                            MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                                    .setTitle(documentTitle)
                                    .setMimeType("text/plain")
                                    .setStarred(true).build();

                            // create a file on root folder
                            Drive.DriveApi.getRootFolder(getGoogleApi())
                                    .createFile(getGoogleApi(), changeSet, driveContents)
                                    .setResultCallback(fileCallback);
                        }
                    }.start();
                }
            };

    final private ResultCallback<DriveFolder.DriveFileResult> fileCallback = new
            ResultCallback<DriveFolder.DriveFileResult>() {
                @Override
                public void onResult(DriveFolder.DriveFileResult result) {
                    if (!result.getStatus().isSuccess()) {
                        showMessage("Error while trying to create the file");
                        return;
                    }
                    showMessage("Report was created and stored in google drive.");
                }
            };


}
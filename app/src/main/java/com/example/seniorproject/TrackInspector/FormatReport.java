package com.example.seniorproject.TrackInspector;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.BundleCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.Space;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.seniorproject.DriveActivitys;
import com.example.seniorproject.R;
import com.example.seniorproject.TrackInspector.HeaderData;
import com.example.seniorproject.TrackInspector.MapTI;
import com.example.seniorproject.TrackInspector.MenuTI;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.MetadataChangeSet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.logging.LogRecord;

/**
 * Created by willw on 10/11/2017.
 * Formats viewable report at end of inspection. if id for either switch or track is entered all other columns currently have to be entered
 * or it will give nullpointer error.
 */

public class FormatReport extends DriveActivitys {
    public String theCompanyName;
    public  String theStreetAddress;
    public  String theCityStateZip;

    public  ArrayList<String> trackIDs= new ArrayList<>();
    public  ArrayList<String> trackNumber= new ArrayList<>();
    public  ArrayList<String> trackLocations= new ArrayList<>();
    public  ArrayList<String> trackDescriptions= new ArrayList<>();
    public  ArrayList<String> trackUnits= new ArrayList<>();
    public  ArrayList<String> trackQuantitys= new ArrayList<>();
    public  ArrayList<String> trackPriority= new ArrayList<>();
    public  ArrayList<ArrayList<String>> trackArrayList = new ArrayList<>();


    public ArrayList<String> switchIDs= new ArrayList<>();
    public ArrayList<String> switchNumber= new ArrayList<>();
    public ArrayList<String> switchLocations= new ArrayList<>();
    public ArrayList<String> switchDescriptions= new ArrayList<>();
    public ArrayList<String> switchUnits= new ArrayList<>();
    public ArrayList<String> switchQuantitys= new ArrayList<>();
    public ArrayList<String> switchPriority= new ArrayList<>();
    public ArrayList<ArrayList<String>> switchArrayList = new ArrayList<>();

    private ConstraintLayout constraintLayout;
    public String documentTitle;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inspection_report_format);
        constraintLayout = (ConstraintLayout)findViewById(R.id.CLInspectionReport);
        setData();

    }

    private void setData(){
        setHeaderData();
        setDefectData();


        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drive.DriveApi.newDriveContents(getGoogleApi())
                .setResultCallback(driveContentsCallback);
                //adding delay
                handler.postDelayed(mLaunchTask,1500);
            }
        });
    }
    private Runnable mLaunchTask = new Runnable() {
        public void run() {
            Intent i = new Intent(FormatReport.this, MenuTI.class);
            startActivity(i);
        }
    };

    private void addArrayLists() {
        Collections.addAll(trackArrayList,trackIDs,
                trackNumber,
                trackLocations,
                trackDescriptions,
                trackUnits,
                trackQuantitys,
                trackPriority);
        Collections.addAll(switchArrayList, switchIDs,
                switchNumber,
                switchLocations,
                switchDescriptions,
                switchUnits,
                switchQuantitys,
                switchPriority);
    }

    private void setHeaderData(){
        theCompanyName = "Track Inspection for ".concat(HeaderData.thecompanyname);
        theStreetAddress = HeaderData.theaddress;
        theCityStateZip = HeaderData.thelocation;
        TextView companyname = (TextView) findViewById(R.id.companyName);
        companyname.setText(theCompanyName);
        TextView streetaddress = (TextView) findViewById(R.id.streetAddress);
        streetaddress.setText(theStreetAddress);
        TextView citystatezip = (TextView) findViewById(R.id.cityStateZipCode);
        citystatezip.setText(theCityStateZip);
    }
    private void setDefectData(){
        trackIDs= InspectionForm.trackIDs1;
        trackNumber= InspectionForm.trackNumber1;
        trackLocations= InspectionForm.trackLocations1;
        trackDescriptions =InspectionForm.trackDescriptions1;
        trackUnits=InspectionForm.trackUnits1;
        trackQuantitys= InspectionForm.trackQuantitys1;
        trackPriority= InspectionForm.trackPriority1;
        addArrayLists();
        int numOfTrackRows = trackIDs.size();
        int numOfSwitchRows = switchIDs.size();
        TableLayout tracklayout = (TableLayout) findViewById(R.id.trackTable);

        int numOfElementsPerRowTrack =trackArrayList.size();
        for(int i = 0; i < numOfTrackRows;i++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

            for(int j = 0; j < numOfElementsPerRowTrack; j++){
                TextView textView = new TextView(this);
                textView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                if(trackArrayList.get(j).get(i) == null){
                    textView.setText(null);
                }
                else {
                    textView.setText(trackArrayList.get(j).get(i));
                }
                if(j == 5 || j == 1 || j == 4||j==6){
                    textView.setGravity(Gravity.CENTER);
                }
                textView.setTextSize(20);
                textView.setPadding(0,0,30,0);
                tableRow.addView(textView);
            }
            tracklayout.addView(tableRow);
        }
        TableLayout switchlayout = (TableLayout) findViewById(R.id.switchTable);
        switchIDs= InspectionForm.switchIDs1;
        switchNumber= InspectionForm.switchNumber1;
        switchLocations= InspectionForm.switchLocations1;
        switchDescriptions =InspectionForm.switchDescriptions1;
        switchUnits=InspectionForm.switchUnits1;
        switchQuantitys= InspectionForm.switchQuantitys1;
        switchPriority= InspectionForm.switchPriority1;
        int numOfElementsPerRowSwitch = switchArrayList.size();
        for(int i = 0; i < numOfSwitchRows;i++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

            for(int j = 0; j < numOfElementsPerRowSwitch; j++){
                TextView textView = new TextView(this);
                textView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                textView.setText(switchArrayList.get(j).get(i));
                if(j == 5 || j == 1 || j == 4){
                    textView.setGravity(Gravity.CENTER);
                }
                textView.setTextSize(20);
                textView.setPadding(0,0,50,0);
                tableRow.addView(textView);
            }
            switchlayout.addView(tableRow);
        }
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
//                    File pngLogo = new File("logopng.png");
                    // Perform I/O off the UI thread.
                    new Thread() {
                        @Override
                        public void run() {

                            Bitmap bitmap = layoutToImageReport();
//                            int bytes = bitmap.getByteCount();
//                            java.nio.ByteBuffer byteBuffer = java.nio.ByteBuffer.allocate(bytes);
//                            bitmap.copyPixelsToBuffer(byteBuffer);
//                            byte[] bitMapData = byteBuffer.array();
//
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                            byte[] bitMapData = stream.toByteArray();

                            OutputStream outputStream = driveContents.getOutputStream();
//                            Writer writer = new OutputStreamWriter(outputStream);
                            try {
                                //what is written
                                //might use something that can help format the report properly
                                outputStream.write(bitMapData);
                                outputStream.close();
//                                writer.write("some stuff");
//                                writer.close();
                            } catch (IOException e) {
                            }
                            //https://developers.google.com/drive/v3/web/integrate-open
                            MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                                    .setTitle(documentTitle)
                                    .setMimeType("image/jpeg")
                                    //.setStarred(true)
                                    //.setDescription("Desciptiong of file")                                    .
                                    .build();


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

    public Bitmap layoutToImageReport() {
        constraintLayout.setDrawingCacheEnabled(true);
        constraintLayout.buildDrawingCache();
        Bitmap b = constraintLayout.getDrawingCache();

//        ConstraintLayout childLayout = (ConstraintLayout) findViewById(R.id.CLInspectionReport);
//        Bitmap b;
//        //should trigger this since inspection report format isnt ever viewed
//        if (childLayout.getMeasuredHeight() <= 0) {
//            constraintLayout.measure(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
//////
////            int specWidth = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
////            constraintLayout.measure(specWidth, specWidth);
//            b = Bitmap.createBitmap(constraintLayout.getMeasuredWidth(), constraintLayout.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
//            Canvas c = new Canvas(b);
//            constraintLayout.layout(0, 0, constraintLayout.getMeasuredWidth(), constraintLayout.getMeasuredHeight());
//            constraintLayout.draw(c);
//
////        ConstraintLayout view = (ConstraintLayout) inflatedFrame.findViewById(R.id.CLInspectionReport);
////        view.setDrawingCacheEnabled(true);
////        view.buildDrawingCache();
////        Bitmap bm = view.getDrawingCache();
//        }//incase we want it to be viewed
//        else{
////            b = Bitmap.createBitmap(constraintLayout.getLayoutParams().width, constraintLayout.getLayoutParams().height, Bitmap.Config.ARGB_8888);
//            Canvas c = new Canvas(b);
////            constraintLayout.layout(constraintLayout.getLeft(), constraintLayout.getTop(), constraintLayout.getRight(), constraintLayout.getBottom());
//            constraintLayout.draw(c);
//        }

        return b;
    }


}

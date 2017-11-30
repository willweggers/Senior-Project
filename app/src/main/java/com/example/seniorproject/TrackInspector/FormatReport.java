package com.example.seniorproject.TrackInspector;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.seniorproject.AccountInfo;
import com.example.seniorproject.Admin.MenuAdmin;
import com.example.seniorproject.DB.Inspection;
import com.example.seniorproject.DB.LocalDBHelper;
import com.example.seniorproject.DriveActivitys;
import com.example.seniorproject.MainActivityLogin;
import com.example.seniorproject.Manager.MenuManager;
import com.example.seniorproject.R;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.MetadataChangeSet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;

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

    public String documentTitle;
    private Handler handler = new Handler();


    private LinearLayout constraintLayout;
    private Button sendToDrive;

    private LocalDBHelper localDBHelper;


    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inspection_report_format);
        constraintLayout = (LinearLayout) findViewById(R.id.CLInspectionReport);
        sendToDrive = (Button) findViewById(R.id.sendToDriveReport);
        localDBHelper = LocalDBHelper.getInstance(this);
        username = LocalDBHelper.getDataInSharedPreference(this,"username");
        setData();

    }

    private void setData(){
        setHeaderData();
        setDefectData();

        try {
            documentTitle = HeaderData.inspection.inspectionDate.toString().concat(" - ").concat(HeaderData.inspection.inspectorID);
        }
        catch (NullPointerException e){
            documentTitle = "title error";
        }
        sendToDrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToDrive.setVisibility(View.INVISIBLE);
                Drive.DriveApi.newDriveContents(getGoogleApi())
                .setResultCallback(driveContentsCallback);
                emptyStuff();
                //adding delay
                handler.postDelayed(mLaunchTask,1500);
            }
        });
    }
    private Runnable mLaunchTask = new Runnable() {
        public void run() {
            String accountType = localDBHelper.getAccountByUser(username).accountType;

            try {
                if (accountType.equals(AccountInfo.MANAGER_PREM)) {
                    emptyStuff();
                    Intent i = new Intent(FormatReport.this, MenuManager.class);
                    startActivity(i);
                } else if (accountType.equals(AccountInfo.TI_PREM)) {
                    emptyStuff();
                    Intent i = new Intent(FormatReport.this, MenuTI.class);
                    startActivity(i);
                } else {
                    emptyStuff();
                    Intent i = new Intent(FormatReport.this, MainActivityLogin.class);
                    startActivity(i);
                }
            }catch (NullPointerException e){
                emptyStuff();
                Intent i = new Intent(FormatReport.this, MainActivityLogin.class);
                startActivity(i);
            }
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
            try {
                theCompanyName = "Track Inspection for ".concat(HeaderData.inspection.contact);
                theStreetAddress = HeaderData.inspection.address;
                theCityStateZip = HeaderData.inspection.city.concat(", ").concat(HeaderData.inspection.state).concat(" ").concat(HeaderData.inspection.zip);
            }
            catch (NullPointerException e){
                theCompanyName = "Track Inspection for company name not entered.";
                theStreetAddress = "Street Address Not Found";
                theCityStateZip = "City, State and Zip not found";
            }


        TextView companyname = (TextView) findViewById(R.id.companyName);
        companyname.setText(theCompanyName);
        TextView streetaddress = (TextView) findViewById(R.id.streetAddress);
        streetaddress.setText(theStreetAddress);
        TextView citystatezip = (TextView) findViewById(R.id.cityStateZipCode);
        citystatezip.setText(theCityStateZip);
    }
    private void setDefectData(){
        for(int i = 0; i < HeaderData.inspection.defectList.size();i++){
            if(HeaderData.inspection.defectList.get(i).lineItem.contains("T")){
                trackIDs.add(HeaderData.inspection.defectList.get(i).lineItem);
                trackNumber.add(HeaderData.inspection.defectList.get(i).trackNumber);
                trackLocations.add(HeaderData.inspection.defectList.get(i).location);
                trackDescriptions.add(HeaderData.inspection.defectList.get(i).description);
                trackUnits.add(HeaderData.inspection.defectList.get(i).unit);
                trackQuantitys.add(Integer.toString(HeaderData.inspection.defectList.get(i).quantity));
                trackPriority.add(Integer.toString(HeaderData.inspection.defectList.get(i).priority));
            }else if(HeaderData.inspection.defectList.get(i).lineItem.contains("S")){
                switchIDs.add(HeaderData.inspection.defectList.get(i).lineItem);
                switchNumber.add(HeaderData.inspection.defectList.get(i).trackNumber);
                switchLocations.add(HeaderData.inspection.defectList.get(i).location);
                switchDescriptions.add(HeaderData.inspection.defectList.get(i).description);
                switchUnits.add(HeaderData.inspection.defectList.get(i).unit);
                switchQuantitys.add(Integer.toString(HeaderData.inspection.defectList.get(i).quantity));
                switchPriority.add(Integer.toString(HeaderData.inspection.defectList.get(i).priority));
            }
        }


        addArrayLists();
        int numOfTrackRows = trackIDs.size();
        int numOfSwitchRows = switchIDs.size();

        TableLayout tracklayout = (TableLayout) findViewById(R.id.trackTable);
        if(numOfTrackRows == 0){
            tracklayout.setVisibility(View.INVISIBLE);
        }
        int numOfElementsPerRowTrack =trackArrayList.size();
        for(int i = 0; i < numOfTrackRows;i++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

            for(int j = 0; j < numOfElementsPerRowTrack; j++){
                TextView textView = new TextView(this);
                if(j == 2){
                    textView.setLayoutParams(new TableRow.LayoutParams(0,TableRow.LayoutParams.WRAP_CONTENT,3.0f));
                }
                else if(j == 3){
                    textView.setLayoutParams(new TableRow.LayoutParams(0,TableRow.LayoutParams.WRAP_CONTENT,2.0f));

                }
                else {
                    textView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
                }
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
//                textView.setPadding(0,0,30,0);
                tableRow.addView(textView);
            }
            tracklayout.addView(tableRow);
        }
        TableLayout switchlayout = (TableLayout) findViewById(R.id.switchTable);
        if(numOfSwitchRows == 0){
            switchlayout.setVisibility(View.INVISIBLE);
        }
        int numOfElementsPerRowSwitch = switchArrayList.size();
        for(int i = 0; i < numOfSwitchRows;i++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

            for(int j = 0; j < numOfElementsPerRowSwitch; j++){
                TextView textView = new TextView(this);
                if(j == 2){
                    textView.setLayoutParams(new TableRow.LayoutParams(0,TableRow.LayoutParams.WRAP_CONTENT,3.0f));
                }
                else if(j == 3){
                    textView.setLayoutParams(new TableRow.LayoutParams(0,TableRow.LayoutParams.WRAP_CONTENT,2.0f));

                }
                else {
                    textView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
                }
                textView.setText(switchArrayList.get(j).get(i));
                if(j == 5 || j == 1 || j == 4){
                    textView.setGravity(Gravity.CENTER);
                }
                textView.setTextSize(20);
//                textView.setPadding(0,0,50,0);
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
                            Drawable d = getResources().getDrawable(R.drawable.rsz_1rsz_ameritracklogoreal);
//                            Bitmap icon = drawableToBitmap(d);
                            Bitmap icon = layoutToImageReport();

////
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            icon.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                            byte[] bitMapData = stream.toByteArray();
                            OutputStream outputStream = driveContents.getOutputStream();
                            Writer writer = new OutputStreamWriter(outputStream);
                            try {
                                //what is written
                                //might use something that can help format the report properly
                                outputStream.write(bitMapData);
                                outputStream.close();
//                                writer.write(imageEncoded);
//                                writer.close();

                            } catch (IOException e) {
                            }
                            //https://developers.google.com/drive/v3/web/integrate-open
                            //https://www.sitepoint.com/mime-types-complete-list/
                            MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                                    .setTitle(documentTitle)
                                    .setMimeType("image/JPEG")
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
    //https://stackoverflow.com/questions/3035692/how-to-convert-a-drawable-to-a-bitmap/3035869#3035869
    private Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
    private void emptyStuff(){
        trackArrayList.clear();
                trackIDs.clear();
                trackNumber.clear();
                trackLocations.clear();
                trackDescriptions.clear();
                trackUnits.clear();
                trackQuantitys.clear();
                trackPriority.clear();
        switchArrayList.clear();
                switchIDs.clear();
                switchNumber.clear();
                switchLocations.clear();
                switchDescriptions.clear();
                switchUnits.clear();
                switchQuantitys.clear();
                switchPriority.clear();
    }


}

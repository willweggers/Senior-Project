package com.example.seniorproject.TrackInspector;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import com.example.seniorproject.AccountInfo;
import com.example.seniorproject.DB.Defect;
import com.example.seniorproject.DB.Inspection;
import com.example.seniorproject.DB.LocalDBHelper;
import com.example.seniorproject.DB.MasterFileObjects.CrossingsFile;
import com.example.seniorproject.DB.MasterFileObjects.CrosstiesFile;
import com.example.seniorproject.DB.MasterFileObjects.IssueFile;
import com.example.seniorproject.DB.MasterFileObjects.LaborInstallFile;
import com.example.seniorproject.DB.MasterFileObjects.OTMFile;
import com.example.seniorproject.DB.MasterFileObjects.OtherFile;
import com.example.seniorproject.DB.MasterFileObjects.PriorityFile;
import com.example.seniorproject.DB.MasterFileObjects.RailFile;
import com.example.seniorproject.DB.MasterFileObjects.SwitchTiesFile;
import com.example.seniorproject.DB.MasterFileObjects.TurnoutsFile;
import com.example.seniorproject.MapActivitys;
import com.example.seniorproject.NothingSelectedSpinnerAdapter;
import com.example.seniorproject.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by willw on 10/6/2017.
 */

public class InspectionForm extends AppCompatActivity {
    // button that opens camera app

    // thumbnail image returned by camera app
    private ImageView cameraButton;
    // activity result key for camera
    static final int REQUEST_TAKE_PHOTO = 1;
    Uri imageFileUri;
    // absolute path for camera images
    String myPicPath;
    String anotherPicPath;
    //    private ImageView imageView;
    Bitmap mImageBitmap;

    public static ArrayList<String> trackIDs1= new ArrayList<>();
    public static ArrayList<String> trackNumber1= new ArrayList<>();
    public static ArrayList<String> trackLocations1= new ArrayList<>();
    public static ArrayList<String> trackDescriptions1= new ArrayList<>();
    public static ArrayList<String> trackUnits1= new ArrayList<>();
    public static ArrayList<String> trackQuantitys1= new ArrayList<>();
    public static ArrayList<String> trackPriority1= new ArrayList<>();
    public static ArrayList<Bitmap> imageBitmaps = new ArrayList<>();


    public static ArrayList<String> switchIDs1= new ArrayList<>();
    public static ArrayList<String> switchNumber1= new ArrayList<>();
    public static ArrayList<String> switchLocations1= new ArrayList<>();
    public static ArrayList<String> switchDescriptions1= new ArrayList<>();
    public static ArrayList<String> switchUnits1= new ArrayList<>();
    public static ArrayList<String> switchQuantitys1= new ArrayList<>();
    public static ArrayList<String> switchPriority1= new ArrayList<>();
    public static String trackString;
    public static String laborString;
    public static String locString;
    public static String categoryString;
    public static String codeString;
    public static String descriptionString;
    public static String quantityString;
    public static String priorityString;
    public static String unitString;
    public static String codeDescriptionString;
    private TextView switchTrackNumberTextField;
    public static CheckBox trackCheck;
    public static CheckBox switchCheck;
    private EditText trackNumberEdit;
    private EditText quantityEdit;
    private Spinner defectDescriptionEdit;
    private Spinner laborEdit;
    private Spinner catergoryEdit;
    private Spinner codeEdit;
    private Spinner priorityEdit;
    private EditText locationEdit;
//    private EditText codeDescriptionEdit;
    private Button sumbitDefectButton;
    public static ArrayList<String> allDefectsDescriptions = new ArrayList<>();
    public static ArrayList<String> alllinenumbers = new ArrayList<>();
    public static ArrayList<String> allpriorities = new ArrayList<>();


    public static ArrayList<String> listCommonDefects = new ArrayList<>();
    public static ArrayList<String> listlaborcodes = new ArrayList<>();
    public static ArrayList<String> listcategorys= new ArrayList<>();
    public static ArrayList<String> listthatcategoryscodes = new ArrayList<>();
    public static ArrayList<String> listprioritys = new ArrayList<>();
    public static ArrayList<String> listunits = new ArrayList<>();
    public LocalDBHelper localDBHelper;
    public String[] categories;

    private ArrayList<RailFile> railFiles;
    private ArrayList<CrossingsFile> crossingsFiles;
    private ArrayList<CrosstiesFile> crosstiesFiles;
    private ArrayList<IssueFile> issueFiles;
    private ArrayList<OtherFile> otherFiles;
    private ArrayList<OTMFile> OTMFiles;
    private ArrayList<SwitchTiesFile> switchFiles;
    private ArrayList<TurnoutsFile> turnoutFiles;
    private ArrayList<LaborInstallFile> allLaborFiles;

    private int Crewrate;
    private int production;

    public static String locationSetString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.defect_report_track_inspector);
        switchTrackNumberTextField = (TextView) findViewById(R.id.trackswitchnumber);
        trackCheck = (CheckBox) findViewById(R.id.trackcheckbox);
        switchCheck = (CheckBox) findViewById(R.id.switchcheckbox);
        trackNumberEdit = (EditText) findViewById(R.id.trackturnoutnumber);
        quantityEdit = (EditText) findViewById(R.id.quantityedit);
        localDBHelper = LocalDBHelper.getInstance(this);
//        codeDescriptionEdit = (EditText) findViewById(R.id.codedescnedit);
        //adding temp defects and other
        listCommonDefects.add("Broken Base");
        listCommonDefects.add("Broken Rail Joint Area");
        listCommonDefects.add("Broken Rail");
        listCommonDefects.add("Engine Burn Fracture");
        listCommonDefects.add("Compound Fissure");
        listCommonDefects.add("Detail Fracture");
        listCommonDefects.add("Thermite Weld Boutet(Wide Gap");
        listCommonDefects.add("Vertical Split Head Joint Area(Outside Joint Area");
        listCommonDefects.add("Other");
        if (android.os.Build.VERSION.SDK_INT >= 24){
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }


        //temp adding arraylist for other spinners for testing remove when master data files are a thing
        ArrayList<LaborInstallFile> laborInstallFiles = localDBHelper.getAllLaborFiles();
        for(int i = 0; i < laborInstallFiles.size();i++) {
            listlaborcodes.add(laborInstallFiles.get(i).theID);
        }
       categories = new String[]{"RAIL","CROSSTIES","CROSSINGS","SWITCHTIE","OTM","TURNOUT","OTHER","ISSUES"};
        for(int i = 0; i < categories.length;i++) {
            listcategorys.add(categories[i]);
        }
        //temp
        listthatcategoryscodes.add("category code");
        ArrayList<PriorityFile> priorityFiles = localDBHelper.getAllPriorityFiles();
        for(int i= 0; i < priorityFiles.size();i++){
            listprioritys.add(Integer.toString(priorityFiles.get(i).theID));
        }

        listunits.add("units");
        //listcommondefects
        defectDescriptionEdit = (Spinner) findViewById(R.id.listofcommondefects);
        ArrayAdapter<String> defectAdaptar = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listCommonDefects);
        defectDescriptionEdit.setPrompt("[Select a common defect or select \"other\" below...]");
        defectDescriptionEdit.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        defectAdaptar,
                        R.layout.contact_spinner_row_nothing_selected_defect,
                        this));
        laborEdit = (Spinner) findViewById(R.id.laborlist);
        ArrayAdapter<String> laborAdaptar = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listlaborcodes);
        laborEdit.setPrompt("[Select a labor code below...]");
        laborEdit.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        laborAdaptar,
                        R.layout.contact_spinner_row_nothing_selected_labor,
                        this));
        catergoryEdit = (Spinner) findViewById(R.id.categorylist);
        ArrayAdapter<String> categoryAdaptar = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listcategorys);
        catergoryEdit.setPrompt("[Select a category for this defect below...]");
        catergoryEdit.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        categoryAdaptar,
                        R.layout.contact_spinner_row_nothing_selected_category,
                        this));
        codeEdit = (Spinner) findViewById(R.id.codecategorylist);
        ArrayAdapter<String> codeAdaptar = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listthatcategoryscodes);
        codeEdit.setPrompt("[Select the code from the category below...]");
        codeEdit.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        codeAdaptar,
                        R.layout.contact_spinner_row_nothing_selected_category_code,
                        this));
        priorityEdit = (Spinner) findViewById(R.id.prioritylist);
        ArrayAdapter<String> priorityAdaptar = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listprioritys);
        priorityEdit.setPrompt("[Select the priority of defect...]");
        priorityEdit.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        priorityAdaptar,
                        R.layout.contact_spinner_row_nothing_selected_priority,
                        this));

        locationEdit = (EditText) findViewById(R.id.locationedit);
        sumbitDefectButton = (Button) findViewById(R.id.adddefectsubmit);
        cameraButton = (ImageView) findViewById(R.id.clickheretotakepicofdefect) ;
        onItemSelectedDefect();

        setCheckboxes();
        setButtons();

    }
    private void setCheckboxes(){
        trackCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    switchCheck.setChecked(false);
                    int currentNumTracks = MapTI.currentNumberOfTrack+ 1;
                    String theText = "T0".concat(Integer.toString(currentNumTracks));
                    switchTrackNumberTextField.setText(theText);
                }
            }
        });
        switchCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    trackCheck.setChecked(false);
                    int currentNumSwitches = MapTI.currentNumberOfSwitches + 1;
                    String theText = "S0".concat(Integer.toString(currentNumSwitches));
                    switchTrackNumberTextField.setText(theText);
                }
            }
        });
    }

    private void setButtons(){
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        sumbitDefectButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                setText();
                if((descriptionString.isEmpty() && descriptionString.equals(null)) ||
                    (laborString.isEmpty() && laborString.equals(null))||
                            (categoryString.isEmpty() && categoryString.equals(null)) ||
                    (codeString.isEmpty() && codeString.equals(null))||
                (priorityString.isEmpty() && priorityString.equals(null))){
                    AccountInfo.showMessage("Dropdown isnt set", getApplicationContext());

                }
                else {
                    if(categoryString.equals("RAIL")){
                        for(int i = 0; i < railFiles.size();i++){
                            if(railFiles.get(i).theID.equals(codeString)){
                                unitString = railFiles.get(i).theUnit;
                                codeDescriptionString = railFiles.get(i).thedescription;
                                production = railFiles.get(i).theProduction;
                            }
                        }
                    }else if(categoryString.equals("CROSSTIES")){
                        for(int i = 0; i < crosstiesFiles.size();i++){
                            if(crosstiesFiles.get(i).theID.equals(codeString)){
                                unitString = crosstiesFiles.get(i).theUnit;
                                codeDescriptionString = crosstiesFiles.get(i).thedescription;
                                production = crosstiesFiles.get(i).theProduction;

                            }
                        }
                    }else if(categoryString.equals("CROSSINGS")){
                        for(int i = 0; i < crossingsFiles.size();i++){
                            if(crossingsFiles.get(i).theID.equals(codeString)){
                                unitString = crossingsFiles.get(i).theUnit;
                                codeDescriptionString = crossingsFiles.get(i).thedescription;
                                production = crossingsFiles.get(i).theProduction;

                            }
                        }
                    }else if(categoryString.equals("OTM")){
                        for(int i = 0; i < OTMFiles.size();i++){
                            if(OTMFiles.get(i).theID.equals(codeString)){
                                unitString = OTMFiles.get(i).theUnit;
                                codeDescriptionString = OTMFiles.get(i).thedescription;
                                production = OTMFiles.get(i).theProduction;

                            }
                        }
                    }else if(categoryString.equals("OTHER")){
                        for(int i = 0; i < otherFiles.size();i++){
                            if(otherFiles.get(i).theID.equals(codeString)){
                                unitString = otherFiles.get(i).theUnit;
                                codeDescriptionString = otherFiles.get(i).thedescription;
                                production = otherFiles.get(i).theProduction;

                            }
                        }
                    }else if(categoryString.equals("SWITCHETIE")){
                        for(int i = 0; i < switchFiles.size();i++){
                            if(switchFiles.get(i).theID.equals(codeString)){
                                unitString = switchFiles.get(i).theUnit;
                                codeDescriptionString = switchFiles.get(i).thedescription;
                                production = switchFiles.get(i).theProduction;

                            }
                        }
                    }else if(categoryString.equals("ISSUES")){
                        for(int i = 0; i < issueFiles.size();i++){
                            if(issueFiles.get(i).theID.equals(codeString)){
                                unitString = issueFiles.get(i).theUnit;
                                codeDescriptionString = issueFiles.get(i).thedescription;
                                production = issueFiles.get(i).theProduction;

                            }
                        }
                    }else if(categoryString.equals("TURNOUT")){
                        for(int i = 0; i < turnoutFiles.size();i++){
                            if(turnoutFiles.get(i).theID.equals(codeString)){
                                unitString = turnoutFiles.get(i).theUnit;
                                codeDescriptionString = turnoutFiles.get(i).thedescription;
                                production = turnoutFiles.get(i).theProduction;

                            }
                        }
                    }
                    allLaborFiles = localDBHelper.getAllLaborFiles();
                    for (int i = 0; i < allLaborFiles.size(); i++) {
                        if (allLaborFiles.get(i).theID.equals(laborString)) {
                            Crewrate = allLaborFiles.get(i).theCrewRate;
                        }
                    }
                    if (trackCheck.isChecked()) {
                        int currentNumTracks = MapTI.currentNumberOfTrack + 1;
                        String theText = "T0".concat(Integer.toString(currentNumTracks));
                        String inspectionIDNum = LocalDBHelper.getDataInSharedPreference(getApplicationContext(), "inspectionID");
                        trackIDs1.add(theText);
                        trackNumber1.add(trackString);
                        trackLocations1.add(locString);
                        trackDescriptions1.add(descriptionString);
                        allDefectsDescriptions.add(descriptionString);
                        trackUnits1.add(unitString);
                        trackQuantitys1.add(quantityString);
                        trackPriority1.add(priorityString);
                        alllinenumbers.add(theText);
                        allpriorities.add(priorityString);
                        Defect defect = new Defect();
                        defect.inspection_id_num = inspectionIDNum;
                        defect.trackNumber = trackString;
                        defect.lineItem = theText;
                        defect.location = locString;
                        defect.description = descriptionString;
                        defect.unit = unitString;
                        defect.category = categoryString;
                        defect.code = codeString;
                        defect.labor = laborString;
                        if (production != 0) {
                            defect.price = (Integer.parseInt(quantityString) * Crewrate) / production;
                        } else {
                            defect.price = 0;
                        }
                        try {
                            defect.quantity = Integer.parseInt(quantityString);
                            defect.priority = Integer.parseInt(priorityString);
                        } catch (NumberFormatException e) {
                            defect.quantity = 1;
                            defect.priority = 2;
                            Log.e("NOTNUMQ", e.getMessage());
                        }
                        defect.latitude = getIntent().getDoubleExtra("lat", 0);
                        defect.longitude = getIntent().getDoubleExtra("long", 0);
                        defect.picture = myPicPath;
                        defect.codeDescription = codeDescriptionString;
                        HeaderData.inspection.defectList.add(defect);


                        setEditTextsToNull();

                        MapActivitys.numOfMarker++;
                        if (!MapTI.toggleMarkerOn) {
                            MapTI.toggleMarkerOn = true;
                        }
                        MapTI.currentNumberOfTrack++;
                        emptyLists();
                        Intent intent = new Intent(InspectionForm.this, MapTI.class);
                        startActivity(intent);
                    } else if (switchCheck.isChecked()) {
                        int currentNumSwitches = MapTI.currentNumberOfSwitches + 1;
                        String theText = "S0".concat(Integer.toString(currentNumSwitches));
                        String inspectionIDNum = LocalDBHelper.getDataInSharedPreference(getApplicationContext(), "inspectionID");

                        switchIDs1.add(theText);
                        switchNumber1.add(trackString);
                        switchLocations1.add(locString);
                        switchDescriptions1.add(descriptionString);
                        allDefectsDescriptions.add(descriptionString);
                        switchUnits1.add(unitString);
                        switchQuantitys1.add(quantityString);
                        switchPriority1.add(priorityString);
                        alllinenumbers.add(theText);
                        allpriorities.add(priorityString);
                        Defect defect = new Defect();
                        defect.inspection_id_num = inspectionIDNum;
                        defect.trackNumber = trackString;
                        defect.lineItem = theText;
                        defect.location = locString;
                        defect.description = descriptionString;
                        defect.unit = unitString;
                        defect.category = categoryString;
                        defect.code = codeString;
                        defect.labor = laborString;
                        if (production != 0) {
                            defect.price = (Integer.parseInt(quantityString) * Crewrate) / production;
                        } else {
                            defect.price = 0;
                        }
                        try {
                            defect.quantity = Integer.parseInt(quantityString);
                            defect.priority = Integer.parseInt(priorityString);
                        } catch (NumberFormatException e) {
                            defect.quantity = 1;
                            defect.priority = 2;
                            Log.e("NOTNUMQ", e.getMessage());
                        }


                        defect.latitude = getIntent().getDoubleExtra("lat", 0);
                        defect.longitude = getIntent().getDoubleExtra("long", 0);
                        defect.picture = myPicPath;
                        defect.codeDescription = codeDescriptionString;
                        HeaderData.inspection.defectList.add(defect);
                        setEditTextsToNull();

                        MapTI.currentNumberOfSwitches++;
                        MapActivitys.numOfMarker++;
                        if (!MapTI.toggleMarkerOn) {
                            MapTI.toggleMarkerOn = true;
                        }
                        emptyLists();
                        Intent intent = new Intent(InspectionForm.this, MapTI.class);
                        startActivity(intent);
                    } else {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(InspectionForm.this);
                        builder.setMessage("Check either track or switch checkbox.")
                                .setCancelable(false)
                                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                        dialog.cancel();
                                    }
                                });
                        final AlertDialog alert = builder.create();
                        alert.show();
                        ;
                    }
                }





            }
        });
    }
    private void emptyLists(){
        listCommonDefects.clear();
        listcategorys.clear();
        listprioritys.clear();
        listlaborcodes.clear();
        listunits.clear();
        listthatcategoryscodes.clear();
    }
    private void setText(){

        trackString= trackNumberEdit.getText().toString().trim();
        quantityString= quantityEdit.getText().toString().trim();
        try {
            if (!defectDescriptionEdit.getSelectedItem().toString().equals("Other")) {
                descriptionString = defectDescriptionEdit.getSelectedItem().toString();
            }
            laborString = laborEdit.getSelectedItem().toString();
            categoryString = catergoryEdit.getSelectedItem().toString();
            codeString = codeEdit.getSelectedItem().toString();
            priorityString = priorityEdit.getSelectedItem().toString();
        }catch (NullPointerException e){
            descriptionString = null;
            laborString = null;
            categoryString= null;
            codeString = null;
            priorityString= null;
        }


        locString= locationEdit.getText().toString().trim();
    }
    private void setEditTextsToNull(){
        trackNumberEdit.setText(null);
        quantityEdit.setText(null);
        locationEdit.setText(null);
    }
    private void onItemSelectedDefect() {
        catergoryEdit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem;
                if(position == 0){
                    selectedItem = parent.getItemAtPosition(position + 1).toString();
                }
                else {
                    selectedItem = parent.getItemAtPosition(position).toString();
                }
                if(selectedItem.equals("RAIL")){
                    listthatcategoryscodes.clear();
                     railFiles = localDBHelper.getAllRailFiles();
                    for(int i = 0; i < railFiles.size();i++){
                        listthatcategoryscodes.add(railFiles.get(i).theID);
                    }
                }else if(selectedItem.equals("CROSSTIES")){
                    listthatcategoryscodes.clear();
                    crosstiesFiles = localDBHelper.getAllCrosstiesFiles();
                    for(int i = 0; i < crosstiesFiles.size();i++){
                        listthatcategoryscodes.add(crosstiesFiles.get(i).theID);
                    }
                }else if(selectedItem.equals("CROSSINGS")){
                    listthatcategoryscodes.clear();
                     crossingsFiles = localDBHelper.getAllCrossingsFiles();
                    for(int i = 0; i < crossingsFiles.size();i++){
                        listthatcategoryscodes.add(crossingsFiles.get(i).theID);
                    }
                }else if(selectedItem.equals("OTM")){
                    listthatcategoryscodes.clear();
                     OTMFiles= localDBHelper.getAllOTMFiles();
                    for(int i = 0; i < OTMFiles.size();i++){
                        listthatcategoryscodes.add(OTMFiles.get(i).theID);
                    }
                }else if(selectedItem.equals("OTHER")){
                    listthatcategoryscodes.clear();
                    otherFiles  = localDBHelper.getAllOtherFiles();
                    for(int i = 0; i < otherFiles.size();i++){
                        listthatcategoryscodes.add(otherFiles.get(i).theID);
                    }
                }else if(selectedItem.equals("SWITCHETIE")){
                    listthatcategoryscodes.clear();
                    switchFiles = localDBHelper.getAllSwitchTiesFiles();
                    for(int i = 0; i < switchFiles.size();i++){
                        listthatcategoryscodes.add(switchFiles.get(i).theID);
                    }
                }else if(selectedItem.equals("ISSUES")){
                    listthatcategoryscodes.clear();
                    issueFiles = localDBHelper.getAllIssuesFiles();
                    for(int i = 0; i < issueFiles.size();i++){
                        listthatcategoryscodes.add(issueFiles.get(i).theID);
                    }
                }else if(selectedItem.equals("TURNOUT")){
                    listthatcategoryscodes.clear();
                    turnoutFiles = localDBHelper.getAllTurnoutFiles();
                    for(int i = 0; i < turnoutFiles.size();i++){
                        listthatcategoryscodes.add(turnoutFiles.get(i).theID);
                    }
                }
                codeEdit = (Spinner) findViewById(R.id.codecategorylist);

                ArrayAdapter<String> codeAdaptar = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, listthatcategoryscodes);
                codeEdit.setPrompt("[Select the code from the category below...]");
                codeEdit.setAdapter(
                        new NothingSelectedSpinnerAdapter(
                                codeAdaptar,
                                R.layout.contact_spinner_row_nothing_selected_category_code,
                                getBaseContext()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        defectDescriptionEdit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selectedItem;
                    if(position == 0){
                        selectedItem = parent.getItemAtPosition(position + 1).toString();
                    }
                    else {
                        selectedItem = parent.getItemAtPosition(position).toString();
                    }
                    if (selectedItem.equals("Other")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(InspectionForm.this);
                        builder.setTitle("Describe the defect below: ");

                        final EditText input = new EditText(InspectionForm.this);
                        input.setInputType(InputType.TYPE_CLASS_TEXT);
                        builder.setView(input);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                descriptionString = input.getText().toString();

                            }
                        });

                        builder.show();
                    }
                    else{
                        descriptionString = selectedItem;
                    }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }


    private void dispatchTakePictureIntent() {
        // create Camera intent
        Intent takePicIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // check that there is an installed camera app
        if (takePicIntent.resolveActivity(getPackageManager()) != null) {
            // create file where picture will be stored
            imageFileUri = Uri.fromFile(createImageFile());

            // if file successfully created
            if (imageFileUri != null) {
                takePicIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
                startActivityForResult(takePicIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "CameraTest");

        // check if desired directory for storing the pictures exists
        if (!mediaStorageDir.exists()) {
            // create the directory if does not exist
            if (!mediaStorageDir.mkdirs()) {
                return null; // unable to create target directory
            }

        }
        // create filename for the full-size picture
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "CameraTest_IMG_" + timeStamp + ".jpg";
        anotherPicPath = "/Internal storage/Pictures/CameraTest/".concat(fileName);
        // return the full file reference including directory
        File image = new File(mediaStorageDir.getPath() + File.separator + fileName);
        myPicPath = "file:" + image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
                try {
                    mImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(myPicPath));
                    cameraButton.setImageBitmap(mImageBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

        }

    }
}
//                final StreamResult result = new StreamResult(new File(getFilesDir(), "myFile.xml"));

//try {
//
//                    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
//                    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
//
//                    // root elements
//                    Document doc = docBuilder.newDocument();
//                    Element rootElement = doc.createElement("inspection");
//                    doc.appendChild(rootElement);
//                    //Element dataTag = doc.getDocumentElement();
//                    // staff elements
//                    Element userID = doc.createElement("user_id");
//                    rootElement.appendChild(userID);
//
//                    // set attribute to staff element
//                    Attr attr = doc.createAttribute("id");
//                    attr.setValue("1");
//                    userID.setAttributeNode(attr);
//
//
//                    // shorten way
//                    // staff.setAttribute("id", "1");
//
//                    // firstname elements
//                    //Element inspectionNumber = doc.createElement("insp_num");
//                    //inspectionNumber.appendChild(doc.createTextNode(inspectionNoString));
//                    //userID.appendChild(inspectionNumber);
//
//                    // lastname elements
//                    //Element customer = doc.createElement("customer");
//                    //customer.appendChild(doc.createTextNode(nameString));
//                    //userID.appendChild(customer);
//
//                    // nickname elements
//                    //Element address = doc.createElement("address");
//                    //address.appendChild(doc.createTextNode(addressString));
//                    //userID.appendChild(address);
//
//                    // salary elements
//                    //Element city = doc.createElement("city");
//                    //city.appendChild(doc.createTextNode(cityString));
//                    //userID.appendChild(city);
//
//                    //Element state = doc.createElement("state");
//                    //state.appendChild(doc.createTextNode(stateString));
//                    //userID.appendChild(state);
//
//                    //Element zipcode = doc.createElement("zip");
//                    //zipcode.appendChild(doc.createTextNode(zipString));
//                    //userID.appendChild(zipcode);
//
//                    //Element county = doc.createElement("county");
//                    //county.appendChild(doc.createTextNode(countyString));
//                    //userID.appendChild(county);
//
//                    //Element contact = doc.createElement("contact");
//                    //contact.appendChild(doc.createTextNode(contactString));
//                    //userID.appendChild(contact);
//
//                    //Element telephone = doc.createElement("tele_num");
//                    //telephone.appendChild(doc.createTextNode(phoneString));
//                    //userID.appendChild(telephone);
//
//                    //Element telefax = doc.createElement("fax_num");
//                    //telefax.appendChild(doc.createTextNode(faxString));
//                    //userID.appendChild(telefax);
//
//                    //Element email = doc.createElement("cust_email");
//                    //email.appendChild(doc.createTextNode(emailString));
//                    //userID.appendChild(email);
//
//                    //Element miles = doc.createElement("distance");
//                    //miles.appendChild(doc.createTextNode(milesString));
//                    //userID.appendChild(miles);
//
//                    //Element trips = doc.createElement("trips_num");
//                    //trips.appendChild(doc.createTextNode(tripsString));
//                    //userID.appendChild(trips);
//
//                    //Element surfacingTrips = doc.createElement("surface_trips");
//                    //surfacingTrips.appendChild(doc.createTextNode(surfacingString));
//                    //userID.appendChild(surfacingTrips);
//
//                    //Element mobilization = doc.createElement("mobil");
//                    //mobilization.appendChild(doc.createTextNode("100000"));
//                    //userID.appendChild(mobilization);
//
//                    //Element lineItem = doc.createElement("item_num");
//                    //lineItem.appendChild(doc.createTextNode("100000"));
//                    //userID.appendChild(lineItem);
//
//                    Element track = doc.createElement("track_num");
//                    track.appendChild(doc.createTextNode(trackString));
//                    userID.appendChild(track);
//
//                    Element location = doc.createElement("track_loc");
//                    location.appendChild(doc.createTextNode(locString));
//                    userID.appendChild(location);
//
//                    Element description = doc.createElement("track_desc");
//                    description.appendChild(doc.createTextNode(descriptionString));
//                    userID.appendChild(description);
//
//                    Element picture = doc.createElement("pic");
//                    picture.appendChild(doc.createTextNode(""));
//                    userID.appendChild(picture);
//
//                    Element labor = doc.createElement("labor");
//                    labor.appendChild(doc.createTextNode(laborString));
//                    userID.appendChild(labor);
//
//                    Element category = doc.createElement("category");
//                    category.appendChild(doc.createTextNode(categoryString));
//                    userID.appendChild(category);
//
//                    Element code = doc.createElement("code");
//                    code.appendChild(doc.createTextNode(codeString));
//                    userID.appendChild(code);
//
//                    Element codeDescription = doc.createElement("code_desc");
//                    codeDescription.appendChild(doc.createTextNode(desctypeString));
//                    userID.appendChild(codeDescription);
//
//                    Element quantity = doc.createElement("issue_num");
//                    quantity.appendChild(doc.createTextNode(priorityString));
//                    userID.appendChild(quantity);
//
//                    Element unit = doc.createElement("unit");
//                    unit.appendChild(doc.createTextNode(unitString));
//                    userID.appendChild(unit);
//
//                    Element priority = doc.createElement("priority");
//                    priority.appendChild(doc.createTextNode(priorityString));
//                    userID.appendChild(priority);
//
//                    // write the content into xml file
//                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
//                    Transformer transformer = transformerFactory.newTransformer();
//                    DOMSource source = new DOMSource(doc);
//                    //public File getFilesDir();
//
//
//                    transformer.transform(source, result);
//
//                } catch (ParserConfigurationException pce) {
//                    pce.printStackTrace();
//                } catch (TransformerException tfe) {
//                    tfe.printStackTrace();
//                }
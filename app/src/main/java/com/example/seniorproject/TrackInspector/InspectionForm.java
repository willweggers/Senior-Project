package com.example.seniorproject.TrackInspector;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;

import com.example.seniorproject.AccountInfo;
import com.example.seniorproject.CreateDB;
import com.example.seniorproject.MapActivitys;
import com.example.seniorproject.NothingSelectedSpinnerAdapter;
import com.example.seniorproject.R;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    private Spinner unitEdit;
    private EditText locationEdit;
    private EditText codeDescriptionEdit;
    private Button sumbitDefectButton;

    public int currentNumberOfTrack = 0;
    public int currentNumberOfSwitches = 0;

    public static ArrayList<String> listCommonDefects = new ArrayList<>();
    public static ArrayList<String> listlaborcodes = new ArrayList<>();
    public static ArrayList<String> listcategorys= new ArrayList<>();
    public static ArrayList<String> listthatcategoryscodes = new ArrayList<>();
    public static ArrayList<String> listprioritys = new ArrayList<>();
    public static ArrayList<String> listunits = new ArrayList<>();



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
        codeDescriptionEdit = (EditText) findViewById(R.id.codedescnedit);
        //adding temp defects and other
        listCommonDefects.add("a defect");
        listCommonDefects.add("another defect");
        listCommonDefects.add("another one");
        listCommonDefects.add("Other");

        //temp adding arraylist for other spinners for testing remove when master data files are a thing
        listlaborcodes.add("labor code");
        listcategorys.add("a category");
        listprioritys.add("High");
        listthatcategoryscodes.add("category code");
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
        unitEdit = (Spinner) findViewById(R.id.unitlist);
        ArrayAdapter<String> unitAdaptar = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listunits);
        unitEdit.setPrompt("[Select the unit of defect...]");
        unitEdit.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        unitAdaptar,
                        R.layout.contact_spinner_row_nothing_selected_units,
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
                    switchTrackNumberTextField.setText("T0" + currentNumberOfTrack+1);
                }
            }
        });
        switchCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    trackCheck.setChecked(false);
                    switchTrackNumberTextField.setText("S0" + currentNumberOfSwitches+1);
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
                if(trackCheck.isChecked()){
                    trackIDs1.add("T0"+currentNumberOfTrack+1);
                    trackNumber1.add(trackString);
                    trackLocations1.add(locString);
                    trackDescriptions1.add(descriptionString);
                    trackUnits1.add(unitString);
                    trackQuantitys1.add(quantityString);
                    trackPriority1.add(priorityString);
                    setEditTextsToNull();

                    MapActivitys.numOfMarker++;
                    if(!MapTI.toggleMarkerOn){
                        MapTI.toggleMarkerOn = true;
                    }
                    currentNumberOfTrack++;
                   // emptyLists();
                    Intent intent = new Intent(InspectionForm.this, MapTI.class);
                    startActivity(intent);
                }
                else if(switchCheck.isChecked()){
                    switchIDs1.add("S0"+currentNumberOfSwitches+1);
                    switchNumber1.add(trackString);
                    switchLocations1.add(locString);
                    switchDescriptions1.add(descriptionString);
                    switchUnits1.add(unitString);
                    switchQuantitys1.add(quantityString);
                    switchPriority1.add(priorityString);
                    setEditTextsToNull();

                    currentNumberOfSwitches++;
                    MapActivitys.numOfMarker++;
                    if(!MapTI.toggleMarkerOn){
                        MapTI.toggleMarkerOn = true;
                    }
                    //emptyLists();
                    Intent intent = new Intent(InspectionForm.this, MapTI.class);
                    startActivity(intent);
                }
                else{
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
        trackString= trackNumberEdit.getText().toString();
        quantityString= quantityEdit.getText().toString();
        if(!defectDescriptionEdit.getSelectedItem().toString().equals("Other")) {
            descriptionString = defectDescriptionEdit.getSelectedItem().toString();
        }
        laborString= laborEdit.getSelectedItem().toString();
        categoryString= catergoryEdit.getSelectedItem().toString();
        codeString=codeEdit.getSelectedItem().toString();
        priorityString=priorityEdit.getSelectedItem().toString();
        unitString=unitEdit.getSelectedItem().toString();
        locString= locationEdit.getText().toString();
    }
    private void setEditTextsToNull(){
        trackNumberEdit.setText(null);
        quantityEdit.setText(null);
        locationEdit.setText(null);
    }
    private void onItemSelectedDefect() {

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
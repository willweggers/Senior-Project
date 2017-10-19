package com.example.seniorproject.TrackInspector;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;

import com.example.seniorproject.MapActivitys;
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
    private Button cameraBtn;
    // thumbnail image returned by camera app
    public ImageView thumbnail;
    // activity result key for camera
    static final int REQUEST_TAKE_PHOTO = 1;
    Uri imageFileUri;
    // absolute path for camera images
    String myPicPath;
//    private ImageView imageView;
    public static ArrayList<String> trackIDs1= new ArrayList<>();
    public static ArrayList<String> trackNumber1= new ArrayList<>();
    public static ArrayList<String> trackLocations1= new ArrayList<>();
    public static ArrayList<String> trackDescriptions1= new ArrayList<>();
    public static ArrayList<String> trackUnits1= new ArrayList<>();
    public static ArrayList<String> trackQuantitys1= new ArrayList<>();
    public static ArrayList<String> trackPriority1= new ArrayList<>();



    public static ArrayList<String> switchIDs1= new ArrayList<>();
    public static ArrayList<String> switchNumber1= new ArrayList<>();
    public static ArrayList<String> switchLocations1= new ArrayList<>();
    public static ArrayList<String> switchDescriptions1= new ArrayList<>();
    public static ArrayList<String> switchUnits1= new ArrayList<>();
    public static ArrayList<String> switchQuantitys1= new ArrayList<>();
    public static ArrayList<String> switchPriority1= new ArrayList<>();
    public static String trackString;
    public static ArrayList<String> desctrypeArray = new ArrayList<>();
    public static String laborString;
    public static String locString;
    public static String categoryString;
    public static String codeString;
    public static String descriptionString;
    public static String desctypeString;
    public static String quantityString;
    public static String priorityString;
    public static String unitString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.defect_report_track_inspector);
        cameraBtn = (Button) findViewById(R.id.camerabtn);
//        imageView = (ImageView) findViewById(R.id.currdisplayimage);
//        Geocoder geocoder;
//        List<Address> addresses = new ArrayList<>();
//        geocoder = new Geocoder(this, Locale.getDefault());
//
//        try {
//            addresses = geocoder.getFromLocation(MapTI.markers.get(MapTI.numOfMarker).getPosition().latitude, MapTI.markers.get(MapTI.numOfMarker).getPosition().longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        final String address = addresses.get(0).getAddressLine(0);
//        String city = addresses.get(0).getLocality();
//        String state = addresses.get(0).getAdminArea();
//        String country = addresses.get(0).getCountryName();
//        String postalCode = addresses.get(0).getPostalCode();
//        final String knownName = addresses.get(0).getFeatureName();
        setButtons();

        Button add_defect = (Button)findViewById(R.id.button);
        add_defect.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
        final TextView itemtv = (TextView) findViewById(R.id.itemTV);
        final EditText track_box = (EditText)findViewById(R.id.trackText);
        final EditText labor_box = (EditText)findViewById(R.id.laborText);
        final EditText loc_box = (EditText)findViewById(R.id.locationText);
        final EditText category_box = (EditText)findViewById(R.id.categoryText);
        final EditText code_box = (EditText)findViewById(R.id.codeText);
        final EditText description_box = (EditText)findViewById(R.id.descriptionText);
        final EditText desctype_box = (EditText)findViewById(R.id.desctypeText);
        final EditText quantity_box = (EditText)findViewById(R.id.quantityText);
        final EditText priority_box = (EditText)findViewById(R.id.priorityText);
                final EditText unit_box= (EditText)findViewById(R.id.unit);

                //final EditText inspectionNo_box = (EditText)findViewById(R.id.inspectionnoText);
        //final EditText inspectionD_box = (EditText)findViewById(R.id.inspectiondText);
        //final EditText zip_box = (EditText)findViewById(R.id.zipText);
        //final EditText name_box = (EditText)findViewById(R.id.nameText);
        //final EditText address_box = (EditText)findViewById(R.id.addressText);
        //final EditText city_box = (EditText)findViewById(R.id.cityText);
        //final EditText county_box = (EditText)findViewById(R.id.countyText);
        //final EditText contact_box = (EditText)findViewById(R.id.contactText);
        //final EditText phone_box = (EditText)findViewById(R.id.phoneText);
        //final EditText fax_box = (EditText)findViewById(R.id.faxText);
        //final EditText email_box = (EditText)findViewById(R.id.emailText);
        //final EditText miles_box = (EditText)findViewById(R.id.milesText);
        //final EditText trips_box = (EditText)findViewById(R.id.tripsText);
        //final EditText surfacing_box = (EditText)findViewById(R.id.surfacingText);
        //final EditText state_box = (EditText)findViewById(R.id.stateText);
        //String stateString = state_box.getText().toString();
        //String milesString = miles_box.getText().toString();
        //String tripsString = trips_box.getText().toString();
        //String surfacingString = surfacing_box.getText().toString();
        //final String inspectionNoString = inspectionNo_box.getText().toString();
        //String inspectionDString = inspectionD_box.getText().toString();
        //String zipString = zip_box.getText().toString();
        //String nameString = name_box.getText().toString();
        //String addressString = address_box.getText().toString();
        //String cityString = city_box.getText().toString();
        //String countyString = county_box.getText().toString();
        //String contactString = contact_box.getText().toString();
        //String phoneString = phone_box.getText().toString();
        //String faxString = fax_box.getText().toString();
        //String emailString = email_box.getText().toString();
       trackString = track_box.getText().toString();
                trackNumber1.add(trackString);
       laborString = labor_box.getText().toString();

       locString = loc_box.getText().toString();
                trackLocations1.add(locString);
       categoryString = category_box.getText().toString();
       codeString = code_box.getText().toString();
       descriptionString = description_box.getText().toString();
                trackDescriptions1.add(descriptionString);
       desctypeString = desctype_box.getText().toString();
                desctrypeArray.add(desctypeString);
       quantityString = quantity_box.getText().toString();
                trackQuantitys1.add(quantityString);
       priorityString = priority_box.getText().toString();
                trackPriority1.add(quantityString);
       unitString = unit_box.getText().toString();
                trackUnits1.add(unitString);

        final StreamResult result = new StreamResult(new File(getFilesDir(), "myFile.xml"));
        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("inspection");
            doc.appendChild(rootElement);
            //Element dataTag = doc.getDocumentElement();
            // staff elements
            Element userID = doc.createElement("user_id");
            rootElement.appendChild(userID);

            // set attribute to staff element
            Attr attr = doc.createAttribute("id");
            attr.setValue("1");
            userID.setAttributeNode(attr);


            // shorten way
            // staff.setAttribute("id", "1");

            // firstname elements
            //Element inspectionNumber = doc.createElement("insp_num");
            //inspectionNumber.appendChild(doc.createTextNode(inspectionNoString));
            //userID.appendChild(inspectionNumber);

            // lastname elements
            //Element customer = doc.createElement("customer");
            //customer.appendChild(doc.createTextNode(nameString));
            //userID.appendChild(customer);

            // nickname elements
            //Element address = doc.createElement("address");
            //address.appendChild(doc.createTextNode(addressString));
            //userID.appendChild(address);

            // salary elements
            //Element city = doc.createElement("city");
            //city.appendChild(doc.createTextNode(cityString));
            //userID.appendChild(city);

            //Element state = doc.createElement("state");
            //state.appendChild(doc.createTextNode(stateString));
            //userID.appendChild(state);

            //Element zipcode = doc.createElement("zip");
            //zipcode.appendChild(doc.createTextNode(zipString));
            //userID.appendChild(zipcode);

            //Element county = doc.createElement("county");
            //county.appendChild(doc.createTextNode(countyString));
            //userID.appendChild(county);

            //Element contact = doc.createElement("contact");
            //contact.appendChild(doc.createTextNode(contactString));
            //userID.appendChild(contact);

            //Element telephone = doc.createElement("tele_num");
            //telephone.appendChild(doc.createTextNode(phoneString));
            //userID.appendChild(telephone);

            //Element telefax = doc.createElement("fax_num");
            //telefax.appendChild(doc.createTextNode(faxString));
            //userID.appendChild(telefax);

            //Element email = doc.createElement("cust_email");
            //email.appendChild(doc.createTextNode(emailString));
            //userID.appendChild(email);

            //Element miles = doc.createElement("distance");
            //miles.appendChild(doc.createTextNode(milesString));
            //userID.appendChild(miles);

            //Element trips = doc.createElement("trips_num");
            //trips.appendChild(doc.createTextNode(tripsString));
            //userID.appendChild(trips);

            //Element surfacingTrips = doc.createElement("surface_trips");
            //surfacingTrips.appendChild(doc.createTextNode(surfacingString));
            //userID.appendChild(surfacingTrips);

            //Element mobilization = doc.createElement("mobil");
            //mobilization.appendChild(doc.createTextNode("100000"));
            //userID.appendChild(mobilization);

            //Element lineItem = doc.createElement("item_num");
            //lineItem.appendChild(doc.createTextNode("100000"));
            //userID.appendChild(lineItem);

            Element track = doc.createElement("track_num");
            track.appendChild(doc.createTextNode(trackString));
            userID.appendChild(track);

            Element location = doc.createElement("track_loc");
            location.appendChild(doc.createTextNode(locString));
            userID.appendChild(location);

            Element description = doc.createElement("track_desc");
            description.appendChild(doc.createTextNode(descriptionString));
            userID.appendChild(description);

            Element picture = doc.createElement("pic");
            picture.appendChild(doc.createTextNode(""));
            userID.appendChild(picture);

            Element labor = doc.createElement("labor");
            labor.appendChild(doc.createTextNode(laborString));
            userID.appendChild(labor);

            Element category = doc.createElement("category");
            category.appendChild(doc.createTextNode(categoryString));
            userID.appendChild(category);

            Element code = doc.createElement("code");
            code.appendChild(doc.createTextNode(codeString));
            userID.appendChild(code);

            Element codeDescription = doc.createElement("code_desc");
            codeDescription.appendChild(doc.createTextNode(desctypeString));
            userID.appendChild(codeDescription);

            Element quantity = doc.createElement("issue_num");
            quantity.appendChild(doc.createTextNode(quantityString));
            userID.appendChild(quantity);

            Element unit = doc.createElement("unit");
            unit.appendChild(doc.createTextNode(unitString));
            userID.appendChild(unit);

            Element priority = doc.createElement("priority");
            priority.appendChild(doc.createTextNode(priorityString));
            userID.appendChild(priority);

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            //public File getFilesDir();


            transformer.transform(source, result);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }






                track_box.setText(null);
                labor_box.setText(null);
                loc_box.setText(null);
                category_box.setText(null);
                code_box.setText(null);
                description_box.setText(null);
                desctype_box.setText(null);
                quantity_box.setText(null);
                priority_box.setText(null);
                unit_box.setText(null);


                if (MapTI.score <10) {
                    itemtv.setText("T 0" + MapTI.score);
                    trackIDs1.add(itemtv.getText().toString());
                }
                else{
                itemtv.setText("T " + MapTI.score);
                trackIDs1.add(itemtv.getText().toString());
            }


                MapActivitys.numOfMarker++;
                if(!MapTI.toggleMarkerOn){
                    MapTI.toggleMarkerOn = true;
                }

                Intent intent = new Intent(InspectionForm.this, MapTI.class);
                startActivity(intent);


            }
        });
    }
    public void setButtons(){
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
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
        return new File(mediaStorageDir.getPath() + File.separator + fileName);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
//            Bitmap photo = (Bitmap) data.getExtras().get("data");
//            imageView.setImageBitmap(photo);
        }

    }
}

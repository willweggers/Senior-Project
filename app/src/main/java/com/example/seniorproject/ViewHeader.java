package com.example.seniorproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.seniorproject.DB.AccountFields;
import com.example.seniorproject.DB.Inspection;
import com.example.seniorproject.DB.LocalDBHelper;
import com.example.seniorproject.DB.MasterFileObjects.MobilizationFile;
import com.example.seniorproject.TrackInspector.HeaderData;
import com.example.seniorproject.TrackInspector.MapTI;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by willw on 11/30/2017.
 */

public class ViewHeader extends AppCompatActivity {
    private Button goToMap;
    private String inspectionViewing;
    private LocalDBHelper localDBHelper;

    private TextView customer;
    private TextView address;
    private TextView city;
    private TextView state;
    private TextView zipcode;
    private TextView county;
    private TextView contact;
    private TextView tele;
    private TextView telefax;
    private TextView email;
    private TextView miles;
    private TextView trips;
    private TextView surfacingtrips;
    private TextView mobilization;

    int j = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_header);
        goToMap     = (Button) findViewById(R.id.gotomaphead);
        customer    = (TextView) findViewById(R.id.companynameeview);
        address     = (TextView) findViewById(R.id.locationaddressview);
        city        = (TextView) findViewById(R.id.locationcityview);
        state       = (TextView) findViewById(R.id.locationstateview);
        zipcode     = (TextView) findViewById(R.id.locationzipview);
        county      = (TextView) findViewById(R.id.locationcountyview);
        contact     = (TextView) findViewById(R.id.contactnameview);
        tele        = (TextView) findViewById(R.id.contactteleview);
        telefax     = (TextView) findViewById(R.id.contacttelefaxview);
        email       = (TextView) findViewById(R.id.contactemailview);
        miles       = (TextView) findViewById(R.id.milesview);
        trips       = (TextView) findViewById(R.id.tripsview);
        surfacingtrips= (TextView) findViewById(R.id.surfactingtripsview);
        mobilization= (TextView) findViewById(R.id.mobilizationview);
        goToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewHeader.this, ViewInspection.class);
                startActivity(intent);
            }
        });

        inspectionViewing = LocalDBHelper.getDataInSharedPreference(getApplicationContext(),"inspectionID");
        localDBHelper = LocalDBHelper.getInstance(getApplicationContext());
        setTexts();
        setTitle(inspectionViewing);
    }
    private void setTexts(){
        ArrayList<Inspection> inspections = localDBHelper.getAllInspections();
        for(int i = 0; i < inspections.size();i++){
            if(inspections.get(i).inspectionNum.equals(inspectionViewing)){

                        customer.setText(inspections.get(i).customer);
                address.setText(inspections.get(i).address);
                        city.setText(inspections.get(i).city);
                state.setText(inspections.get(i).state);
                        zipcode.setText(inspections.get(i).zip);
                county.setText(inspections.get(i).county);
                        contact.setText(inspections.get(i).contact);
                tele.setText(inspections.get(i).phone);
                        telefax.setText(inspections.get(i).telefax);
                email.setText(inspections.get(i).email);
                        miles.setText(String.valueOf(inspections.get(i).distance));
                trips.setText(String.valueOf(inspections.get(i).trips));
                        surfacingtrips.setText(String.valueOf(inspections.get(i).surfacingTrips));
                mobilization.setText(String.valueOf(inspections.get(i).mobilization));
            }
        }
    }

    private void setButtons(){
        goToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewHeader.this, ViewInspection.class);
                startActivity(intent);
            }
        });
    }

}


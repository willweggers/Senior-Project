package com.example.seniorproject.TrackInspector;

import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.seniorproject.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by willw on 10/6/2017.
 */

public class HeaderData extends AppCompatActivity {
    private Button goToMap;
    private EditText companyname;
    private EditText location;
    private EditText trackinspector;
    private EditText address;
    private EditText customercontact;
    public static String thecompanyname;
    public static String thelocation;
    public static String thetrackinspectorid;
    public static String theaddress;
    public static String thecustomercontact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.header_data_track_inspector);
        goToMap = (Button) findViewById(R.id.gotomap);
        companyname = (EditText) findViewById(R.id.companynamee);
        location = (EditText) findViewById(R.id.location);
        trackinspector = (EditText) findViewById(R.id.trackinspectorid);
        address = (EditText) findViewById(R.id.address);
        customercontact = (EditText) findViewById(R.id.cusomtercontact);
        setTitle("Header Data");

        setButtons();
    }
    private void setButtons(){
        goToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HeaderData.this, MapTI.class);
                startActivity(intent);
                getTexts();
            }
        });
    }
    private void getTexts(){
        thecompanyname = companyname.getText().toString();
        thelocation = location.getText().toString();
        thetrackinspectorid = trackinspector.getText().toString();
        theaddress = address.getText().toString();
        thecustomercontact = customercontact.getText().toString();
    }
    public static String getDate(){
        DateFormat df = new SimpleDateFormat("MM/d/yy");
        String date = df.format(Calendar.getInstance().getTime());
        return date;
    }
}

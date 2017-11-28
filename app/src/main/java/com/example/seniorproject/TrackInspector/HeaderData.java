package com.example.seniorproject.TrackInspector;

import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.seniorproject.DB.AccountFields;
import com.example.seniorproject.DB.Inspection;
import com.example.seniorproject.DB.LocalDBHelper;
import com.example.seniorproject.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by willw on 10/6/2017.
 */

public class HeaderData extends AppCompatActivity {
    private Button goToMap;

    private EditText inspectionidnum;
    private EditText companyname;

    private EditText locationadd;
    private EditText locationstate;
    private EditText locationcity;
    private EditText locationcounty;
    private EditText locationzip;

    private EditText customername;
    private EditText telephone;
    private EditText telefax;
    private EditText email;

    int j = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.header_data_track_inspector);
        goToMap = (Button) findViewById(R.id.gotomap);
        companyname = (EditText) findViewById(R.id.companynamee);
        inspectionidnum = (EditText) findViewById(R.id.inspectionidnum);
        locationadd= (EditText) findViewById(R.id.locationaddress);
        locationstate= (EditText) findViewById(R.id.locationstate);
        locationcity= (EditText) findViewById(R.id.locationcity);
        locationcounty= (EditText) findViewById(R.id.locationcounty);
        locationzip= (EditText) findViewById(R.id.locationzip);
        customername= (EditText) findViewById(R.id.contactname);
        telephone= (EditText) findViewById(R.id.contacttele);
        telefax= (EditText) findViewById(R.id.contacttelefax);
        email= (EditText) findViewById(R.id.contactemail);
        LocalDBHelper localDBHelper = LocalDBHelper.getInstance(getApplicationContext());
        AccountFields accountFields = new AccountFields();
        try {
            accountFields = localDBHelper.getAccountByUser(LocalDBHelper.getDataInSharedPreference(this, "username"));
        }catch (IllegalArgumentException e){
            accountFields.initials = "ERR";
        }
        String inspectionNumber = accountFields.initials.concat(getDate()).concat("-").concat(Integer.toString(j));
        inspectionNumber = checkInspectionNumUnique(inspectionNumber);
        inspectionidnum.setText(inspectionNumber);
        setTitle("Enter header information for this inspection below:");

        setButtons();
    }
    private void setEditTextNull(){
        companyname.setText(null);
        inspectionidnum.setText(null);
        locationadd.setText(null);
        locationstate.setText(null);
        locationcity.setText(null);
        locationcounty.setText(null);
        locationzip.setText(null);
        customername.setText(null);
        telephone.setText(null);
        telefax.setText(null);
        email.setText(null);
    }
    private void setButtons(){
        goToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Inspection inspection = getTexts();
                Intent intent = new Intent(HeaderData.this, MapTI.class);
                intent.putExtra("inspection", inspection);

                startActivity(intent);
                setEditTextNull();

            }
        });
    }
    private Inspection getTexts(){
        Inspection inspection = new Inspection();
        inspection.address = locationadd.getText().toString().trim();
        inspection.telefax = telefax.getText().toString().trim();
        inspection.zip = locationzip.getText().toString().trim();
        inspection.phone = telephone.getText().toString().trim();
        inspection.city = locationadd.getText().toString().trim();
        inspection.customer = customername.getText().toString().trim();
        inspection.county = locationcounty.getText().toString().trim();
        inspection.email = email.getText().toString().trim();
        inspection.state = locationstate.getText().toString().trim();
        inspection.contact = companyname.getText().toString().trim();
        inspection.inspectionDate= getPreciseDate();
        inspection.inspectorID = getIntent().getStringExtra("Username");
        inspection.inspectionNum = inspectionidnum.getText().toString().trim();
        return inspection;
    }
    private String checkInspectionNumUnique(String inspectionID){
        String newinspectionID = inspectionID;
        LocalDBHelper localDBHelper = LocalDBHelper.getInstance(getApplicationContext());
       ArrayList<String> arrayList = localDBHelper.getAllInspectionsIDNum();
        for (int i = 0; i< arrayList.size();i++){
            if(arrayList.get(i).equals(inspectionID)){
                j++;
                newinspectionID =  removeAfterDashChar(inspectionID).concat(Integer.toString(j));
                checkInspectionNumUnique(newinspectionID);
            }
        }
        return newinspectionID;
    }
    private String removeAfterDashChar(String str) {
        return str.split("-")[0];
    }
    public static String getDate(){
        DateFormat df = new SimpleDateFormat("MMdyy");
        String date = df.format(Calendar.getInstance().getTime());
        return date;
    }
    public static Date getPreciseDate(){
        Date date = new Date();
        return date;
    }
}

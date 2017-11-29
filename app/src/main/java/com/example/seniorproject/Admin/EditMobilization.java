package com.example.seniorproject.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.seniorproject.DB.LocalDBHelper;
import com.example.seniorproject.DB.MasterFileObjects.MobilizationFile;
import com.example.seniorproject.DB.MasterFileObjects.StateFile;
import com.example.seniorproject.R;

import java.util.ArrayList;

/**
 * Created by willw on 11/29/2017.
 */

public class EditMobilization extends AppCompatActivity {
    private Button submitButton;
    private EditText id;
    private EditText descp;
    private EditText min;
    private EditText travel;
    private EditText markup;
    private EditText billingrate;
    private EditText production;
    private LocalDBHelper localDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_mobilizaton);
        id = (EditText) findViewById(R.id.editmobileid);
        descp = (EditText) findViewById(R.id.descpidmobile);
        min = (EditText) findViewById(R.id.minmobile);
        travel = (EditText) findViewById(R.id.travelmobile);
        submitButton = (Button) findViewById(R.id.submitechangedmobilizatonrow);
        localDB = LocalDBHelper.getInstance(this);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MenuAdmin.class);
                MobilizationFile stateFile = new MobilizationFile();
                stateFile.theID = id.getText().toString().trim();
                stateFile.thedescription = descp.getText().toString().trim();
                stateFile.theMinimum = Integer.parseInt(min.getText().toString().trim());
                stateFile.thetravel = Integer.parseInt(travel.getText().toString().trim());
                localDB.updateMobilizationLine(stateFile);
                startActivity(intent);
            }
        });
        ArrayList<MobilizationFile> stateFiles = localDB.getAllMobilizationFiles();
        String rowid = LocalDBHelper.getDataInSharedPreference(this,"rowviewing");
        for(int i = 0; i < stateFiles.size();i++){
            if(stateFiles.get(i).theID.equals(rowid)){
                min.setText(String.valueOf(stateFiles.get(i).theMinimum));
                descp.setText(stateFiles.get(i).thedescription);
                travel.setText(String.valueOf(stateFiles.get(i).thetravel));
            }
        }
        id.setText(rowid);

    }

}

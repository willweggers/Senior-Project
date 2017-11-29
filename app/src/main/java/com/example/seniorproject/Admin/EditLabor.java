package com.example.seniorproject.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.seniorproject.DB.LocalDBHelper;
import com.example.seniorproject.DB.MasterFileObjects.LaborInstallFile;
import com.example.seniorproject.DB.MasterFileObjects.MobilizationFile;
import com.example.seniorproject.R;

import java.util.ArrayList;

/**
 * Created by willw on 11/29/2017.
 */

public class EditLabor extends AppCompatActivity {
    private Button submitButton;
    private EditText id;
    private EditText descp;
    private EditText crewrate;
    private EditText perdiem;

    private LocalDBHelper localDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_labor);
        id = (EditText) findViewById(R.id.editlaborid);
        descp = (EditText) findViewById(R.id.descpidlabor);
        crewrate = (EditText) findViewById(R.id.crewrate);
        perdiem = (EditText) findViewById(R.id.perdiem);
        submitButton = (Button) findViewById(R.id.submitechangedlaborrow);
        localDB = LocalDBHelper.getInstance(this);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MenuAdmin.class);
                LaborInstallFile stateFile = new LaborInstallFile();
                stateFile.theID = id.getText().toString().trim();
                stateFile.thedescription = descp.getText().toString().trim();
                stateFile.theCrewRate = Integer.parseInt(crewrate.getText().toString().trim());
                stateFile.thePerDiem = Integer.parseInt(perdiem.getText().toString().trim());
                localDB.updateLaborLine(stateFile);
                startActivity(intent);
            }
        });
        ArrayList<LaborInstallFile> stateFiles = localDB.getAllLaborFiles();
        String rowid = LocalDBHelper.getDataInSharedPreference(this,"rowviewing");
        for(int i = 0; i < stateFiles.size();i++){
            if(stateFiles.get(i).theID.equals(rowid)){
                crewrate.setText(String.valueOf(stateFiles.get(i).theCrewRate));
                descp.setText(stateFiles.get(i).thedescription);
                perdiem.setText(String.valueOf(stateFiles.get(i).thePerDiem));
            }
        }
        id.setText(rowid);

    }

}

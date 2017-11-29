package com.example.seniorproject.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.seniorproject.DB.LocalDBHelper;
import com.example.seniorproject.DB.MasterFileObjects.StateFile;
import com.example.seniorproject.R;

import java.util.ArrayList;

/**
 * Created by willw on 11/29/2017.
 */

public class EditStateRow extends AppCompatActivity {
    private Button submitButton;
    private EditText id;
    private EditText descp;
    private EditText labortax;
    private EditText othertaxperc;
    private EditText othertaxon;

    private LocalDBHelper localDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_state_row);
        id = (EditText) findViewById(R.id.editstateid);
        descp = (EditText) findViewById(R.id.descpidstate);
        labortax = (EditText) findViewById(R.id.labortaxstate);
        othertaxon = (EditText) findViewById(R.id.othertaxonstate);
        othertaxperc = (EditText) findViewById(R.id.othertaxpercentstate);
        submitButton = (Button) findViewById(R.id.submitechangedstaterow);
        localDB = LocalDBHelper.getInstance(this);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditStateRow.this, MenuAdmin.class);
                StateFile stateFile = new StateFile();
                stateFile.theID = id.getText().toString().trim();
                stateFile.thedescription = descp.getText().toString().trim();
                stateFile.theLaborTax = labortax.getText().toString().trim();
                stateFile.theOtherTaxPerc = Integer.parseInt(othertaxperc.getText().toString().trim());
                stateFile.theOtherTaxOn = othertaxon.getText().toString().trim();
                localDB.updateStateLine(stateFile);
                startActivity(intent);
            }
        });
        ArrayList<StateFile> stateFiles = localDB.getAllStateFiles();
        String rowid = LocalDBHelper.getDataInSharedPreference(this,"rowviewing");
        for(int i = 0; i < stateFiles.size();i++){
            if(stateFiles.get(i).theID.equals(rowid)){
                labortax.setText(stateFiles.get(i).theLaborTax);
                descp.setText(stateFiles.get(i).thedescription);
                othertaxon.setText(stateFiles.get(i).theOtherTaxOn);
                othertaxperc.setText(String.valueOf(stateFiles.get(i).theOtherTaxPerc));
            }
        }
        id.setText(rowid);
    }

}

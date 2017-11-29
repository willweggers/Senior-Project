package com.example.seniorproject.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.seniorproject.DB.LocalDBHelper;
import com.example.seniorproject.DB.MasterFileObjects.PriorityFile;
import com.example.seniorproject.DB.MasterFileObjects.StateFile;
import com.example.seniorproject.DB.MasterFileTable;
import com.example.seniorproject.R;

import java.util.ArrayList;

/**
 * Created by willw on 11/29/2017.
 */

public class EditPriority extends AppCompatActivity {
    private Button submitButton;
    private EditText id;
    private EditText descp;
    private LocalDBHelper localDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_priority);
        id = (EditText) findViewById(R.id.editprioid);
        descp = (EditText) findViewById(R.id.descpidpri);
        submitButton = (Button) findViewById(R.id.submitechangedpriorityrow);
        localDB = LocalDBHelper.getInstance(this);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MenuAdmin.class);
                PriorityFile stateFile = new PriorityFile();
                stateFile.theID = Integer.parseInt(id.getText().toString().trim());
                stateFile.thedescription = descp.getText().toString().trim();

                localDB.updatePrioriyLine(stateFile);
                startActivity(intent);
            }
        });
        ArrayList<PriorityFile> stateFiles = localDB.getAllPriorityFiles();
        String rowid = LocalDBHelper.getDataInSharedPreference(this,"rowviewing");
        for(int i = 0; i < stateFiles.size();i++){
            if(stateFiles.get(i).theID == Integer.parseInt(rowid)){
                descp.setText(stateFiles.get(i).thedescription);
            }
        }
        id.setText(rowid);

    }

}
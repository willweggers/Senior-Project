package com.example.seniorproject.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.seniorproject.AccountInfo;
import com.example.seniorproject.DB.AccountFields;
import com.example.seniorproject.DB.LocalDBHelper;
import com.example.seniorproject.DB.MasterFileObjects.CrossingsFile;
import com.example.seniorproject.DB.MasterFileObjects.CrosstiesFile;
import com.example.seniorproject.DB.MasterFileObjects.IssueFile;
import com.example.seniorproject.DB.MasterFileObjects.OTMFile;
import com.example.seniorproject.DB.MasterFileObjects.OtherFile;
import com.example.seniorproject.DB.MasterFileObjects.RailFile;
import com.example.seniorproject.DB.MasterFileObjects.StateFile;
import com.example.seniorproject.DB.MasterFileObjects.SwitchTiesFile;
import com.example.seniorproject.DB.MasterFileObjects.TurnoutsFile;
import com.example.seniorproject.MainActivityLogin;
import com.example.seniorproject.Manager.MenuManager;
import com.example.seniorproject.R;
import com.example.seniorproject.TempDB.TempDB;
import com.example.seniorproject.TrackInspector.MenuTI;

import java.util.ArrayList;

/**
 * Created by willw on 11/29/2017.
 */

public class EditCat extends AppCompatActivity {
    private Button submitButton;
    private EditText id;
    private EditText descp;
    private EditText unit;
    private EditText unitcost;
    private EditText markup;
    private EditText billingrate;
    private EditText production;
    private LocalDBHelper localDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_cat_file);
        id = (EditText) findViewById(R.id.idcat);
        descp = (EditText) findViewById(R.id.descptioncat);
        unit = (EditText) findViewById(R.id.unit);
        unitcost = (EditText) findViewById(R.id.unitcost);
        markup = (EditText) findViewById(R.id.markup);
        billingrate = (EditText) findViewById(R.id.billingrate);
        production = (EditText) findViewById(R.id.production);
        submitButton = (Button) findViewById(R.id.submitechangedcatrow);
        localDB = LocalDBHelper.getInstance(this);

        String whatCat =  LocalDBHelper.getDataInSharedPreference(getApplicationContext(),"cat");
        if(whatCat.equals("Turnout")){
            ArrayList<TurnoutsFile> arrayList = localDB.getAllTurnoutFiles();
            addTurnoutRows(arrayList);
        }else if(whatCat.equals("Rail")){
            ArrayList<RailFile> arrayList = localDB.getAllRailFiles();
            addRailRows(arrayList);
        }else if(whatCat.equals("Switches")){
            ArrayList<SwitchTiesFile> arrayList = localDB.getAllSwitchTiesFiles();
            addSwitchRows(arrayList);
        }else if(whatCat.equals("OtherTrack")){
            ArrayList<OTMFile> arrayList = localDB.getAllOTMFiles();
            addOTMRows(arrayList);
        }else if(whatCat.equals("Other")){
            ArrayList<OtherFile> arrayList = localDB.getAllOtherFiles();
            addOtherRows(arrayList);
        }else if(whatCat.equals("Crossings")){
            ArrayList<CrossingsFile> arrayList = localDB.getAllCrossingsFiles();
            addCrossingsRows(arrayList);
        }else if(whatCat.equals("Crossties")){
            ArrayList<CrosstiesFile> arrayList = localDB.getAllCrosstiesFiles();
            addCrosstiesRows(arrayList);
        }else if(whatCat.equals("Issue")){
            ArrayList<IssueFile> arrayList = localDB.getAllIssuesFiles();
            addIssueRows(arrayList);
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String whatCat =  LocalDBHelper.getDataInSharedPreference(getApplicationContext(),"cat");
                Intent intent = new Intent(getApplicationContext(), MenuAdmin.class);
                if(whatCat.equals("Turnout")){
                    TurnoutsFile turnoutsFile = new TurnoutsFile();
                    turnoutsFile.theID = id.getText().toString().trim();
                    turnoutsFile.thedescription = descp.getText().toString().trim();
                    turnoutsFile.theUnit = unit.getText().toString().trim();
                    turnoutsFile.theBillingRate = Integer.parseInt(billingrate.getText().toString().trim());
                    turnoutsFile.theUnitCost = Integer.parseInt(unitcost.getText().toString().trim());
                    turnoutsFile.theProduction = Integer.parseInt(production.getText().toString().trim());
                    turnoutsFile.theMarkup = Integer.parseInt(markup.getText().toString().trim());
                    localDB.updateTurnoutLine(turnoutsFile);

                }else if(whatCat.equals("Rail")){
                    RailFile turnoutsFile = new RailFile();
                    turnoutsFile.theID = id.getText().toString().trim();
                    turnoutsFile.thedescription = descp.getText().toString().trim();
                    turnoutsFile.theUnit = unit.getText().toString().trim();
                    turnoutsFile.theBillingRate = Integer.parseInt(billingrate.getText().toString().trim());
                    turnoutsFile.theUnitCost = Integer.parseInt(unitcost.getText().toString().trim());
                    turnoutsFile.theProduction = Integer.parseInt(production.getText().toString().trim());
                    turnoutsFile.theMarkup = Integer.parseInt(markup.getText().toString().trim());
                    localDB.updateRailLine(turnoutsFile);
                }else if(whatCat.equals("Switches")){
                    SwitchTiesFile turnoutsFile = new SwitchTiesFile();
                    turnoutsFile.theID = id.getText().toString().trim();
                    turnoutsFile.thedescription = descp.getText().toString().trim();
                    turnoutsFile.theUnit = unit.getText().toString().trim();
                    turnoutsFile.theBillingRate = Integer.parseInt(billingrate.getText().toString().trim());
                    turnoutsFile.theUnitCost = Integer.parseInt(unitcost.getText().toString().trim());
                    turnoutsFile.theProduction = Integer.parseInt(production.getText().toString().trim());
                    turnoutsFile.theMarkup = Integer.parseInt(markup.getText().toString().trim());
                    localDB.updateSwitchLine(turnoutsFile);
                }else if(whatCat.equals("OtherTrack")){
                    OTMFile turnoutsFile = new OTMFile();
                    turnoutsFile.theID = id.getText().toString().trim();
                    turnoutsFile.thedescription = descp.getText().toString().trim();
                    turnoutsFile.theUnit = unit.getText().toString().trim();
                    turnoutsFile.theBillingRate = Integer.parseInt(billingrate.getText().toString().trim());
                    turnoutsFile.theUnitCost = Integer.parseInt(unitcost.getText().toString().trim());
                    turnoutsFile.theProduction = Integer.parseInt(production.getText().toString().trim());
                    turnoutsFile.theMarkup = Integer.parseInt(markup.getText().toString().trim());
                    localDB.updateOTMLine(turnoutsFile);
                }else if(whatCat.equals("Other")){
                    OtherFile turnoutsFile = new OtherFile();
                    turnoutsFile.theID = id.getText().toString().trim();
                    turnoutsFile.thedescription = descp.getText().toString().trim();
                    turnoutsFile.theUnit = unit.getText().toString().trim();
                    turnoutsFile.theBillingRate = Integer.parseInt(billingrate.getText().toString().trim());
                    turnoutsFile.theUnitCost = Integer.parseInt(unitcost.getText().toString().trim());
                    turnoutsFile.theProduction = Integer.parseInt(production.getText().toString().trim());
                    turnoutsFile.theMarkup = Integer.parseInt(markup.getText().toString().trim());
                    localDB.updateOtherLine(turnoutsFile);
                }else if(whatCat.equals("Crossings")){
                    CrossingsFile turnoutsFile = new CrossingsFile();
                    turnoutsFile.theID = id.getText().toString().trim();
                    turnoutsFile.thedescription = descp.getText().toString().trim();
                    turnoutsFile.theUnit = unit.getText().toString().trim();
                    turnoutsFile.theBillingRate = Integer.parseInt(billingrate.getText().toString().trim());
                    turnoutsFile.theUnitCost = Integer.parseInt(unitcost.getText().toString().trim());
                    turnoutsFile.theProduction = Integer.parseInt(production.getText().toString().trim());
                    turnoutsFile.theMarkup = Integer.parseInt(markup.getText().toString().trim());
                    localDB.updateCrossingsLine(turnoutsFile);
                }else if(whatCat.equals("Crossties")){
                    CrosstiesFile turnoutsFile = new CrosstiesFile();
                    turnoutsFile.theID = id.getText().toString().trim();
                    turnoutsFile.thedescription = descp.getText().toString().trim();
                    turnoutsFile.theUnit = unit.getText().toString().trim();
                    turnoutsFile.theBillingRate = Integer.parseInt(billingrate.getText().toString().trim());
                    turnoutsFile.theUnitCost = Integer.parseInt(unitcost.getText().toString().trim());
                    turnoutsFile.theProduction = Integer.parseInt(production.getText().toString().trim());
                    turnoutsFile.theMarkup = Integer.parseInt(markup.getText().toString().trim());
                    localDB.updateCrossTiesLine(turnoutsFile);
                }else if(whatCat.equals("Issue")){
                    IssueFile turnoutsFile = new IssueFile();
                    turnoutsFile.theID = id.getText().toString().trim();
                    turnoutsFile.thedescription = descp.getText().toString().trim();
                    turnoutsFile.theUnit = unit.getText().toString().trim();
                    turnoutsFile.theBillingRate = Integer.parseInt(billingrate.getText().toString().trim());
                    turnoutsFile.theUnitCost = Integer.parseInt(unitcost.getText().toString().trim());
                    turnoutsFile.theProduction = Integer.parseInt(production.getText().toString().trim());
                    turnoutsFile.theMarkup = Integer.parseInt(markup.getText().toString().trim());
                    localDB.updateIssueLine(turnoutsFile);
                }
                startActivity(intent);
            }
        });

    }
    private void addTurnoutRows(ArrayList<TurnoutsFile> stateFiles){
        String rowid = LocalDBHelper.getDataInSharedPreference(this,"rowviewing");
        for(int i = 0; i < stateFiles.size();i++){
            if(stateFiles.get(i).theID.equals(rowid)){
                descp.setText(stateFiles.get(i).thedescription);
                unit.setText(stateFiles.get(i).theUnit);
                unitcost.setText(String.valueOf(stateFiles.get(i).theUnitCost));
                markup.setText(String.valueOf(stateFiles.get(i).theMarkup));
                billingrate.setText(String.valueOf(stateFiles.get(i).theBillingRate));
                production.setText(String.valueOf(stateFiles.get(i).theProduction));
            }
        }
        id.setText(rowid);
    }
    private void addSwitchRows(ArrayList<SwitchTiesFile> stateFiles){
        String rowid = LocalDBHelper.getDataInSharedPreference(this,"rowviewing");
        for(int i = 0; i < stateFiles.size();i++){
            if(stateFiles.get(i).theID.equals(rowid)){
                descp.setText(stateFiles.get(i).thedescription);
                unit.setText(stateFiles.get(i).theUnit);
                unitcost.setText(String.valueOf(stateFiles.get(i).theUnitCost));
                markup.setText(String.valueOf(stateFiles.get(i).theMarkup));
                billingrate.setText(String.valueOf(stateFiles.get(i).theBillingRate));
                production.setText(String.valueOf(stateFiles.get(i).theProduction));
            }
        }
        id.setText(rowid);
    }
    private void addCrossingsRows(ArrayList<CrossingsFile> stateFiles){
        String rowid = LocalDBHelper.getDataInSharedPreference(this,"rowviewing");
        for(int i = 0; i < stateFiles.size();i++){
            if(stateFiles.get(i).theID.equals(rowid)){
                descp.setText(stateFiles.get(i).thedescription);
                unit.setText(stateFiles.get(i).theUnit);
                unitcost.setText(String.valueOf(stateFiles.get(i).theUnitCost));
                markup.setText(String.valueOf(stateFiles.get(i).theMarkup));
                billingrate.setText(String.valueOf(stateFiles.get(i).theBillingRate));
                production.setText(String.valueOf(stateFiles.get(i).theProduction));
            }
        }
        id.setText(rowid);
    }
    private void addCrosstiesRows(ArrayList<CrosstiesFile> stateFiles){
        String rowid = LocalDBHelper.getDataInSharedPreference(this,"rowviewing");
        for(int i = 0; i < stateFiles.size();i++){
            if(stateFiles.get(i).theID.equals(rowid)){
                descp.setText(stateFiles.get(i).thedescription);
                unit.setText(stateFiles.get(i).theUnit);
                unitcost.setText(String.valueOf(stateFiles.get(i).theUnitCost));
                markup.setText(String.valueOf(stateFiles.get(i).theMarkup));
                billingrate.setText(String.valueOf(stateFiles.get(i).theBillingRate));
                production.setText(String.valueOf(stateFiles.get(i).theProduction));
            }
        }
        id.setText(rowid);
    }
    private void addIssueRows(ArrayList<IssueFile> stateFiles){
        String rowid = LocalDBHelper.getDataInSharedPreference(this,"rowviewing");
        for(int i = 0; i < stateFiles.size();i++){
            if(stateFiles.get(i).theID.equals(rowid)){
                descp.setText(stateFiles.get(i).thedescription);
                unit.setText(stateFiles.get(i).theUnit);
                unitcost.setText(String.valueOf(stateFiles.get(i).theUnitCost));
                markup.setText(String.valueOf(stateFiles.get(i).theMarkup));
                billingrate.setText(String.valueOf(stateFiles.get(i).theBillingRate));
                production.setText(String.valueOf(stateFiles.get(i).theProduction));
            }
        }
        id.setText(rowid);
    }
    private void addOtherRows(ArrayList<OtherFile> stateFiles){
        String rowid = LocalDBHelper.getDataInSharedPreference(this,"rowviewing");
        for(int i = 0; i < stateFiles.size();i++){
            if(stateFiles.get(i).theID.equals(rowid)){
                descp.setText(stateFiles.get(i).thedescription);
                unit.setText(stateFiles.get(i).theUnit);
                unitcost.setText(String.valueOf(stateFiles.get(i).theUnitCost));
                markup.setText(String.valueOf(stateFiles.get(i).theMarkup));
                billingrate.setText(String.valueOf(stateFiles.get(i).theBillingRate));
                production.setText(String.valueOf(stateFiles.get(i).theProduction));
            }
        }
        id.setText(rowid);
    }
    private void addOTMRows(ArrayList<OTMFile> stateFiles){
        String rowid = LocalDBHelper.getDataInSharedPreference(this,"rowviewing");
        for(int i = 0; i < stateFiles.size();i++){
            if(stateFiles.get(i).theID.equals(rowid)){
                descp.setText(stateFiles.get(i).thedescription);
                unit.setText(stateFiles.get(i).theUnit);
                unitcost.setText(String.valueOf(stateFiles.get(i).theUnitCost));
                markup.setText(String.valueOf(stateFiles.get(i).theMarkup));
                billingrate.setText(String.valueOf(stateFiles.get(i).theBillingRate));
                production.setText(String.valueOf(stateFiles.get(i).theProduction));
            }
        }
        id.setText(rowid);
    }
    private void addRailRows(ArrayList<RailFile> stateFiles){
        String rowid = LocalDBHelper.getDataInSharedPreference(this,"rowviewing");
        for(int i = 0; i < stateFiles.size();i++){
            if(stateFiles.get(i).theID.equals(rowid)){
                descp.setText(stateFiles.get(i).thedescription);
                unit.setText(stateFiles.get(i).theUnit);
                unitcost.setText(String.valueOf(stateFiles.get(i).theUnitCost));
                markup.setText(String.valueOf(stateFiles.get(i).theMarkup));
                billingrate.setText(String.valueOf(stateFiles.get(i).theBillingRate));
                production.setText(String.valueOf(stateFiles.get(i).theProduction));
            }
        }
        id.setText(rowid);
    }


}

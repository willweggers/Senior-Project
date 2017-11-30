package com.example.seniorproject;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.seniorproject.Admin.MenuAdmin;
import com.example.seniorproject.DB.AccountFields;
import com.example.seniorproject.DB.InspectionContract;
import com.example.seniorproject.DB.LocalDBHelper;
import com.example.seniorproject.DB.MasterFileObjects.CrossingsFile;
import com.example.seniorproject.DB.MasterFileObjects.CrosstiesFile;
import com.example.seniorproject.DB.MasterFileObjects.IssueFile;
import com.example.seniorproject.DB.MasterFileObjects.LaborInstallFile;
import com.example.seniorproject.DB.MasterFileObjects.MobilizationFile;
import com.example.seniorproject.DB.MasterFileObjects.OTMFile;
import com.example.seniorproject.DB.MasterFileObjects.OtherFile;
import com.example.seniorproject.DB.MasterFileObjects.PriorityFile;
import com.example.seniorproject.DB.MasterFileObjects.RailFile;
import com.example.seniorproject.DB.MasterFileObjects.StateFile;
import com.example.seniorproject.DB.MasterFileObjects.SwitchTiesFile;
import com.example.seniorproject.DB.MasterFileObjects.TurnoutsFile;
import com.example.seniorproject.DB.MasterFileTable;
import com.example.seniorproject.Manager.MenuManager;
import com.example.seniorproject.TempDB.TempDB;
import com.example.seniorproject.TrackInspector.MenuTI;

import java.util.ArrayList;


public class MainActivityLogin extends AppCompatActivity {
    private Button submitButton;
    private EditText enterUserName;
    private EditText enterPass;
    private String userName;
    private String passWord;
    private LocalDBHelper localDB;
    private ArrayList<AccountFields> allAccounts = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        submitButton = (Button) findViewById(R.id.submit);
        enterUserName = (EditText) findViewById(R.id.userName);
        enterPass = (EditText) findViewById(R.id.passWord);
        localDB = LocalDBHelper.getInstance(this);
//        Log.e("SOMETHING", Integer.toString(localDB.getAllAccounts().size()));
        createAdminAccountIfEmpty();
        tempAddRowsMasterFiles();
        setSubmitButton();
    }
    private void createAdminAccountIfEmpty(){
//
      if(localDB.getAllAccounts().size() == 0){
            AccountFields adminAccount = new AccountFields();
            adminAccount.accountType = AccountInfo.ADMIN_PREM;
            adminAccount.firstName = "default";
            adminAccount.lastName = "admin";
            adminAccount.initials = "da";
            adminAccount.userName = AccountInfo.adminUN;
            adminAccount.passWord = AccountInfo.md5(AccountInfo.adminPass);
            localDB.addAccount(adminAccount);
        }
    }

    private void setSubmitButton() {
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            searchAccounts();
            }
        });
    }

    private void searchAccounts(){
        assignUNPass();
        allAccounts.clear();
        allAccounts = localDB.getAllAccounts();
        for(int i = 0; i < allAccounts.size();i++) {
            String currentUsername = allAccounts.get(i).userName;
            String currentPassword = allAccounts.get(i).passWord;
            String currentPremissions = allAccounts.get(i).accountType;
            if (userName.equals(currentUsername)) {
                if (AccountInfo.md5(passWord).equals(currentPassword)) {
                    if (currentPremissions.equals(AccountInfo.ADMIN_PREM)) {
                        Intent intent = new Intent(MainActivityLogin.this, MenuAdmin.class);
                        LocalDBHelper.storeDataInSharedPreference(getApplicationContext(),"username", currentUsername);
                        startActivity(intent);
                        enterUserName.setText(null);
                        enterPass.setText(null);
                        break;
                    } else if (currentPremissions.equals(AccountInfo.MANAGER_PREM)) {
                        Intent intent = new Intent(MainActivityLogin.this, MenuManager.class);
                        LocalDBHelper.storeDataInSharedPreference(getApplicationContext(),"username", currentUsername);

                        startActivity(intent);
                        enterUserName.setText(null);
                        enterPass.setText(null);
                        break;
                    } else if (currentPremissions.equals(AccountInfo.TI_PREM)) {
                        Intent intent = new Intent(MainActivityLogin.this, MenuTI.class);
                        LocalDBHelper.storeDataInSharedPreference(getApplicationContext(),"username", currentUsername);
                        startActivity(intent);
                        AccountFields accountFields= localDB.getAccountByUser(currentUsername);
                        TempDB tempDB = TempDB.getInstance(this);

                        enterUserName.setText(null);
                        enterPass.setText(null);
                        break;
                    } else {
                        AccountInfo.showMessage("Error account doesnt have premission set.", getApplicationContext());
                    }

                } else {
                    AccountInfo.showMessage("Password is invalid.", getApplicationContext());
                }
            } else if (i == allAccounts.size()-1) {
                AccountInfo.showMessage("Username or Password is invalid.", getApplicationContext());
            }


            }
        }

    private void assignUNPass(){
        passWord = enterPass.getText().toString().trim();
        userName = enterUserName.getText().toString().trim();
    }
    private void tempAddRowsMasterFiles(){
        if(localDB.getAllStateFiles().size() == 0){
            StateFile stateFile = new StateFile();
            stateFile.theID = "AK";
            stateFile.thedescription = "Alaska";
            stateFile.theOtherTaxPerc = 0;
            stateFile.theOtherTaxOn = "ALL";
            stateFile.theLaborTax = "No";
            localDB.addStateFiles(stateFile);
            stateFile = new StateFile();
            stateFile.theID = "HI";
            stateFile.thedescription = "Hawaii";
            stateFile.theOtherTaxPerc = 0;
            stateFile.theOtherTaxOn = "ALL";
            stateFile.theLaborTax = "Yes";
            localDB.addStateFiles(stateFile);
            stateFile = new StateFile();
            stateFile.theID = "MS";
            stateFile.thedescription = "Mississippi";
            stateFile.theOtherTaxPerc = 3;
            stateFile.theOtherTaxOn = "ALL";
            stateFile.theLaborTax = "No";
            localDB.addStateFiles(stateFile);

        }
        if(localDB.getAllPriorityFiles().size() == 0){
            PriorityFile stateFile = new PriorityFile();
            stateFile.theID = 1;
            stateFile.thedescription = "Emergency Condition - Replace Immediately";
            localDB.addPriorityFiles(stateFile);
            stateFile = new PriorityFile();
            stateFile.theID = 2;
            stateFile.thedescription = "High Priority - Repair in 30 days";
            localDB.addPriorityFiles(stateFile);
            stateFile = new PriorityFile();
            stateFile.theID = 3;
            stateFile.thedescription = "Medium Priority - Repair in 90 days";
            localDB.addPriorityFiles(stateFile);
            stateFile = new PriorityFile();
            stateFile.theID = 4;
            stateFile.thedescription = "Low Priority - Repair in 180 days";
            localDB.addPriorityFiles(stateFile);
            stateFile = new PriorityFile();
            stateFile.theID = 5;
            stateFile.thedescription = "Very Low Priority - Budget for Next Year Maintenance";
            localDB.addPriorityFiles(stateFile);
        }
        if(localDB.getAllMobilizationFiles().size() == 0){
            MobilizationFile stateFile = new MobilizationFile();
            stateFile.theID = "STANDARD";
            stateFile.thedescription = "Standard Mobilization";
            stateFile.thetravel = 7;
            stateFile.theMinimum = 500;
            MobilizationFile stateFile1 = new MobilizationFile();
            stateFile.theID = "SURFACING";
            stateFile.thedescription = "Surfacing Mobilization";
            stateFile.thetravel = 12;
            stateFile.theMinimum = 5000;
            localDB.addMobilizationFiles(stateFile1);
            localDB.addMobilizationFiles(stateFile);


        }
        if(localDB.getAllLaborFiles().size() == 0){
            LaborInstallFile stateFile = new LaborInstallFile();
            stateFile.theID = "CR-TC-OT-LO";
            stateFile.thedescription = "Track Crew, Overtime, Local";
            stateFile.theCrewRate = 3700;
            stateFile.thePerDiem = 0;
            localDB.addLaborFiles(stateFile);
            stateFile = new LaborInstallFile();
            stateFile.theID = "CR-WL-OT-TV";
            stateFile.thedescription = "Welding Crew, Overtime, Travel";
            stateFile.theCrewRate = 1000;
            stateFile.thePerDiem = 150;
            localDB.addLaborFiles(stateFile);
        }
        if(localDB.getAllCrossingsFiles().size()==0){
            CrossingsFile crossingsFile = new CrossingsFile();
            crossingsFile.theID = "COMPOSITE";
            crossingsFile.thedescription = "Crossing-Composite";
            crossingsFile.theBillingRate = 155;
            crossingsFile.theMarkup = 15;
            crossingsFile.theProduction = 60;
            crossingsFile.theUnit = "TF";
            crossingsFile.theUnitCost = 135;
            localDB.addCrossingFiles(crossingsFile);
            crossingsFile = new CrossingsFile();
            crossingsFile.theID = "PVCPIPE";
            crossingsFile.thedescription = "PVC-Pipe";
            crossingsFile.theBillingRate = 11;
            crossingsFile.theMarkup = 15;
            crossingsFile.theProduction = 1000;
            crossingsFile.theUnit = "TF";
            crossingsFile.theUnitCost = 10;
            localDB.addCrossingFiles(crossingsFile);
        }
        if(localDB.getAllCrosstiesFiles().size()==0){
            CrosstiesFile crossingsFile = new CrosstiesFile();
            crossingsFile.theID = "CT6-G3";
            crossingsFile.thedescription = "6\" x 8\"Grade 3 Crossties";
            crossingsFile.theBillingRate = 51;
            crossingsFile.theMarkup = 15;
            crossingsFile.theProduction = 80;
            crossingsFile.theUnit = "Each";
            crossingsFile.theUnitCost = 45;
            localDB.addCrosstiesFiles(crossingsFile);
             crossingsFile = new CrosstiesFile();
            crossingsFile.theID = "CT7-RX";
            crossingsFile.thedescription = "7\" x 9\"Relay Crosstie";
            crossingsFile.theBillingRate = 35;
            crossingsFile.theMarkup = 15;
            crossingsFile.theProduction = 60;
            crossingsFile.theUnit = "LF";
            crossingsFile.theUnitCost = 30;
            localDB.addCrosstiesFiles(crossingsFile);
        } if(localDB.getAllIssuesFiles().size()==0){
            IssueFile crossingsFile = new IssueFile();
            crossingsFile.theID = "CRIBMUD";
            crossingsFile.thedescription = "Crib out Mud";
            crossingsFile.theBillingRate = 0;
            crossingsFile.theMarkup = 15;
            crossingsFile.theProduction = 200;
            crossingsFile.theUnit = "TF";
            crossingsFile.theUnitCost = 0;
            localDB.addIssueFiles(crossingsFile);
             crossingsFile = new IssueFile();
            crossingsFile.theID = "REGULATE";
            crossingsFile.thedescription = "Regulate Track";
            crossingsFile.theBillingRate = 0;
            crossingsFile.theMarkup = 15;
            crossingsFile.theProduction = 1000;
            crossingsFile.theUnit = "TF";
            crossingsFile.theUnitCost = 0;
            localDB.addIssueFiles(crossingsFile);
        } if(localDB.getAllOTMFiles().size()==0){
            OTMFile crossingsFile = new OTMFile();
            crossingsFile.theID = "AN-05-N";
            crossingsFile.thedescription = "Rail Anchors - 5\" Base - New";
            crossingsFile.theBillingRate = 2;
            crossingsFile.theMarkup = 15;
            crossingsFile.theProduction = 1000;
            crossingsFile.theUnit = "Each";
            crossingsFile.theUnitCost = 2;
            localDB.addOTMFiles(crossingsFile);
            crossingsFile = new OTMFile();
            crossingsFile.theID = "TS-200";
            crossingsFile.thedescription = "Track Spikes - 200 lb Keg";
            crossingsFile.theBillingRate = 150;
            crossingsFile.theMarkup = 15;
            crossingsFile.theProduction = 1000;
            crossingsFile.theUnit = "Keg";
            crossingsFile.theUnitCost = 130;
            localDB.addOTMFiles(crossingsFile);

        } if(localDB.getAllOtherFiles().size()==0){
            OtherFile crossingsFile = new OtherFile();
            crossingsFile.theID = "ASPHALT";
            crossingsFile.thedescription = "Asphalt Work";
            crossingsFile.theBillingRate = 115;
            crossingsFile.theMarkup = 15;
            crossingsFile.theProduction = 80;
            crossingsFile.theUnit = "Ton";
            crossingsFile.theUnitCost = 100;
            localDB.addOtherFiles(crossingsFile);
            crossingsFile = new OtherFile();
            crossingsFile.theID = "WALBAL";
            crossingsFile.thedescription = "Walkway Ballst";
            crossingsFile.theBillingRate = 86;
            crossingsFile.theMarkup = 15;
            crossingsFile.theProduction = 500;
            crossingsFile.theUnit = "Ton";
            crossingsFile.theUnitCost = 75;
            localDB.addOtherFiles(crossingsFile);

        } if(localDB.getAllRailFiles().size()==0){
            RailFile crossingsFile = new RailFile();
            crossingsFile.theID = "RA-60 AS-N";
            crossingsFile.thedescription = "60 AS ASCE Rail - New";
            crossingsFile.theBillingRate = 359;
            crossingsFile.theMarkup = 15;
            crossingsFile.theProduction = 8;
            crossingsFile.theUnit = "EACH";
            crossingsFile.theUnitCost = 312;
            localDB.addRailFiles(crossingsFile);
             crossingsFile = new RailFile();
            crossingsFile.theID = "RA-155 PS-X";
            crossingsFile.thedescription = "155 PS PS Rail - Relay";
            crossingsFile.theBillingRate = 811;
            crossingsFile.theMarkup = 15;
            crossingsFile.theProduction = 8;
            crossingsFile.theUnit = "EACH";
            crossingsFile.theUnitCost = 705;
            localDB.addRailFiles(crossingsFile);

        } if(localDB.getAllSwitchTiesFiles().size()==0){

            SwitchTiesFile crossingsFile = new SwitchTiesFile();
            crossingsFile.theID = "ST09-N";
            crossingsFile.thedescription = "7\" x 9\" x 9' Switch Tie - New";
            crossingsFile.theBillingRate = 81;
            crossingsFile.theMarkup = 15;
            crossingsFile.theProduction = 20;
            crossingsFile.theUnit = "Each";
            crossingsFile.theUnitCost = 71;
            localDB.addSwitchFtiles(crossingsFile);
            crossingsFile = new SwitchTiesFile();
            crossingsFile.theID = "ST17-X";
            crossingsFile.thedescription = "7\" x 9\" x 17' Switch Tie - Relay";
            crossingsFile.theBillingRate = 92;
            crossingsFile.theMarkup = 15;
            crossingsFile.theProduction = 10;
            crossingsFile.theUnit = "Each";
            crossingsFile.theUnitCost = 80;
            localDB.addSwitchFtiles(crossingsFile);



        } if(localDB.getAllTurnoutFiles().size()==0){
            TurnoutsFile crossingsFile = new TurnoutsFile();
            crossingsFile.theID = "BR-AD-N";
            crossingsFile.thedescription = "Brace, Adjustable, New";
            crossingsFile.theBillingRate = 35;
            crossingsFile.theMarkup = 15;
            crossingsFile.theProduction = 500;
            crossingsFile.theUnit = "Each";
            crossingsFile.theUnitCost = 30;
            localDB.addTuroutFiles(crossingsFile);
             crossingsFile = new TurnoutsFile();
            crossingsFile.theID = "SS-SH-N";
            crossingsFile.thedescription = "Switch Stand, Shims";
            crossingsFile.theBillingRate = 3;
            crossingsFile.theMarkup = 15;
            crossingsFile.theProduction = 200;
            crossingsFile.theUnit = "Each";
            crossingsFile.theUnitCost = 3;
            localDB.addTuroutFiles(crossingsFile);

        }


    }

}

//        }

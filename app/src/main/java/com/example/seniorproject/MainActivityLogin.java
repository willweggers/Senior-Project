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
//                        if(tempDB.isUserTableEmpty()){
//                            tempDB.addAccount(accountFields);
//                        }
//                        else{
//                            tempDB.deleteAllEntriesInAccountTable();
//                            tempDB.addAccount(accountFields);
//                        }
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
            stateFile.theID = "id1";
            stateFile.thedescription = "descp";
            stateFile.theOtherTaxPerc = 1;
            stateFile.theOtherTaxOn = "okokok";
            stateFile.theLaborTax = "each";
            localDB.addStateFiles(stateFile);

        }
        if(localDB.getAllPriorityFiles().size() == 0){
            PriorityFile stateFile = new PriorityFile();
            stateFile.theID = 1;
            stateFile.thedescription = "high";
            localDB.addPriorityFiles(stateFile);

        }
        if(localDB.getAllMobilizationFiles().size() == 0){
            MobilizationFile stateFile = new MobilizationFile();
            stateFile.theID = "id1";
            stateFile.thedescription = "descp";
            stateFile.thetravel = 1;
            stateFile.theMinimum = 2;
            localDB.addMobilizationFiles(stateFile);

        }
        if(localDB.getAllLaborFiles().size() == 0){
            LaborInstallFile stateFile = new LaborInstallFile();
            stateFile.theID = "id1";
            stateFile.thedescription = "descp";
            stateFile.theCrewRate = 1;
            stateFile.thePerDiem = 11;
            localDB.addLaborFiles(stateFile);

        }
        if(localDB.getAllCrossingsFiles().size()==0){
            CrossingsFile crossingsFile = new CrossingsFile();
            crossingsFile.theID = "id";
            crossingsFile.thedescription = "askdjhadsh";
            crossingsFile.theBillingRate = 12121;
            crossingsFile.theMarkup = 1298;
            crossingsFile.theProduction = 12;
            crossingsFile.theUnit = "Each";
            crossingsFile.theUnitCost = 1292198;
            localDB.addCrossingFiles(crossingsFile);
        }
        if(localDB.getAllCrosstiesFiles().size()==0){
            CrosstiesFile crossingsFile = new CrosstiesFile();
            crossingsFile.theID = "id";
            crossingsFile.thedescription = "askdjhadsh";
            crossingsFile.theBillingRate = 12100021;
            crossingsFile.theMarkup = 1298;
            crossingsFile.theProduction = 12;
            crossingsFile.theUnit = "Each";
            crossingsFile.theUnitCost = 1292198;
            localDB.addCrosstiesFiles(crossingsFile);
        } if(localDB.getAllIssuesFiles().size()==0){
            IssueFile crossingsFile = new IssueFile();
            crossingsFile.theID = "id";
            crossingsFile.thedescription = "askdjhadsh";
            crossingsFile.theBillingRate = 12111121;
            crossingsFile.theMarkup = 1298;
            crossingsFile.theProduction = 12;
            crossingsFile.theUnit = "Each";
            crossingsFile.theUnitCost = 1292198;
            localDB.addIssueFiles(crossingsFile);
        } if(localDB.getAllOTMFiles().size()==0){
            OTMFile crossingsFile = new OTMFile();
            crossingsFile.theID = "id";
            crossingsFile.thedescription = "askdjhadsh";
            crossingsFile.theBillingRate = 12222121;
            crossingsFile.theMarkup = 1298;
            crossingsFile.theProduction = 12;
            crossingsFile.theUnit = "Each";
            crossingsFile.theUnitCost = 1292198;
            localDB.addOTMFiles(crossingsFile);

        } if(localDB.getAllOtherFiles().size()==0){
            OtherFile crossingsFile = new OtherFile();
            crossingsFile.theID = "id";
            crossingsFile.thedescription = "askdjhadsh";
            crossingsFile.theBillingRate = 12333121;
            crossingsFile.theMarkup = 1298;
            crossingsFile.theProduction = 12;
            crossingsFile.theUnit = "Each";
            crossingsFile.theUnitCost = 1292198;
            localDB.addOtherFiles(crossingsFile);

        } if(localDB.getAllRailFiles().size()==0){
            RailFile crossingsFile = new RailFile();
            crossingsFile.theID = "id";
            crossingsFile.thedescription = "askdjhadsh";
            crossingsFile.theBillingRate = 12144421;
            crossingsFile.theMarkup = 1298;
            crossingsFile.theProduction = 12;
            crossingsFile.theUnit = "Each";
            crossingsFile.theUnitCost = 1292198;
            localDB.addRailFiles(crossingsFile);

        } if(localDB.getAllSwitchTiesFiles().size()==0){
            SwitchTiesFile crossingsFile = new SwitchTiesFile();
            crossingsFile.theID = "id";
            crossingsFile.thedescription = "askdjhadsh";
            crossingsFile.theBillingRate = 12155521;
            crossingsFile.theMarkup = 1298;
            crossingsFile.theProduction = 12;
            crossingsFile.theUnit = "Each";
            crossingsFile.theUnitCost = 1292198;
            localDB.addSwitchFtiles(crossingsFile);

        } if(localDB.getAllTurnoutFiles().size()==0){
            TurnoutsFile crossingsFile = new TurnoutsFile();
            crossingsFile.theID = "id";
            crossingsFile.thedescription = "askdjhadsh";
            crossingsFile.theBillingRate = 12166621;
            crossingsFile.theMarkup = 1298;
            crossingsFile.theProduction = 12;
            crossingsFile.theUnit = "Each";
            crossingsFile.theUnitCost = 1292198;
            localDB.addTuroutFiles(crossingsFile);

        }


    }

}

//        }

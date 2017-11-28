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
                        if(tempDB.isUserTableEmpty()){
                            tempDB.addAccount(accountFields);
                        }
                        else{
                            tempDB.deleteAllEntriesInAccountTable();
                            tempDB.addAccount(accountFields);
                        }
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

}

//        }

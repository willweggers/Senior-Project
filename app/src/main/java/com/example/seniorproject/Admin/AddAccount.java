package com.example.seniorproject.Admin;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.seniorproject.AccountInfo;
import com.example.seniorproject.CreateDB;
import com.example.seniorproject.MainActivityLogin;
import com.example.seniorproject.R;
import com.example.seniorproject.TrackInspector.InspectionForm;
import com.example.seniorproject.TrackInspector.MapTI;

/**
 * Created by willw on 11/5/2017.
 */

public class AddAccount extends AppCompatActivity {
    private Button submitButton;
    private EditText enterUserName;
    private String userName;
    private String passWord;
    private Spinner dropdown;
    private String type;
    private SQLiteDatabase writeDB;
    private SQLiteDatabase readDB;
    private Cursor cursor;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_account_form);
        submitButton = (Button) findViewById(R.id.addsubmit);
        enterUserName = (EditText) findViewById(R.id.addusername);
        dropdown = (Spinner)findViewById(R.id.addtype);
        String[] items = new String[]{"Track Inspector", "Manager"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        writeDB = new CreateDB(this).getWritableDatabase();
        readDB = new CreateDB(this).getReadableDatabase();
        cursor = readDB.rawQuery("SELECT * FROM " + CreateDB.TABLE_NAME, null);
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setText();
                if(checkUsername()){

                    passWord = AccountInfo.md5("");
                    ContentValues values = new ContentValues();
                    values.put(CreateDB.COLUMN_NAME, userName);
                    values.put(CreateDB.COLUMN_TYPE, type);
                    values.put(CreateDB.COLUMN_PASS, passWord);
                    long newRowId = writeDB.insert(CreateDB.TABLE_NAME, null, values);
                    enterUserName.setText(null);
                    AccountInfo.showMessage("Account successfully created.",getApplicationContext());
                    handler.postDelayed(mLaunchTask,1000);


                }

            }
        });

    }
    private Runnable mLaunchTask = new Runnable() {
        public void run() {
            Intent intent = new Intent(AddAccount.this, ManageAccounts.class);
            startActivity(intent);
        }
    };
    private boolean checkUsername(){

        while(cursor.moveToNext()){
            if(cursor.getString(0).equals(userName)){
                    enterUserName.setText(null);
                    AccountInfo.showMessage("Username has been taken.",getApplicationContext());
                    return false;
            }
        }
        return true;

    }
//    private boolean checkPassword(){
//
//        if(!verifypassWord.equals(passWord)){
//            verifyenterPass.setText(null);
//            enterPass.setText(null);
//            AccountInfo.showMessage("Passwords dont match.", getApplicationContext());
//            return false;
//        }
//        return true;
//    }
    private void setText(){
        userName = enterUserName.getText().toString();
        type = dropdown.getSelectedItem().toString();
        if(type.equals("Administrator")){
            type = AccountInfo.ADMIN_PREM;
        }else if(type.equals("Manager")){
            type = AccountInfo.MANAGER_PREM;
        }else{
            type = AccountInfo.TI_PREM;
        }


    }
}

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
import com.example.seniorproject.Manager.MenuManager;
import com.example.seniorproject.TrackInspector.MenuTI;


public class MainActivityLogin extends AppCompatActivity {
    private Button submitButton;
    private EditText enterUserName;
    private EditText enterPass;

    private String userName;
    private String passWord;
    private SQLiteDatabase localDB;
    private SQLiteDatabase readDB;

    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        submitButton = (Button) findViewById(R.id.submit);
        enterUserName = (EditText) findViewById(R.id.userName);
        enterPass = (EditText) findViewById(R.id.passWord);
        localDB = new CreateDB(this).getWritableDatabase();
        readDB = new CreateDB(this).getReadableDatabase();
        if(CreateDB.isDBEmpty(localDB)) {
//            AccountInfo.showMessage("empty database",getApplicationContext());
            ContentValues values = new ContentValues();
            values.put(CreateDB.COLUMN_NAME, AccountInfo.adminUN);
            values.put(CreateDB.COLUMN_TYPE, AccountInfo.ADMIN_PREM);
            values.put(CreateDB.COLUMN_PASS, AccountInfo.md5(AccountInfo.adminPass));
            long newRowId = localDB.insert(CreateDB.TABLE_NAME, null, values);
        }
        else{
//            AccountInfo.showMessage("not empty database",getApplicationContext());
        }
        cursor = readDB.rawQuery("SELECT * FROM " + CreateDB.TABLE_NAME, null);
        cursor.moveToFirst();
//        cursor.moveToFirst();
//        while (cursor.moveToNext()){
//            AccountInfo.showMessage(cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2),getApplicationContext());
//
//        }
//        if(CreateDB.isDBEmpty(readDB)){
//                        AccountInfo.showMessage("empty database",getApplicationContext());
//
//        }
//        else{
//                        AccountInfo.showMessage("not empty database",getApplicationContext());
//
//        }

        setSubmitButton();
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
        cursor = readDB.rawQuery("SELECT * FROM " + CreateDB.TABLE_NAME, null);
        int amount = 0;
        cursor.moveToFirst();
//        cursor = readDB.rawQuery("SELECT * FROM " + CreateDB.TABLE_NAME, null);
//        cursor.moveToFirst();
//        AccountInfo.showMessage(Integer.toString(cursor.getCount()),getApplicationContext());
        do{
            amount++;
               if(userName.equals(cursor.getString(0))){
                   if(AccountInfo.md5(passWord).equals(cursor.getString(2))){
                           if(cursor.getString(1).equals(AccountInfo.ADMIN_PREM)){
                               Intent intent = new Intent(MainActivityLogin.this,MenuAdmin.class);
                               startActivity(intent);
                               enterUserName.setText(null);
                               enterPass.setText(null);
                               cursor.close();
                               break;
                           }
                           else if(cursor.getString(1).equals(AccountInfo.MANAGER_PREM)){
                               MenuManager.userNameManager = cursor.getString(0);
                               Intent intent = new Intent(MainActivityLogin.this,MenuManager.class);
                               startActivity(intent);
                               enterUserName.setText(null);
                               enterPass.setText(null);
                               cursor.close();

                               break;
                           }
                           else if(cursor.getString(1).equals(AccountInfo.TI_PREM)){
                               MenuTI.userNameTI = cursor.getString(0);
                               Intent intent = new Intent(MainActivityLogin.this,MenuTI.class);
                               startActivity(intent);
                               enterUserName.setText(null);
                               enterPass.setText(null);
                               cursor.close();

                               break;
                           }
                           else{
                               AccountInfo.showMessage("Error account doesnt have premission set.",getApplicationContext());
                           }

                   }
                   else{
                       AccountInfo.showMessage("Password is invalid.",getApplicationContext());
                   }
               }
               else if(amount == cursor.getCount()){
                   AccountInfo.showMessage("Username or Password is invalid.", getApplicationContext());
               }
                   //               else{
//                  // cursor.moveToNext();
//                   continue;
//               }


           }while (cursor.moveToNext());
//           cursor.close();
        }
//        else {
//            AccountInfo.showMessage("empty database",getApplicationContext());
//        }



    //}
    private void readDB(){
//        cursor = readDB.rawQuery("SELECT * FROM " + CreateDB.TABLE_NAME, null);
//
//        String[] projection = {
//                CreateDB.COLUMN_NAME,
//                CreateDB.COLUMN_TYPE,
//                CreateDB.COLUMN_PASS
//        };
//
//        String selection =
//                CreateDB.COLUMN_NAME + " like ? and " +
//                        CreateDB.COLUMN_TYPE + " like ? and " +
//                        CreateDB.COLUMN_PASS + " like ?";
//        String[] selectionArgs = { "My Title" };
//
//
//
//        cursor = readDB.query(
//                CreateDB.TABLE_NAME,     // The table to query
//                projection,                               // The columns to return
//                selection,                               // The columns for the WHERE clause
//                selectionArgs,                            // The values for the WHERE clause
//                null,                                     // don't group the rows
//                null,                                     // don't filter by row groups
////                null                                      // don't sort
//        );
//
//        Log.d(TAG, "The total cursor count is " + cursor.getCount());
//        binding.recycleView.setAdapter(new SampleRecyclerViewCursorAdapter(this, cursor));
    }
    private void assignUNPass(){
        passWord = enterPass.getText().toString().trim();
        userName = enterUserName.getText().toString().trim();
    }

}

//        }

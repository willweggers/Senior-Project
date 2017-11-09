package com.example.seniorproject.Manager;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.seniorproject.AccountInfo;
import com.example.seniorproject.CreateDB;
import com.example.seniorproject.MainActivityLogin;
import com.example.seniorproject.R;
import com.example.seniorproject.TrackInspector.FormatReport;
import com.example.seniorproject.TrackInspector.HeaderData;
import com.example.seniorproject.TrackInspector.MenuTI;

/**
 * Created by willw on 9/12/2017.
 */

public class MenuManager extends AppCompatActivity{
    private Button startInspection;
    private Button viewyourInspection;
    private Button viewTIInspections;
    private Button resumeInspection;
    private int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 99;
    public static String userNameManager;
    private Cursor cursor;
    private SQLiteDatabase localDB;
    private SQLiteDatabase readDB;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_manager);
        startInspection = (Button) findViewById(R.id.startInspectionmanager);
        viewyourInspection = (Button) findViewById(R.id.viewInspectionmanagermanager);
        viewTIInspections =  (Button) findViewById(R.id.viewInspectionmanager);
        resumeInspection = (Button) findViewById(R.id.resumeInspectionmanager);
        logout = (Button) findViewById(R.id.logoutmanager);
        FormatReport.usernameAccessingThis = userNameManager;
        localDB = new CreateDB(this).getWritableDatabase();
        readDB = new CreateDB(this).getReadableDatabase();
        cursor = readDB.rawQuery("SELECT * FROM " + CreateDB.TABLE_NAME + " WHERE username = ?", new String[]{userNameManager});
        cursor.moveToFirst();
        if(cursor.getString(2).equals(AccountInfo.md5(""))){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("New Account set your password below: ");

            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            builder.setView(input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String newPass = input.getText().toString();
                    ContentValues values = new ContentValues();
                    values.put(CreateDB.COLUMN_PASS, AccountInfo.md5(newPass));
                    localDB.update(CreateDB.TABLE_NAME, values, "username=?",new String[]{userNameManager});

                }
            });

            builder.show();
        }

        ActivityCompat.requestPermissions(MenuManager.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_ACCESS_FINE_LOCATION);
        setButtons();
        setTitle(userNameManager + " Manager Menu");
    }

    private void setButtons(){

        startInspection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuManager.this, HeaderData.class);
                startActivity(intent);
            }
        });


        viewTIInspections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListTrackInspectors.userUsing = userNameManager;
                Intent intent = new Intent(MenuManager.this, ListTrackInspectors.class);
                startActivity(intent);
            }
        });
        viewyourInspection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        resumeInspection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MenuManager.this, MainActivityLogin.class);
                startActivity(intent);
            }
        });
    }

}
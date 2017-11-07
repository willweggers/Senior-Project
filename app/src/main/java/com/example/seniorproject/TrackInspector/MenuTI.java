package com.example.seniorproject.TrackInspector;

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


/**
 * Created by willw on 10/5/2017.
 * menu page for TI. just used to declare intents between this page and whatever page it is suppose to go to.
 * currently startinspection is going to trackinspector page which is just the old demo trackinspection page.
 */

public class MenuTI extends AppCompatActivity{
    private Button startInspection;
    private Button viewInspection;
    private Button editInspection;
    private int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 99;
    private SQLiteDatabase localDB;
    private SQLiteDatabase readDB;
    public static String userNameTI;
    private Cursor cursor;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_track_inspector);
        localDB = new CreateDB(this).getWritableDatabase();
        readDB = new CreateDB(this).getReadableDatabase();
        startInspection = (Button) findViewById(R.id.startInspection);
        viewInspection = (Button) findViewById(R.id.viewInspection);
        editInspection = (Button) findViewById(R.id.editInspection);
        FormatReport.usernameAccessingThis = userNameTI;
        logout = (Button) findViewById(R.id.logoutti);
            cursor = readDB.rawQuery("SELECT * FROM " + CreateDB.TABLE_NAME + " WHERE username = ?", new String[]{userNameTI});
            cursor.moveToFirst();
            if (cursor.getString(2).equals(AccountInfo.md5(""))) {
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
                        localDB.update(CreateDB.TABLE_NAME, values, "username=?", new String[]{userNameTI});

                    }
                });

                builder.show();
            }


        ActivityCompat.requestPermissions(MenuTI.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_ACCESS_FINE_LOCATION);
        setButtons();
        setTitle("Menu");

    }

    private void setButtons(){

        startInspection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuTI.this, HeaderData.class);
                startActivity(intent);
            }
        });

        viewInspection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        editInspection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MenuTI.this, MainActivityLogin.class);
                startActivity(intent);
            }
        });
    }

}

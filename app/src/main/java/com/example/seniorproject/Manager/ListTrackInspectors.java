package com.example.seniorproject.Manager;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.seniorproject.AccountInfo;
import com.example.seniorproject.Admin.AddAccount;
import com.example.seniorproject.Admin.ManageAccounts;
import com.example.seniorproject.Admin.MenuAdmin;
import com.example.seniorproject.Admin.MenuManagerAdmin;
import com.example.seniorproject.Admin.MenuTIAdmin;
import com.example.seniorproject.CreateDB;
import com.example.seniorproject.R;

import java.util.ArrayList;

/**
 * Created by willw on 11/6/2017.
 */

public class ListTrackInspectors extends AppCompatActivity {
    private Button backmenu;
    private TableLayout accountTable;
    private SQLiteDatabase writeDB;
    private SQLiteDatabase readDB;
    private Cursor cursor;
    private String lastUN;
    private String lastType;
    private ArrayList<String> types = new ArrayList<>();
    public static String userUsing;
    public static String userViewing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_track_inspectors);
        backmenu = (Button) findViewById(R.id.adminbackmenu);
        accountTable = (TableLayout) findViewById(R.id.trackinspectortable);
        writeDB = new CreateDB(this).getWritableDatabase();
        readDB = new CreateDB(this).getReadableDatabase();

//        String adminusername = cursor.getString(0);


//                while (cursor.moveToNext()){
//            AccountInfo.showMessage(cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2),getApplicationContext());
//
//        }
        setAddAccounts();

        addRows();
    }
    private void setAddAccounts(){

        backmenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ListTrackInspectors.this, MenuManager.class);
                startActivity(intent);
            }
        });
    }
    private void addRows(){
        cursor = writeDB.rawQuery("SELECT * FROM " + CreateDB.TABLE_NAME, null);
        cursor.moveToFirst();
        accountTable = (TableLayout) findViewById(R.id.trackinspectortable);
        accountTable.removeAllViews();
        do {

            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            tableRow.setPadding(0, 20, 0, 20);
            tableRow.setWeightSum(10);

            for (int j = 0; j < 1; j++) {

                TextView textView = new TextView(this);
                if (j == 0) {
                    textView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 10.0f));
                }
//                    AccountInfo.showMessage(cursor.getString(j),getApplicationContext());
                textView.setText(cursor.getString(j));

                textView.setTypeface(null, Typeface.ITALIC);
                textView.setTextSize(25f);
                textView.setPadding(10,0,0,0);

                    lastUN = cursor.getString(0);
                    lastType = cursor.getString(1);


                    tableRow.addView(textView);

            }
            final String lastTypeFinal = lastType;
            tableRow.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(lastTypeFinal.equals("Track Inspector")){
                        MenuTIManager.currentUserName = userViewing;
                        Intent intent = new Intent(ListTrackInspectors.this, MenuTIManager.class);
                        if(userUsing.equals("Admin")) {
                            intent = new Intent(ListTrackInspectors.this, MenuTIAdmin.class);
                        }
                        startActivity(intent);
                    }
                }
            });
            if(!lastTypeFinal.equals(AccountInfo.ADMIN_PREM) && (!lastTypeFinal.equals(AccountInfo.MANAGER_PREM))) {
                accountTable.addView(tableRow);
            }
        } while (cursor.moveToNext());
        cursor.close();
    }

}

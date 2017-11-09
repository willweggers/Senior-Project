package com.example.seniorproject.Admin;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.seniorproject.AccountInfo;
import com.example.seniorproject.CreateDB;
import com.example.seniorproject.Manager.MenuManager;
import com.example.seniorproject.R;
import com.example.seniorproject.TrackInspector.MapTI;
import com.example.seniorproject.TrackInspector.MenuTI;

import java.util.ArrayList;

/*
 * Created by willw on 11/4/2017.
 */
public class ManageAccounts extends AppCompatActivity {
    private Button addAccounts;
    private Button backmenu;
    private TableLayout accountTable;
    private SQLiteDatabase writeDB;
    private SQLiteDatabase readDB;
    private Cursor cursor;
    private String lastUN;
    private String lastType;
    private ArrayList<Button> upgradbuttons = new ArrayList<>();
    private ArrayList<Button> removeButtons = new ArrayList<>();
    private ArrayList<TableRow> tableRows = new ArrayList<>();
    private ArrayList<String> username = new ArrayList<>();
    private ArrayList<String> types = new ArrayList<>();

    public static String userCurrentlyViewing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_manage_accounts);
        addAccounts = (Button) findViewById(R.id.addaccount);
        backmenu = (Button) findViewById(R.id.adminbackmenu);
        accountTable = (TableLayout) findViewById(R.id.accounttable);
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

            addAccounts.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent intent = new Intent(ManageAccounts.this, AddAccount.class);
                    startActivity(intent);
                }
            });
        backmenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ManageAccounts.this, MenuAdmin.class);
                startActivity(intent);
            }
        });
    }
    private void addRows(){
        cursor = writeDB.rawQuery("SELECT * FROM " + CreateDB.TABLE_NAME, null);
        cursor.moveToFirst();
        accountTable = (TableLayout) findViewById(R.id.accounttable);
        accountTable.removeAllViews();
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.border, null);
            do {
                TableRow tableRow = new TableRow(this);

                tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                tableRow.setPadding(0, 20, 0, 20);
                tableRow.setWeightSum(10);

                for (int j = 0; j < 2; j++) {
                    TextView textView = new TextView(this);

                   // textView.setBackground(drawable);
                    if (j == 0) {
                        textView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 3.9f));
                    } else if (j == 1) {
                        textView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 2.9f));

                    }
//                    AccountInfo.showMessage(cursor.getString(j),getApplicationContext());
                    textView.setText(cursor.getString(j));
                    textView.setTypeface(null, Typeface.ITALIC);


                    textView.setTextSize(25f);
                    textView.setPadding(10,0,0,0);
                    if (j == 0) {
                        lastUN = cursor.getString(j);
                    }
                    else if(j == 1){
                        lastType = cursor.getString(j);
                    }
                    tableRow.addView(textView);
                }

                Button upgradAcc = new Button(this);
                upgradAcc.setText("Change Type");
                upgradAcc.setTextSize(25f);
                upgradAcc.setPadding(0, 0, 20, 0);
               // upgradAcc.setBackground(drawable);
                if(!cursor.getString(1).equals("Administrator")) {
                    upgradbuttons.add(upgradAcc);
                }

                Button removeacc = new Button(this);
                removeacc.setText("Delete");
                removeacc.setTextSize(25f);
                removeacc.setPadding(0, 0, 20, 0);
               // removeacc.setBackground(drawable);
                if(!lastType.equals(AccountInfo.ADMIN_PREM)) {
                    removeButtons.add(removeacc);
                }
                upgradAcc.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1.4f));
                removeacc.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1.4f));
                tableRow.addView(upgradAcc);
                tableRow.addView(removeacc);
                if(!lastType.equals(AccountInfo.ADMIN_PREM)) {
                    tableRows.add(tableRow);
                }

                if(!lastType.equals(AccountInfo.ADMIN_PREM)) {
                    accountTable.addView(tableRow);
                }
            } while (cursor.moveToNext());
            cursor.close();
        setButtons();

    }
    private void setButtons(){
        cursor = readDB.rawQuery("SELECT * FROM " + CreateDB.TABLE_NAME, null);
        cursor.moveToFirst();
        final String[] accountType = {"Track Inspector", "Manager"};
        int i = 0;
        username.clear();
        types.clear();
        do{
            if(cursor.getString(1).equals(AccountInfo.ADMIN_PREM)){
               // cursor.moveToNext();
            }
            else {
                final int currentI = i;
                username.add(cursor.getString(0));
                types.add(cursor.getString(1));
                removeButtons.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(ManageAccounts.this);
                        builder.setMessage("Would you like to delete this account?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                        writeDB.delete(CreateDB.TABLE_NAME, "username=?", new String[]{username.get(currentI)});
                                        upgradbuttons.clear();
                                        removeButtons.clear();
                                        tableRows.clear();
                                        addRows();
                                        dialog.cancel();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                        dialog.cancel();
                                    }
                                });
                        final AlertDialog alert = builder.create();
                        alert.show();
                    }
                });
                upgradbuttons.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(ManageAccounts.this);
                        builder.setMessage("Would you like to change the premissions of this account?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
//                                    // Account picker
                                        final AlertDialog builder1 = new AlertDialog.Builder(ManageAccounts.this).setTitle("Pick new account type").setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, accountType), new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                String typeSelected = accountType[which];
                                                ContentValues values = new ContentValues();
                                                values.put(CreateDB.COLUMN_TYPE, typeSelected);
                                                writeDB.update(CreateDB.TABLE_NAME, values, "username=?", new String[]{username.get(currentI)});
                                                upgradbuttons.clear();
                                                removeButtons.clear();
                                                tableRows.clear();
                                                addRows();
                                                dialog.cancel();

                                            }
                                        }).create();
                                        builder1.show();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                        dialog.cancel();
                                    }
                                });
                        final AlertDialog alert = builder.create();
                        alert.show();
                    }
                });
                tableRows.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (types.get(currentI).equals("Manager")) {
                            upgradbuttons.clear();
                            removeButtons.clear();
                            tableRows.clear();
                            userCurrentlyViewing = username.get(currentI);
                            Intent intent = new Intent(ManageAccounts.this, MenuManagerAdmin.class);
                            startActivity(intent);
                        } else if (types.get(currentI).equals("Track Inspector")) {
                            upgradbuttons.clear();
                            removeButtons.clear();
                            tableRows.clear();
                            userCurrentlyViewing = username.get(currentI);
                            Intent intent = new Intent(ManageAccounts.this, MenuTIAdmin.class);
                            startActivity(intent);
                        }
                    }
                });
               i++;
            }
        }while(cursor.moveToNext() && cursor !=null);
    }
}

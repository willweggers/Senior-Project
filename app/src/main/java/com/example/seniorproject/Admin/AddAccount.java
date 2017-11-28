package com.example.seniorproject.Admin;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.seniorproject.DB.AccountFields;
import com.example.seniorproject.DB.LocalDBHelper;
import com.example.seniorproject.R;

import java.util.ArrayList;

/**
 * Created by willw on 11/5/2017.
 */

public class AddAccount extends AppCompatActivity {
    private Button submitButton;
    private EditText enterFirstName;
    private EditText enterLastName;
    private EditText enterUserName;
    private String firstName;
    private String lastName;
    private String userName;
    private String passWord;
    private String initals;
    private Spinner dropdown;
    private String type;
    private LocalDBHelper writeAccDB;
    private Handler handler = new Handler();
    private AccountFields accountFields;
    private int numInitials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_account_form);
        submitButton = (Button) findViewById(R.id.addsubmit);
        enterUserName = (EditText) findViewById(R.id.addusername);
        enterFirstName = (EditText) findViewById(R.id.addfirstname);
        enterLastName = (EditText) findViewById(R.id.addlastname);
        accountFields = new AccountFields();
        dropdown = (Spinner)findViewById(R.id.addtype);
        ArrayList<String> listOfManagersNames = new ArrayList<>();
        String[] items = new String[]{AccountInfo.TI_PREM, AccountInfo.MANAGER_PREM, AccountInfo.ADMIN_PREM};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        writeAccDB = LocalDBHelper.getInstance(this);

        final ArrayList<AccountFields> managerAccounts =  writeAccDB.getAllAccountByType(AccountInfo.MANAGER_PREM);
        for(int i = 0; i < managerAccounts.size();i++) {
            listOfManagersNames.add(managerAccounts.get(i).firstName.concat(" ").concat(managerAccounts.get(i).lastName));
        }
        final CharSequence[] actListOfManagerNames = listOfManagersNames.toArray(new CharSequence[listOfManagersNames.size()]);
        setTitle("Add New Account Below");
//        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String itemSelected = parent.getItemAtPosition(position).toString();
//                if(itemSelected.equals(AccountInfo.TI_PREM)){
//                    final AlertDialog.Builder builder = new AlertDialog.Builder(AddAccount.this);
//                    builder.setTitle("Select the manager that can view this inspectors inspections below: ");
//                    builder.setItems(actListOfManagerNames, new DialogInterface.OnClickListener(){
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            accountFields.tiManager = managerAccounts.get(which).userName;
//                        }
//                    });
//
//
//                    builder.show();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setText();
                if(checkUsername()){
                    setInitials();
                    passWord = AccountInfo.md5("");
                    accountFields.initials = initals;
                    accountFields.firstName = firstName;
                    accountFields.lastName = lastName;
                    accountFields.userName = userName;
                    accountFields.passWord = passWord;
                    accountFields.accountType = type;
                    writeAccDB.addAccount(accountFields);
                    enterUserName.setText(null);
                    enterFirstName.setText(null);
                    enterLastName.setText(null);
                    AccountInfo.showMessage("Account successfully created and password is nothing.",getApplicationContext());
                    handler.postDelayed(mLaunchTask,1000);
                }
                else{
                    AccountInfo.showMessage("Username is taken.", getApplicationContext());
                }

            }
        });

    }
    private Runnable mLaunchTask = new Runnable() {
        public void run() {
            Intent intent = new Intent(AddAccount.this, MenuAdmin.class);
            startActivity(intent);
        }
    };
    private boolean checkUsername(){
        if(writeAccDB.getAccountByUser(userName) == null){
            return true;
        }
        return false;

    }

    private void setText(){
        userName = enterUserName.getText().toString();
        firstName = enterFirstName.getText().toString();
        lastName = enterLastName.getText().toString();

        type = dropdown.getSelectedItem().toString();
        if(type.equals("Administrator")){
            type = AccountInfo.ADMIN_PREM;
        }else if(type.equals("Manager")){
            type = AccountInfo.MANAGER_PREM;
        }else{
            type = AccountInfo.TI_PREM;
        }


    }
    private void setInitials(){
        initals = firstName.substring(0,1).concat(lastName.substring(0,1));
        if(writeAccDB.getAccountByInitials(initals)!=null){
            initals = initals.concat(Integer.toString(numInitials++));
            setInitials();
        }

    }
}

package com.example.seniorproject;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.seniorproject.DB.AccountFields;
import com.example.seniorproject.DB.LocalDBHelper;

/**
 * Created by willw on 11/25/2017.
 */

public class NullPassDialog {
    public void createDiaglogBox(final Context context, final String theUsername2, LocalDBHelper localDBHelper){
        final LocalDBHelper localDB = localDBHelper;
        final String theUsername = theUsername2;
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("New Account set your password below: ");
        final LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        final TextView inputText = new TextView(context);
        inputText.setText("Password:");
        final EditText input = new EditText(context);
        input.setHint("Password");
        final TextView inputText2 = new TextView(context);
        inputText2.setText("Confirm Password:");
        final EditText confirmInput = new EditText(context);
        confirmInput.setHint("Confirm Password");
        linearLayout.addView(inputText);
        linearLayout.addView(input);
        linearLayout.addView(inputText2);
        linearLayout.addView(confirmInput);

        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        confirmInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setCancelable(false);
        builder.setView(linearLayout);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String input1 = input.getText().toString().trim();
                String input2 = confirmInput.getText().toString().trim();
                if(input1.equals(input2)){
                    AccountFields account = new AccountFields();
                    try {
                        account = localDB.getAccountByUser(theUsername);
                    }catch (IllegalArgumentException e){

                    }
                    account.passWord = AccountInfo.md5(input1);
                    localDB.updateAccountLine(account);
                    AccountInfo.showMessage("Password set.", context);
                    dialog.dismiss();
                }
                else{
                    createDiaglogBox(context,theUsername2,localDB);
                    AccountInfo.showMessage("Passwords didnt match.",context);
                }


            }
        });

        builder.show();
    }
}

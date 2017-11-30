package com.example.seniorproject;

import android.accounts.Account;

import com.example.seniorproject.DB.AccountFields;
import com.example.seniorproject.DB.LocalDBHelper;

/**
 * Created by willw on 11/26/2017.
 */

public class FindLocalDBInfo {
    public static String findFirstNameLastName(LocalDBHelper localDBHelper, String userName){
        String theName;
        AccountFields accountFields= localDBHelper.getAccountByUser(userName);
        theName = accountFields.firstName.concat(" ").concat(accountFields.lastName);
        return theName;
    }
    public static String findType(LocalDBHelper localDBHelper,String username){
        String type;
        AccountFields accountFields= localDBHelper.getAccountByUser(username);
        type = accountFields.accountType;
        return type;
    }
}

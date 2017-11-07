package com.example.seniorproject;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Context;
import android.widget.Toast;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by willw on 11/3/2017.
 */

public class AccountInfo{
    //overarcing account type for this apps accounts
//    public static final String ACCOUNT_TYPE = "com.example.seniorproject.Accounts";
    //diff premissons
    public static final String ADMIN_PREM = "Administrator";
    public static final String MANAGER_PREM = "Manager";
    public static final String TI_PREM = "Track Inspector";
    //Admin info
    public static final String adminUN = "Admin";
    public static final String adminPass = "adminpass";
    //hashing string function
    //https://stackoverflow.com/questions/3934331/how-to-hash-a-string-in-android
    public static String md5(String s)
    {
        MessageDigest digest;
        try
        {
            digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes(Charset.forName("US-ASCII")),0,s.length());
            byte[] magnitude = digest.digest();
            BigInteger bi = new BigInteger(1, magnitude);
            String hash = String.format("%0" + (magnitude.length << 1) + "x", bi);
            return hash;
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return "";
    }
    //so you dont have to use toast everytime
    public static void showMessage(String msg, Context context){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}

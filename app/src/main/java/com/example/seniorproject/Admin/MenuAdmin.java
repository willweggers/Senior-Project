package com.example.seniorproject.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.seniorproject.AccountInfo;
import com.example.seniorproject.DB.LocalDBHelper;
import com.example.seniorproject.ListInspectionsCurrent;

import com.example.seniorproject.ListOfInspections;
import com.example.seniorproject.NullPassDialog;
import com.example.seniorproject.R;
import com.example.seniorproject.TrackInspector.MenuTrackInspectorFrag;
import com.example.seniorproject.MainActivityLogin;
import com.example.seniorproject.R;
import com.example.seniorproject.TrackInspector.FormatReport;
import com.example.seniorproject.TrackInspector.HeaderData;
import com.example.seniorproject.TrackInspector.MenuTI;

/**
 * Created by willw on 9/12/2017.
 */

public class MenuAdmin extends AppCompatActivity implements SettingsAdmin.OnFragmentInteractionListener,
        ManageAccounts.OnFragmentInteractionListener,
        ModiyMasterFiles.OnFragmentInteractionListener{

private int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 99;
private LocalDBHelper localDB;




@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_admin);
        TabLayout bottomLayout = (TabLayout) findViewById(R.id.atabmenu);
        bottomLayout.addTab(bottomLayout.newTab().setText("Manage Accounts"));
        bottomLayout.addTab(bottomLayout.newTab().setText("Modify Master Files"));
        bottomLayout.addTab(bottomLayout.newTab().setText("Settings"));
        bottomLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        bottomLayout.setTabMode(TabLayout.MODE_FIXED);

final ViewPager wholeViewPager = (ViewPager) findViewById(R.id.wholemenuapager);
final PagerAdapterA pageAdapter = new PagerAdapterA(getSupportFragmentManager(), bottomLayout.getTabCount());
        wholeViewPager.setAdapter(pageAdapter);
        wholeViewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(bottomLayout));

        bottomLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
@Override
public void onTabSelected(TabLayout.Tab tab) {
        wholeViewPager.setCurrentItem(tab.getPosition());
        }

@Override
public void onTabUnselected(TabLayout.Tab tab) {

        }

@Override
public void onTabReselected(TabLayout.Tab tab) {

        }
        });
        localDB = LocalDBHelper.getInstance(this);


        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
        MY_PERMISSIONS_ACCESS_FINE_LOCATION);
        checkIfPassISDefault();



        }

private void checkIfPassISDefault() {
final String theUsername = LocalDBHelper.getDataInSharedPreference(this, "username");
        if (localDB.getAccountByUser(theUsername).passWord.equals(AccountInfo.md5(""))) {
        NullPassDialog nullPassDialog = new NullPassDialog();
        nullPassDialog.createDiaglogBox(this, theUsername, localDB);
        }
        }


@Override
public void onFragmentInteraction(Uri uri) {

        }


        }
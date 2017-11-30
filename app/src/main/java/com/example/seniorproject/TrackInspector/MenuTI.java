package com.example.seniorproject.TrackInspector;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.seniorproject.AccountInfo;
import com.example.seniorproject.DB.LocalDBHelper;
import com.example.seniorproject.FindLocalDBInfo;
import com.example.seniorproject.ListInspectionsCurrent;

import com.example.seniorproject.ListOfInspections;
import com.example.seniorproject.NullPassDialog;
import com.example.seniorproject.R;


/**
 * Created by willw on 10/5/2017.
 * menu page for TI. just used to declare intents between this page and whatever page it is suppose to go to.
 * currently startinspection is going to trackinspector page which is just the old demo trackinspection page.
 */

public class MenuTI extends AppCompatActivity implements MenuTrackInspectorFrag.OnFragmentInteractionListener,
        SettingsTIFrag.OnFragmentInteractionListener,
        //ListInspectionsCurrent.OnFragmentInteractionListener,
        ListOfInspections.OnFragmentInteractionListener{

    private int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 99;
    private LocalDBHelper localDB;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_track_inspector);
        TabLayout bottomLayout = (TabLayout) findViewById(R.id.titabmenu);
        bottomLayout.addTab(bottomLayout.newTab().setText("Inspections"));
        bottomLayout.addTab(bottomLayout.newTab().setText("Settings"));
        bottomLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        bottomLayout.setTabMode(TabLayout.MODE_FIXED);

        final ViewPager wholeViewPager = (ViewPager) findViewById(R.id.wholemenutipager);
        final PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(), bottomLayout.getTabCount());
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


        ActivityCompat.requestPermissions(MenuTI.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
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
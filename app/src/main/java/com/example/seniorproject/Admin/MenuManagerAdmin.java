package com.example.seniorproject.Admin;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.seniorproject.Manager.ListTrackInspectors;
import com.example.seniorproject.Manager.MenuManager;
import com.example.seniorproject.R;
import com.example.seniorproject.TrackInspector.HeaderData;

/**
 * Created by willw on 11/6/2017.
 */

public class MenuManagerAdmin extends AppCompatActivity {
    private Button startInspection;
    private Button viewyourInspection;
    private Button viewTIInspections;
    private Button editInspection;
    private Button backadminmenu;

    private int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_manager_admin);
        startInspection = (Button) findViewById(R.id.startInspectionmanager);
        viewyourInspection = (Button) findViewById(R.id.viewInspectionmanagermanager);
        viewTIInspections =  (Button) findViewById(R.id.viewInspectionmanager);
        editInspection = (Button) findViewById(R.id.editInspectionmanager);
        backadminmenu = (Button) findViewById(R.id.backadminmenu2) ;

        ActivityCompat.requestPermissions(MenuManagerAdmin.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_ACCESS_FINE_LOCATION);
        setButtons();

    }

    private void setButtons(){

        startInspection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuManagerAdmin.this, HeaderData.class);
                startActivity(intent);
            }
        });
        backadminmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuManagerAdmin.this, MenuAdmin.class);
                startActivity(intent);
            }
        });

        viewTIInspections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListTrackInspectors.userUsing = "Admin";
                Intent intent = new Intent(MenuManagerAdmin.this, ListTrackInspectors.class);
                startActivity(intent);
            }
        });
        viewyourInspection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        editInspection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}

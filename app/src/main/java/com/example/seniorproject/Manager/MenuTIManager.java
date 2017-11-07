package com.example.seniorproject.Manager;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.seniorproject.Admin.MenuAdmin;
import com.example.seniorproject.Admin.MenuTIAdmin;
import com.example.seniorproject.R;
import com.example.seniorproject.TrackInspector.HeaderData;

/**
 * Created by willw on 11/6/2017.
 */

public class MenuTIManager extends AppCompatActivity {
    private Button startInspection;
    private Button viewInspection;
    private Button editInspection;
    private Button back;
    private int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 99;
    public static String currentUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_ti_admin);
        startInspection = (Button) findViewById(R.id.startInspectionadmin);
        viewInspection = (Button) findViewById(R.id.viewInspectionadmin);
        editInspection = (Button) findViewById(R.id.editInspectionadmin);
        back = (Button) findViewById(R.id.backadminmenu5) ;
        ActivityCompat.requestPermissions(MenuTIManager.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_ACCESS_FINE_LOCATION);
        setButtons();

    }

    private void setButtons(){

        startInspection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuTIManager.this, HeaderData.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuTIManager.this, MenuManager.class);
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
    }

}
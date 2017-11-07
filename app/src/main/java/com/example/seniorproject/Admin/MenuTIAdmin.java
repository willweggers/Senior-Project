package com.example.seniorproject.Admin;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.seniorproject.R;
import com.example.seniorproject.TrackInspector.HeaderData;
import com.example.seniorproject.TrackInspector.MenuTI;

/**
 * Created by willw on 11/6/2017.
 */

public class MenuTIAdmin extends AppCompatActivity {
    private Button startInspection;
    private Button viewInspection;
    private Button editInspection;
    private Button back;
    private int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_ti_admin);
        startInspection = (Button) findViewById(R.id.startInspectionadmin);
        viewInspection = (Button) findViewById(R.id.viewInspectionadmin);
        editInspection = (Button) findViewById(R.id.editInspectionadmin);
        back = (Button) findViewById(R.id.backadminmenu4) ;
        ActivityCompat.requestPermissions(MenuTIAdmin.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_ACCESS_FINE_LOCATION);
        setButtons();

    }

    private void setButtons(){

        startInspection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuTIAdmin.this, HeaderData.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuTIAdmin.this, MenuAdmin.class);
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
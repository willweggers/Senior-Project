package com.example.seniorproject.TrackInspector;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.seniorproject.R;


/**
 * Created by willw on 10/5/2017.
 * menu page for TI. just used to declare intents between this page and whatever page it is suppose to go to.
 * currently startinspection is going to trackinspector page which is just the old demo trackinspection page.
 */

public class MenuTI extends AppCompatActivity{
    private Button startInspection;
    private Button viewInspection;
    private Button editInspection;
    private int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_track_inspector);
        startInspection = (Button) findViewById(R.id.startInspection);
        viewInspection = (Button) findViewById(R.id.viewInspection);
        editInspection = (Button) findViewById(R.id.editInspection);
        ActivityCompat.requestPermissions(MenuTI.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_ACCESS_FINE_LOCATION);
        setButtons();
        setTitle("Menu");

    }

    private void setButtons(){

        startInspection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuTI.this, HeaderData.class);
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

package com.example.seniorproject.Admin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.seniorproject.BaseDriveMapActivitys;
import com.example.seniorproject.R;

/**
 * Created by willw on 9/12/2017.
 */

public class Admin extends BaseDriveMapActivitys {
    private Button changeDriveAcc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_admin);
        changeDriveAcc = (Button) findViewById(R.id.changedriveaccount);
        setButtons();
    }
    public void setButtons(){
        changeDriveAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDriveAccount();
            }
        });
    }


}
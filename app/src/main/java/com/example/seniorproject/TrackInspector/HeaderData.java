package com.example.seniorproject.TrackInspector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.seniorproject.R;

/**
 * Created by willw on 10/6/2017.
 */

public class HeaderData extends AppCompatActivity {
    private Button goToMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.header_data_track_inspector);
        goToMap = (Button) findViewById(R.id.gotomap);
        setButtons();
    }
    private void setButtons(){
        goToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HeaderData.this, MapTI.class);
                startActivity(intent);
            }
        });
    }
}

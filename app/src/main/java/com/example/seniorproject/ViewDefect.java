package com.example.seniorproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.seniorproject.DB.AccountFields;
import com.example.seniorproject.DB.Defect;
import com.example.seniorproject.DB.Inspection;
import com.example.seniorproject.DB.LocalDBHelper;
import com.example.seniorproject.DB.MasterFileObjects.MobilizationFile;
import com.example.seniorproject.TrackInspector.HeaderData;
import com.example.seniorproject.TrackInspector.MapTI;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by willw on 11/30/2017.
 */

public class ViewDefect extends AppCompatActivity {
    private Button goToMap;

    private TextView lineitem;
    private TextView tracknum;
    private TextView loc;
    private TextView descp;
    private ImageView pic;
    private TextView labor;
    private TextView cat;
    private TextView code;
    private TextView codedescp;
    private TextView quant;
    private TextView unit;
    private TextView priority;
    private TextView price;
    private LocalDBHelper localDBHelper;
    private String linenumber;
    private String inspectionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_defects);
        linenumber = LocalDBHelper.getDataInSharedPreference(getApplicationContext(),"lineitem");
        inspectionID = LocalDBHelper.getDataInSharedPreference(getApplicationContext(),"inspectionID");
        goToMap = (Button) findViewById(R.id.gotomapdefect);
        lineitem = (TextView) findViewById(R.id.lineitemnumberview);
        tracknum= (TextView) findViewById(R.id.tracknumberview);
        loc= (TextView) findViewById(R.id.locationview);
        descp= (TextView) findViewById(R.id.descpviewdef);
        pic= (ImageView) findViewById(R.id.imageviewdef);
        labor= (TextView) findViewById(R.id.laborcodeview);
        cat= (TextView) findViewById(R.id.catview);
        code= (TextView) findViewById(R.id.catcodeview);
        codedescp= (TextView) findViewById(R.id.catcodedescpview);
        quant= (TextView) findViewById(R.id.quanityview);
        unit= (TextView) findViewById(R.id.unitview);
        priority= (TextView) findViewById(R.id.priorityview);
        price= (TextView) findViewById(R.id.priceview);
        localDBHelper = LocalDBHelper.getInstance(this);
        setTexts();
        setButtons();
        setTitle(linenumber + " - " + inspectionID);
    }
    private void setTexts(){
        ArrayList<Defect> alldefects = localDBHelper.getAllDefects();
        for(int i = 0; i < alldefects.size();i++){
            if(alldefects.get(i).inspection_id_num.equals(inspectionID)){
                if(alldefects.get(i).lineItem.equals(linenumber)){
                    lineitem.setText(linenumber);
                    tracknum.setText(alldefects.get(i).trackNumber);
                    loc.setText(alldefects.get(i).location);
                    descp.setText(alldefects.get(i).description);

                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(alldefects.get(i).picture));
                        pic.setImageBitmap(bitmap);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }catch (OutOfMemoryError e){
                        e.getMessage();
                    }catch (NullPointerException e){
                        e.getMessage();
                    }

                    labor.setText(alldefects.get(i).labor);
                    cat.setText(alldefects.get(i).category);
                    code.setText(alldefects.get(i).code);
                    codedescp.setText(alldefects.get(i).codeDescription);
                    quant.setText(String.valueOf(alldefects.get(i).quantity));
                    unit.setText(alldefects.get(i).unit);
                    priority.setText(String.valueOf(alldefects.get(i).priority));
                    price.setText(String.valueOf(alldefects.get(i).price));
                }
            }

        }
    }

    private void setButtons(){
        goToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewDefect.this, ViewInspection.class);
                startActivity(intent);

            }
        });
    }
    public Bitmap getBitmap(String path) {
        Bitmap bitmap=null;
        try {

            File f= new File(path);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;

            bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

}


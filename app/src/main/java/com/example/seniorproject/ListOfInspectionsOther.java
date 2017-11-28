package com.example.seniorproject;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.Space;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.seniorproject.DB.LocalDBHelper;

import java.util.ArrayList;

/**
 * Created by willw on 11/28/2017.
 */

public class ListOfInspectionsOther extends AppCompatActivity {
    private TableLayout inspectionList;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_inspections_other);
        inspectionList = (TableLayout) findViewById(R.id.listofotherinspec);
        title = (TextView) findViewById(R.id.titleUserViewing);
        String userviewing = LocalDBHelper.getDataInSharedPreference(this,"userviewing");
        LocalDBHelper localDBHelper = LocalDBHelper.getInstance(this);
        String userviewingName = FindLocalDBInfo.findFirstNameLastName(localDBHelper,userviewing);
        title.setText(userviewingName.concat(" Inspections"));
        setTableRows();
    }
    private void setTableRows(){

        inspectionList.removeAllViews();
        LocalDBHelper localDBHelper = LocalDBHelper.getInstance(this);
        ArrayList<String> allInspectionsidNums=localDBHelper.getAllInspectionsIDNum();
        ArrayList<Integer> allInspectionsDates = localDBHelper.getAllInspectionsDate();
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.tablerowborder, null);
        final Drawable clickDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.tablerowborderclick, null);
        int numRows = allInspectionsidNums.size()*2;
        for(int i = 0; i< numRows;i++){
            //if even add space
            if(i==0 || i%2 == 0){
                Space space = new Space(this);
                space.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, 30));
                inspectionList.addView(space);
                continue;
            }
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            tableRow.setWeightSum(3);
            tableRow.setBackground(drawable);

            for (int j = 0; j < 2; j++) {
                TextView textView = new TextView(this);
                if (j == 0) {
                    textView.setText(allInspectionsidNums.get(i-1));
                    textView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f));

                } else if (j == 1) {
                    textView.setText(allInspectionsDates.get(i-1));
                    textView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f));
                }
                textView.setTextSize(20f);
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setPadding(10,0,0,0);
                tableRow.addView(textView);
            }
            Space space = new Space(this);
            space.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.5f));
            tableRow.addView(space);
            final String inspectionID = allInspectionsidNums.get(i-1);
            final TableRow currTableRow = tableRow;
            tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currTableRow.setBackground(clickDrawable);
                    LocalDBHelper tempDB = LocalDBHelper.getInstance(getApplicationContext());
                    Intent intent = new Intent(ListOfInspectionsOther.this, ViewInspection.class);
                    getIntent().putExtra("inspectionID",inspectionID);
                    startActivity(intent);
                }
            });
            inspectionList.addView(tableRow);
        }
    }
}
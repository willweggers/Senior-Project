package com.example.seniorproject;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.Space;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.seniorproject.DB.Inspection;
import com.example.seniorproject.DB.LocalDBHelper;

import java.util.ArrayList;

/**
 * Created by willw on 11/28/2017.
 */

public class ListOfInspectionsOther extends AppCompatActivity {
    private TableLayout inspectionList;
    private TextView title;
    private String userViewing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_inspections_other);
        inspectionList = (TableLayout) findViewById(R.id.listofotherinspec);
        title = (TextView) findViewById(R.id.titleUserViewing);
        userViewing = LocalDBHelper.getDataInSharedPreference(this,"userviewing");
        LocalDBHelper localDBHelper = LocalDBHelper.getInstance(this);
        String userviewingName = FindLocalDBInfo.findFirstNameLastName(localDBHelper,userViewing);
        title.setText(userviewingName.concat(" Inspections"));
        setTableRows();
    }
    private void setTableRows(){

        inspectionList.removeAllViews();
        LocalDBHelper localDBHelper = LocalDBHelper.getInstance(this);
        ArrayList<Inspection> allInspections = localDBHelper.getAllInspections();
        ArrayList<String> allInspectionIDNum = new ArrayList<>();
        ArrayList<String> allInspectionIDDate = new ArrayList<>();

        for(int i = 0; i < allInspections.size();i++){
            if(allInspections.get(i).inspectorID.equals(userViewing)) {
                allInspectionIDNum.add(allInspections.get(i).inspectionNum);
                allInspectionIDDate.add(AccountInfo.convertDate(allInspections.get(i).inspectionDate));
            }

        }
        final Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.tablerowborder, null);
        final Drawable clickDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.tablerowborderclick, null);
        int numRows = allInspections.size()*2;
        int count = 0;
        for(int i = 0; i< numRows;i++){
            //if even add space
            if(i==0 || i%2 == 0){
                Space space = new Space(this);
                space.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, 30));
                inspectionList.addView(space);
                count++;
                continue;
            }
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            tableRow.setWeightSum(3);
            tableRow.setBackground(drawable);

            for (int j = 0; j < 2; j++) {
                TextView textView = new TextView(this);
                if (j == 0) {
                    textView.setText(allInspectionIDNum.get(i-count));
                    textView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f));

                } else if (j == 1) {
                    textView.setText(allInspectionIDDate.get(i-count));
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
            final String inspectionID = allInspectionIDNum.get(i-count);
            final TableRow currTableRow = tableRow;
            tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currTableRow.setBackground(clickDrawable);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            currTableRow.setBackground(drawable);
                        }
                    }, 1000);
                    LocalDBHelper tempDB = LocalDBHelper.getInstance(getApplicationContext());
                    Intent intent = new Intent(ListOfInspectionsOther.this, ViewHeader.class);
                    LocalDBHelper.storeDataInSharedPreference(getApplicationContext(),"inspectionID",inspectionID);
                    startActivity(intent);
                }
            });
            inspectionList.addView(tableRow);
        }
    }
}
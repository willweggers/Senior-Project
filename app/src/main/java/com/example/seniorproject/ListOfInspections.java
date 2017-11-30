package com.example.seniorproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.Space;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.seniorproject.DB.Inspection;
import com.example.seniorproject.DB.LocalDBHelper;
import com.example.seniorproject.TrackInspector.HeaderData;

import java.util.ArrayList;

/**
 * Created by willw on 11/13/2017.
 */

public class ListOfInspections extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ListOfInspections() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab1.
     */
    // TODO: Rename and change types and number of parameters
    public static ListOfInspections newInstance(String param1, String param2) {
        ListOfInspections fragment = new ListOfInspections();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_of_inspections, container, false);
        TableLayout tableLayout = (TableLayout) view.findViewById(R.id.currentinspectionlistold);
        LocalDBHelper localDBHelper = LocalDBHelper.getInstance(getContext());
        ArrayList<Inspection> allInspections = new ArrayList<>();
        String userid = LocalDBHelper.getDataInSharedPreference(getContext(),"username");
        allInspections = localDBHelper.getAllInspections();
        ArrayList<String> allInspectionIDNum = new ArrayList<>();
        ArrayList<String> allInspectionIDDate = new ArrayList<>();
        int numrows = 0;
        for(int i = 0; i < allInspections.size();i++){
            if(allInspections.get(i).inspectorID.equals(userid)) {
                numrows++;
                allInspectionIDNum.add(allInspections.get(i).inspectionNum);
                allInspectionIDDate.add(AccountInfo.convertDate(allInspections.get(i).inspectionDate));
            }

        }
        int numRows = numrows*2;
//        AccountInfo.showMessage(Integer.toString(localDBHelper.getAmountInspections()),getContext());
        setTableRows(tableLayout, numRows, allInspectionIDNum, allInspectionIDDate);

        return view;
    }
    private void setTableRows(TableLayout tableLayout, int numRows, ArrayList<String> inspectionIDNumbers, ArrayList<String> InspectionDates){
        tableLayout.removeAllViews();
        final Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.tablerowborder, null);
        final Drawable clickDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.tablerowborderclick, null);
        int count = 0;
        for(int i = 0; i< numRows;i++){
            //if even add space
            if(i==0 || i%2 == 0){
                Space space = new Space(getContext());
                space.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, 30));
                tableLayout.addView(space);
                count++;
                continue;
            }
            TableRow tableRow = new TableRow(getContext());
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            tableRow.setWeightSum(3);
            tableRow.setBackground(drawable);

            for (int j = 0; j < 2; j++) {
                TextView textView = new TextView(getContext());
                if (j == 0) {
                    textView.setText(inspectionIDNumbers.get(i-count));
                    textView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f));

                } else if (j == 1) {
                    textView.setText(InspectionDates.get(i-count));
                    textView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f));
                }
                textView.setTextSize(20f);
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setPadding(10,0,0,0);
                tableRow.addView(textView);
            }
            Space space = new Space(getContext());
            space.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.5f));
            tableRow.addView(space);
            Button deleteButton = new Button(getContext());
            deleteButton.setText("DELETE");
            deleteButton.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.5f));
            final String currinspectionIDNum = inspectionIDNumbers.get(i-count);
            final TableLayout tableLayout1 = tableLayout;
            final int numRows1 = numRows;
            final ArrayList<String> inspectionIDNumbers1 = inspectionIDNumbers;
            final ArrayList<String> inspectionIDDates1 = InspectionDates;
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LocalDBHelper tempDB = LocalDBHelper.getInstance(getContext());
                    tempDB.deleteInspection(currinspectionIDNum);
                    setTableRows(tableLayout1, numRows1,inspectionIDNumbers1, inspectionIDDates1);
                }
            });
           // tableRow.addView(deleteButton);
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
                    LocalDBHelper tempDB = LocalDBHelper.getInstance(getContext());
                    Intent intent = new Intent(getActivity(), ViewHeader.class);
                    LocalDBHelper.storeDataInSharedPreference(getContext(),"inspectionID", currinspectionIDNum);
                    startActivity(intent);
                }
            });
            tableLayout.addView(tableRow);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
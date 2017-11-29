package com.example.seniorproject.MasterFiles;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.Space;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.seniorproject.AccountInfo;
import com.example.seniorproject.Admin.EditStateRow;
import com.example.seniorproject.Admin.SettingsAdmin;
import com.example.seniorproject.DB.AccountFields;
import com.example.seniorproject.DB.LocalDBHelper;
import com.example.seniorproject.DB.MasterFileObjects.StateFile;
import com.example.seniorproject.ListOfInspectionsOther;
import com.example.seniorproject.MainActivityLogin;
import com.example.seniorproject.NullPassDialog;
import com.example.seniorproject.R;

import java.util.ArrayList;

/**
 * Created by willw on 11/28/2017.
 */

public class StateMasterFile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;

    public StateMasterFile() {
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
    public static StateMasterFile newInstance(String param1, String param2) {
        StateMasterFile fragment = new StateMasterFile();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.state_master_file, container, false);
        TableLayout tableLayout = (TableLayout) view.findViewById(R.id.statetable);
        setTableRows(tableLayout);
        return view;
    }
    private void setTableRows(TableLayout tableLayout) {
        LocalDBHelper localDBHelper = LocalDBHelper.getInstance(getActivity());
        ArrayList<StateFile> stateFiles = localDBHelper.getAllStateFiles();
        tableLayout.removeAllViews();
        final Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.tablerowborder, null);
        final Drawable clickDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.tablerowborderclick, null);

        for (int i = 0; i < stateFiles.size(); i++) {
            TableRow tableRow = new TableRow(getContext());
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            tableRow.setWeightSum(5);
            tableRow.setBackground(drawable);

            for (int j = 0; j < 5; j++) {
                TextView textView = new TextView(getContext());
                if (j == 0) {
                    textView.setText(stateFiles.get(i).theID);
                    textView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f));
                } else if (j == 1) {
                    textView.setText(stateFiles.get(i).thedescription);
                    textView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f));
                } else if (j == 2) {
                    textView.setText(stateFiles.get(i).theLaborTax);
                    textView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f));
                }else if (j == 3) {
                    textView.setText(String.valueOf(stateFiles.get(i).theOtherTaxPerc));
                    textView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f));
                }else if (j == 4) {
                    textView.setText(stateFiles.get(i).theOtherTaxOn);
                    textView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f));
                }
                textView.setTextSize(20f);
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setPadding(10, 0, 0, 0);
                tableRow.addView(textView);
            }
            final TableRow currTableRow = tableRow;
            final String id = stateFiles.get(i).theID;
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
                        Intent intent = new Intent(getActivity(), EditStateRow.class);
                        LocalDBHelper.storeDataInSharedPreference(getContext(), "rowviewing",id);
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

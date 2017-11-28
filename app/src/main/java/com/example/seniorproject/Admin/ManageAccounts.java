package com.example.seniorproject.Admin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.Space;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import com.example.seniorproject.DB.AccountFields;
import com.example.seniorproject.DB.Inspection;
import com.example.seniorproject.DB.LocalDBHelper;
import com.example.seniorproject.ListOfInspectionsOther;
import com.example.seniorproject.Manager.ListTrackInspectors;
import com.example.seniorproject.R;

import java.util.ArrayList;

/*
 * Created by willw on 11/4/2017.
 */
public class ManageAccounts extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    private OnFragmentInteractionListener mListener;

    public ManageAccounts() {
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
    public static ManageAccounts newInstance(String param1, String param2) {
        ManageAccounts fragment = new ManageAccounts();
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
        View view = inflater.inflate(R.layout.admin_manage_accounts, container, false);
        Button addAccount = (Button) view.findViewById(R.id.addaccounta);
        addAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddAccount.class);
                startActivity(intent);
            }
        });
        TableLayout tableLayout = (TableLayout) view.findViewById(R.id.listofaccounts);
        LocalDBHelper localDBHelper = LocalDBHelper.getInstance(getContext());
        ArrayList<String> allAccountUserNames = new ArrayList<>();
        ArrayList<String> allAccountName = new ArrayList<>();
        ArrayList<String> allAccountTypes = new ArrayList<>();
        ArrayList<AccountFields> allAcc = localDBHelper.getAllAccounts();
        for (int i = 0; i < allAcc.size(); i++){
            allAccountUserNames.add(allAcc.get(i).userName);
            allAccountName.add(allAcc.get(i).firstName.concat(" ").concat(allAcc.get(i).lastName));
            allAccountTypes.add(allAcc.get(i).accountType);
        }
        int numRows = allAcc.size()*2;
        setTableRows(tableLayout, numRows, allAccountUserNames, allAccountName, allAccountTypes);

        return view;
    }
    private void setTableRows(final TableLayout tableLayout, int numRowsReal, final ArrayList<String> allids, final  ArrayList<String> allNames, final ArrayList<String> alltypes) {
        final String[] accountTypes = new String[]{AccountInfo.TI_PREM, AccountInfo.MANAGER_PREM, AccountInfo.ADMIN_PREM};
        LocalDBHelper localDBHelper = LocalDBHelper.getInstance(getActivity());
        ArrayList<String> allAccountUserNames = new ArrayList<>();
        ArrayList<String> allAccountName = new ArrayList<>();
        ArrayList<String> allAccountTypes = new ArrayList<>();
        ArrayList<AccountFields> allAcc = localDBHelper.getAllAccounts();
        for (int i = 0; i < allAcc.size(); i++){
            allAccountUserNames.add(allAcc.get(i).userName);
            allAccountName.add(allAcc.get(i).firstName.concat(" ").concat(allAcc.get(i).lastName));
            allAccountTypes.add(allAcc.get(i).accountType);
        }
        allids.clear();
        allNames.clear();
        alltypes.clear();
        for(int i = 0; i < allAcc.size();i++){
            allids.add(allAccountUserNames.get(i));
            allNames.add(allAccountName.get(i));
            alltypes.add(allAccountTypes.get(i));
        }

        numRowsReal = allAcc.size()*2;
        tableLayout.removeAllViews();
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.tablerowborder, null);
        final Drawable clickDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.tablerowborderclick, null);
        int count = 0;
        for (int i = 0; i < numRowsReal; i++) {
            //if even add space
            if (i == 0 || i % 2 == 0) {
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

            for (int j = 0; j < 3; j++) {
                TextView textView = new TextView(getContext());
                if (j == 0) {
                    textView.setText(allNames.get(i - count));
                    textView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f));

                } else if (j == 1) {
                    textView.setText(allids.get(i - count));
                    textView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.5f));
                } else if (j == 2) {
                    textView.setText(alltypes.get(i - count));
                    textView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.5f));



                } textView.setTextSize(20f);
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setPadding(10, 0, 0, 0);
                tableRow.addView(textView);
            }
                final String currUsername = allids.get(i - count);
                final int numRows = numRowsReal;
                Button setPrem = new Button(getContext());
                setPrem.setText("Set Type");
                setPrem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Would you like to change the premissions of this account?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                        // Account picker
                                        final AlertDialog builder1 = new AlertDialog.Builder(getContext()).setTitle("Pick new account type").setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, accountTypes), new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                LocalDBHelper localDBHelper = LocalDBHelper.getInstance(getContext());
                                                String typeSelected = accountTypes[which];
                                                AccountFields accountFields = localDBHelper.getAccountByUser(currUsername);
                                                accountFields.accountType = typeSelected;
                                                localDBHelper.updateAccountLine(accountFields);
                                                setTableRows(tableLayout, numRows, allids,allNames, alltypes);
                                                dialog.cancel();

                                            }
                                        }).create();
                                        builder1.show();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                        dialog.cancel();
                                    }
                                });
                        final AlertDialog alert = builder.create();
                        alert.show();
                    }
                });

                setPrem.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.3f));
                tableRow.addView(setPrem);
                Space space = new Space(getContext());
                space.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.05f));
                tableRow.addView(space);

                Button resetPass = new Button(getContext());
                resetPass.setText("Reset Pass");
                resetPass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Would you like to reset the password to nothing for this account?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                        LocalDBHelper localDBHelper = LocalDBHelper.getInstance(getContext());
                                        AccountFields accountFields = localDBHelper.getAccountByUser(currUsername);
                                        accountFields.passWord = AccountInfo.md5("");
                                        localDBHelper.updateAccountLine(accountFields);
                                        setTableRows(tableLayout, numRows, allids,allNames, alltypes);
                                        dialog.dismiss();

                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                        dialog.cancel();
                                    }
                                });
                        final AlertDialog alert = builder.create();
                        alert.show();
                    }
                });
                resetPass.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.3f));
                tableRow.addView(resetPass);
                Space space1 = new Space(getContext());
                space1.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.05f));
                tableRow.addView(space1);
                Button delete = new Button(getContext());
                delete.setText("Delete");
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Would you like to remove this account?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                        LocalDBHelper localDBHelper = LocalDBHelper.getInstance(getContext());
                                        localDBHelper.deleteAccount(currUsername);
                                        setTableRows(tableLayout, numRows, allids,allNames, alltypes);
                                        dialog.dismiss();

                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                        dialog.cancel();
                                    }
                                });
                        final AlertDialog alert = builder.create();
                        alert.show();
                    }
                });
                delete.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.3f));
                tableRow.addView(delete);
                final TableRow currTableRow = tableRow;
                tableRow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currTableRow.setBackground(clickDrawable);
                        Intent intent = new Intent(getActivity(), ListOfInspectionsOther.class);
                        LocalDBHelper.storeDataInSharedPreference(getContext(), "userviewing", currUsername);
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


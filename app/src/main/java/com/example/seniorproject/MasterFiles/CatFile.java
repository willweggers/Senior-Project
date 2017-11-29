package com.example.seniorproject.MasterFiles;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.seniorproject.Admin.PagerAdapterAMasterCat;
import com.example.seniorproject.R;

/**
 * Created by willw on 11/28/2017.
 */

public class CatFile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button ChangeGoogleAccount;
    private Button SyncData;
    private Button ResetPassword;
    private Button Logout;


    private OnFragmentInteractionListener mListener;

    public CatFile() {
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
    public static CatFile newInstance(String param1, String param2) {
        CatFile fragment = new CatFile();
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
        View view = inflater.inflate(R.layout.category_file, container, false);
        final ViewPager mViewPager = (ViewPager) view.findViewById(R.id.catpager);
        TabLayout bottomLayout = (TabLayout) view.findViewById(R.id.cattab);
        bottomLayout.addTab(bottomLayout.newTab().setText("Rail"));
        bottomLayout.addTab(bottomLayout.newTab().setText("Crossties"));
        bottomLayout.addTab(bottomLayout.newTab().setText("Switch Ties"));
        bottomLayout.addTab(bottomLayout.newTab().setText("Other Track"));
        bottomLayout.addTab(bottomLayout.newTab().setText("Turnout"));
        bottomLayout.addTab(bottomLayout.newTab().setText("Crossing"));
        bottomLayout.addTab(bottomLayout.newTab().setText("Other"));
        bottomLayout.addTab(bottomLayout.newTab().setText("Track Issues"));
        bottomLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        bottomLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        final PagerAdapterAMasterCat pageAdapter = new PagerAdapterAMasterCat(getChildFragmentManager(), bottomLayout.getTabCount());
        mViewPager.setAdapter(pageAdapter);
        mViewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(bottomLayout));

        bottomLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
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
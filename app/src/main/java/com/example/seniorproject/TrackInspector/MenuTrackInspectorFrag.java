package com.example.seniorproject.TrackInspector;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.seniorproject.AccountInfo;
import com.example.seniorproject.DB.AccountFields;
import com.example.seniorproject.DB.LocalDBHelper;
import com.example.seniorproject.FindLocalDBInfo;
import com.example.seniorproject.R;

/**
 * Created by willw on 11/27/2017.
 */

public class MenuTrackInspectorFrag extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MenuTrackInspectorFrag() {
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
    public static MenuTrackInspectorFrag newInstance(String param1, String param2) {
        MenuTrackInspectorFrag fragment = new MenuTrackInspectorFrag();
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
        //tabs
        View view = inflater.inflate(R.layout.inspections_tab, container, false);
        TextView usernameTitle = (TextView) view.findViewById(R.id.tititleusername);
        LocalDBHelper localDBHelper = LocalDBHelper.getInstance(getContext());
        String username = LocalDBHelper.getDataInSharedPreference(getContext(), "username");
        AccountFields accountFields = localDBHelper.getAccountByUser(username);
       String name =  FindLocalDBInfo.findFirstNameLastName(LocalDBHelper.getInstance(getContext()), username);
        if(accountFields.accountType.equals(AccountInfo.TI_PREM)) {
            usernameTitle.setText(name.concat(" Track Inspector"));
        }
        else if(accountFields.accountType.equals(AccountInfo.MANAGER_PREM)){
            usernameTitle.setText(name.concat(" Manager"));

        }
        final ViewPager mViewPager = (ViewPager) view.findViewById(R.id.pager);
        TabLayout bottomLayout = (TabLayout) view.findViewById(R.id.inspectionstabtab);
//        bottomLayout.addTab(bottomLayout.newTab().setText("Current Inspections"));
        bottomLayout.addTab(bottomLayout.newTab().setText("Old Inspections"));
        bottomLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        bottomLayout.setTabMode(TabLayout.MODE_FIXED);

        final PageAdapterInspections pageAdapter = new PageAdapterInspections(getChildFragmentManager(), bottomLayout.getTabCount());
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
        Button addInspectionButton = (Button) view.findViewById(R.id.addnewinspection);
        addInspectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HeaderData.class);
                startActivity(intent);

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
//    private class SectionsPagerAdapter extends FragmentPagerAdapter{
//        public SectionsPagerAdapter(FragmentManager fm){
//            super(fm);
//        }
//
//
//        @Override
//        public Fragment getItem(int position) {
//            switch (position){
//                case 0:
//                    return new ListInspectionsCurrent();
//                case 1:
//                    return new ListOfInspections();
//            }
//            return null;
//        }
//
//        @Override
//        public int getCount() {
//            return 2;
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//
//            switch (position) {
//                case 0:
//                    return "List Current Inspections";
//                default:
//                    return "List Old Inspections";
//            }
//
//
//        }
//    }
}

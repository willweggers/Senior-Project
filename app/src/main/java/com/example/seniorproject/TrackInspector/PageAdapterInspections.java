package com.example.seniorproject.TrackInspector;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.seniorproject.ListInspectionsCurrent;
import com.example.seniorproject.ListOfInspections;
import com.example.seniorproject.TrackInspector.SettingsTIFrag;

/**
 * Created by willw on 11/27/2017.
 */

public class PageAdapterInspections extends FragmentStatePagerAdapter {

    int mNoOfTabs;

    public PageAdapterInspections(FragmentManager fm, int NumberOfTabs)
    {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
//            case 0:
//                ListInspectionsCurrent listInspectionsCurrent = new ListInspectionsCurrent();
//                return listInspectionsCurrent;
            case 0:
                ListOfInspections oldInspections = new ListOfInspections();
                return oldInspections;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
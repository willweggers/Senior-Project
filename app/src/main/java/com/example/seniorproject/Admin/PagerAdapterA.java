package com.example.seniorproject.Admin;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.seniorproject.Manager.ListTrackInspectors;
import com.example.seniorproject.Manager.SettingsManager;
import com.example.seniorproject.TrackInspector.MenuTrackInspectorFrag;

/**
 * Created by willw on 11/27/2017.
 */

public class PagerAdapterA extends FragmentStatePagerAdapter {

    int mNoOfTabs;

    public PagerAdapterA(FragmentManager fm, int NumberOfTabs)
    {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0:
                return new ManageAccounts();
            case 1:
                return new ModiyMasterFiles();
            case 2:
                return new SettingsAdmin();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}

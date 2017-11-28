package com.example.seniorproject.Admin;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by willw on 11/28/2017.
 */

public class PagerAdapterAMasterCat extends FragmentStatePagerAdapter {

    int mNoOfTabs;

    public PagerAdapterAMasterCat(FragmentManager fm, int NumberOfTabs)
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

package com.example.seniorproject.Admin;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.seniorproject.MasterFiles.CatFile;
import com.example.seniorproject.MasterFiles.LaborMaster;
import com.example.seniorproject.MasterFiles.MobilizationMaster;
import com.example.seniorproject.MasterFiles.PriorityMaster;
import com.example.seniorproject.MasterFiles.StateMasterFile;

/**
 * Created by willw on 11/28/2017.
 */

public class PagerAdapterAMaster extends FragmentStatePagerAdapter {

    int mNoOfTabs;

    public PagerAdapterAMaster(FragmentManager fm, int NumberOfTabs) {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new StateMasterFile();
            case 1:
                return new CatFile();
            case 2:
                return new PriorityMaster();
            case 3:
                return new LaborMaster();
            case 4:
                return new MobilizationMaster();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
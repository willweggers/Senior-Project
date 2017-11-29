package com.example.seniorproject.Admin;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.seniorproject.MasterFiles.CategoryMFs.CrossingCat;
import com.example.seniorproject.MasterFiles.CategoryMFs.CrosstiesCat;
import com.example.seniorproject.MasterFiles.CategoryMFs.IssuesCat;
import com.example.seniorproject.MasterFiles.CategoryMFs.OtherCat;
import com.example.seniorproject.MasterFiles.CategoryMFs.OtherTrackCat;
import com.example.seniorproject.MasterFiles.CategoryMFs.RaileMasterFile;
import com.example.seniorproject.MasterFiles.CategoryMFs.SwitchesCat;
import com.example.seniorproject.MasterFiles.CategoryMFs.TurnoutCat;

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
                return new RaileMasterFile();
            case 1:
                return new CrosstiesCat();
            case 2:
                return new SwitchesCat();
            case 3:
                return new OtherTrackCat();
            case 4:
                return new TurnoutCat();
            case 5:
                return new CrossingCat();
            case 6:
                return new OtherCat();
            case 7:
                return new IssuesCat();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}

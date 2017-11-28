package com.example.seniorproject.Manager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.seniorproject.TrackInspector.MenuTrackInspectorFrag;

/**
 * Created by willw on 11/27/2017.
 */

public class PageAdapterM extends FragmentStatePagerAdapter {

    int mNoOfTabs;

    public PageAdapterM(FragmentManager fm, int NumberOfTabs)
    {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0:
                return new MenuTrackInspectorFrag();
            case 1:
                return new ListTrackInspectors();
            case 2:
                return new SettingsManager();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}

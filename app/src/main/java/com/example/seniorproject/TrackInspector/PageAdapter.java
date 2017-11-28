package com.example.seniorproject.TrackInspector;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.seniorproject.TrackInspector.MenuTrackInspectorFrag;
import com.example.seniorproject.TrackInspector.SettingsTIFrag;

/**
 * Created by Chirag on 30-Jul-17.
 */

public class PageAdapter extends FragmentStatePagerAdapter {

    int mNoOfTabs;

    public PageAdapter(FragmentManager fm, int NumberOfTabs)
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
                return new SettingsTIFrag();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
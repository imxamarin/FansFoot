package com.fansfoot.fansfoot;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by xamarin on 07/12/16.
 */

public class Pager extends FragmentStatePagerAdapter {

    int tabCount;
    public Pager(FragmentManager fm,int tabCount) {
        super(fm);
        this.tabCount= tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                HomeAlphaFragment tab1 = new HomeAlphaFragment();
                return tab1;
            case 1:
                HomeBetaFragment tab2 = new HomeBetaFragment();
                return tab2;
            case 2:
                HomeDeltaFragment tab3 = new HomeDeltaFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}

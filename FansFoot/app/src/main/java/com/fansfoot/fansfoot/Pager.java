package com.fansfoot.fansfoot;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.fansfoot.fansfoot.fragments.HomeAlphaFragment;
import com.fansfoot.fansfoot.fragments.HomeBetaFragment;
import com.fansfoot.fansfoot.fragments.HomeDeltaFragment;

/**
 * Created by xamarin on 07/12/16.
 */

public class Pager extends FragmentStatePagerAdapter {

    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
    CharSequence Titles[];
    int tabCount;
    Fragment fragment;
    public Pager(FragmentManager fm,CharSequence mTitles[],int tabCount) {
        super(fm);
        this.tabCount= tabCount;
        Titles = mTitles;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            HomeAlphaFragment homeAlphaFragment = (HomeAlphaFragment) HomeAlphaFragment.newInstance();
            return homeAlphaFragment;
        } else if (position == 1) {
            HomeBetaFragment homeBetaFragment = (HomeBetaFragment) HomeBetaFragment.newInstance();
            return homeBetaFragment;
        } else if (position == 2) {
            HomeDeltaFragment draftFragment = (HomeDeltaFragment) HomeDeltaFragment.newInstance();
            return draftFragment;
        }
        return null;



    }

    @Override
    public int getCount() {
        return tabCount;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position){
        return registeredFragments.get(position);
    }

}



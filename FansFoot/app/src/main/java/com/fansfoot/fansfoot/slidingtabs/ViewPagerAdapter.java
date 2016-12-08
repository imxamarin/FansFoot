package com.fansfoot.fansfoot.slidingtabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.fansfoot.fansfoot.fragments.HomeAlphaFragment;
import com.fansfoot.fansfoot.fragments.HomeBetaFragment;
import com.fansfoot.fansfoot.fragments.HomeDeltaFragment;



public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    Fragment fragment;

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {
        /**
         * different-different fragments open,click on Sliding Tabs using switch case
         */
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

    // This method return the titles for the Tabs in the Tab Strip
    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }


    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }

}
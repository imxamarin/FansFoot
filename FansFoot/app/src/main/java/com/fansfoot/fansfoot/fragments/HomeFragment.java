package com.fansfoot.fansfoot.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fansfoot.fansfoot.R;
import com.fansfoot.fansfoot.slidingtabs.SlidingTabLayout;
import com.fansfoot.fansfoot.slidingtabs.ViewPagerAdapter;

/**
 * Created by xamarin on 08/12/16.
 */

public class HomeFragment extends Fragment {

    Context mContext;
    Activity mActivity;
    ViewPagerAdapter adapter;
    CharSequence titles[];
    static ViewPager mView_pager;
    SlidingTabLayout mTabs;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_layout, container, false);

        mContext = getActivity();
        mActivity = getActivity();

        titles = new CharSequence[]{getResources().getString(R.string.alpha)
                , getResources().getString(R.string.beta), getResources().getString(R.string.gama)};

        initialize_Viewpager_for_slidingTabs(view);
        return view;
    }

    private void initialize_Viewpager_for_slidingTabs(View view) {

        mView_pager = (ViewPager) (view.findViewById(R.id.view_pager));
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter = new ViewPagerAdapter(getChildFragmentManager(), titles, 3);
        // Assigning ViewPager View and setting the adapter
        mView_pager.setAdapter(adapter);
        mView_pager.setOffscreenPageLimit(2);

        // Assiging the Sliding Tab Layout View
        mTabs = (SlidingTabLayout) view.findViewById(R.id.tabs);
        //   mTabs.setDividerColors(getResources().getColor(R.color.white));
        mTabs.setSelectedIndicatorColors(ContextCompat.getColor(mContext, R.color.red));
        // Setting the ViewPager For the SlidingTabsLayout
        mTabs.setViewPager(mView_pager);


        mView_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }
        });
    }


    public static int getCurrentPage() {

        return mView_pager.getCurrentItem();
    }


    public static ViewPager getViewPager() {
        return mView_pager;
    }

    public ViewPager getViewPagerInstance() {
        return mView_pager;
    }





}
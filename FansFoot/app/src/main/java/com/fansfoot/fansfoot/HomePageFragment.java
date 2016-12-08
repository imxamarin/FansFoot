package com.fansfoot.fansfoot;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fansfoot.fansfoot.slidingtabs.SlidingTabLayout;
import com.fansfoot.fansfoot.slidingtabs.ViewPagerAdapter;

/**
 * Created by xamarin on 08/12/16.
 */

public class HomePageFragment extends Fragment {

    Context mContext;
    Activity mActivity;
    Pager adapter;
    CharSequence titles[];
    static ViewPager mView_pager;
    TabLayout mTabs;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.homepage_fragment, container, false);

        mContext = getActivity();
        mActivity = getActivity();

        titles = new CharSequence[]{getResources().getString(R.string.alpha)
                , getResources().getString(R.string.beta), getResources().getString(R.string.gama)};
        initialize_Viewpager_for_slidingTabs(view);
        return view;
    }

    private void initialize_Viewpager_for_slidingTabs(View view) {

    mView_pager = (ViewPager)(view.findViewById(R.id.pager));
        adapter = new Pager(getChildFragmentManager(),titles,3);
    mView_pager.setAdapter(adapter);
    mView_pager.setOffscreenPageLimit(2);
        mTabs = (TabLayout) view.findViewById(R.id.tabLayout);
        mTabs.setupWithViewPager(mView_pager);
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

    public static ViewPager getmView_pager(){
        return mView_pager;
    }

    public ViewPager getViewPagerInstance(){
        return mView_pager;
    }


}

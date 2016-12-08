package com.fansfoot.fansfoot;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

/**
 * Created by kafir on 09-Dec-16.
 */

public class HomePage extends FragmentActivity {
    PageAdapter mDemoCollection;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = (TabLayout) findViewById(R.id.TabbedLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Alpha"));
        tabLayout.addTab(tabLayout.newTab().setText("Beta"));
        tabLayout.addTab(tabLayout.newTab().setText("Gamma"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mDemoCollection = new PageAdapter(
                getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager = (ViewPager) findViewById(R.id.Pager);
        viewPager.setAdapter(mDemoCollection);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
}

package com.fansfoot.fansfoot;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;


public class MainActivity extends FragmentActivity {
    Button HomeButton,SectionButton,VideoButton,ProfileButton,SettingButton;
    static FragmentManager fragmentManager;
    Context context;
    PageAdapter mDemoCollection;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CallthisForTab();
        context = this;
        CallThisToPerformButtonAction();

    }

    public void CallthisForTab(){

        tabLayout = (TabLayout) findViewById(R.id.TabbedLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Alpha"));
        tabLayout.addTab(tabLayout.newTab().setText("Beta"));
        tabLayout.addTab(tabLayout.newTab().setText("Gamma"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
       // tabLayout.setupWithViewPager(viewPager);
        mDemoCollection = new PageAdapter(
                getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager = (ViewPager) findViewById(R.id.Pager);
        viewPager.setAdapter(mDemoCollection);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    private void HomeFragment(){
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//
//        fragmentTransaction.replace(R.id.frag,home);
//        fragmentTransaction.commit();
    }

    private void SelectionFragment(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        SelectionPage profilePage  = new SelectionPage();
        fragmentTransaction.replace(R.id.frag,profilePage);
        fragmentTransaction.commit();
    }

    private void ProfileFragment(){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ProfilePage profilePage  = new ProfilePage();
        fragmentTransaction.replace(R.id.frag,profilePage);
        fragmentTransaction.commit();
    }

    private void VideoFragment(){
         fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        VideoPage videoPage = new VideoPage();
        fragmentTransaction.replace(R.id.frag,videoPage);
        fragmentTransaction.commit();
    }

    private void SettingsFragment(){

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SettingsPage settingsPage  = new SettingsPage();
        fragmentTransaction.replace(R.id.frag,settingsPage);
        fragmentTransaction.commit();
    }

    private void CallThisToPerformButtonAction() {
        HomeButton = (Button) findViewById(R.id.HomeButton);
        SectionButton = (Button) findViewById(R.id.SectionButton);
        VideoButton = (Button) findViewById(R.id.VideoButton);
        ProfileButton = (Button) findViewById(R.id.ProfileButton);
        SettingButton = (Button) findViewById(R.id.SettingButton);

        HomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  CallthisForTab();
            }
        });

        SectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectionFragment();
            }
        });

        VideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               VideoFragment();
            }
        });

        ProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileFragment();
            }
        });

        SettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            SettingsFragment();
            }
        });

    }

    public  static FragmentManager getBaseFragmentManager(){
        return  fragmentManager;
    }




    }


package com.fansfoot.fansfoot;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button HomeButton,SectionButton,VideoButton,ProfileButton,SettingButton;
    static FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HomePage home = new HomePage();
        fragmentTransaction.add(R.id.frag,home);
        fragmentTransaction.commit();
        CallThisToPerformButtonAction();
    }

    private void HomeFragment(){

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HomePage home = new HomePage();
        fragmentTransaction.replace(R.id.frag,home);
        fragmentTransaction.commit();
    }

    private void SelectionFragment(){

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SelectionPage selectionPage = new SelectionPage();
        fragmentTransaction.replace(R.id.frag,selectionPage);
        fragmentTransaction.commit();
    }

    private void ProfileFragment(){

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ProfilePage profilePage  = new ProfilePage();
        fragmentTransaction.replace(R.id.frag,profilePage);
        fragmentTransaction.commit();
    }

    private void VideoFragment(){
         fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
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
                HomeFragment();
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

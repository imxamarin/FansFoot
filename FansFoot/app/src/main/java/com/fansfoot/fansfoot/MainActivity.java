package com.fansfoot.fansfoot;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import com.fansfoot.fansfoot.DefaultPages.GifPage;
import com.fansfoot.fansfoot.DefaultPages.HomePage;
import com.fansfoot.fansfoot.DefaultPages.MemesPage;
import com.fansfoot.fansfoot.DefaultPages.NsfwPage;
import com.fansfoot.fansfoot.DefaultPages.ProfilePage;
import com.fansfoot.fansfoot.DefaultPages.SelectionPage;
import com.fansfoot.fansfoot.DefaultPages.SettingsPage;
import com.fansfoot.fansfoot.DefaultPages.VideoPage;

import java.net.URISyntaxException;


public class MainActivity extends AppCompatActivity {
    Button HomeButton,SectionButton,VideoButton,ProfileButton,SettingButton;
    static FragmentManager fragmentManager;
    static FragmentManager supportFrag;
    static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ProfilePage profilePage = new ProfilePage();
        fragmentTransaction.add(R.id.frag,profilePage);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        CallThisToPerformButtonAction();
    }


    private void HomeFragment(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        HomePage home = new HomePage();
        fragmentTransaction.replace(R.id.frag,home);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    private void SelectionFragment(){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        SelectionPage selectionPage = new SelectionPage();
        fragmentTransaction.replace(R.id.frag,selectionPage);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void ProfileFragment(){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ProfilePage profilePage  = new ProfilePage();
        fragmentTransaction.replace(R.id.frag,profilePage);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void VideoFragment(){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        VideoPage videoPage = null;
        try {
            videoPage = new VideoPage();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        fragmentTransaction.replace(R.id.frag,videoPage);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void SettingsFragment(){

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SettingsPage settingsPage  = new SettingsPage();
        fragmentTransaction.replace(R.id.frag,settingsPage);
        fragmentTransaction.commit();
    }


    private void MemesFragment(){

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MemesPage memesPage  = new MemesPage();
        fragmentTransaction.add(R.id.frag,memesPage);
        fragmentTransaction.commit();
    }

    private void NSFWFragment(){

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        NsfwPage nsfwPage  = new NsfwPage();
        fragmentTransaction.add(R.id.frag,nsfwPage);
        fragmentTransaction.commit();
    }

    private void AnimatedFragment(){

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        GifPage gifPage  = new GifPage();
        fragmentTransaction.add(R.id.frag,gifPage);
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
                AnimatedFragment();
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
            //SettingsFragment();
               SettingsFragment();
            }
        });

    }

    public  static FragmentManager getBaseFragmentManager(){
        return  fragmentManager;
    }
    public static MenuInflater gettheMenuInflater() {
        return new MenuInflater(context);
    }
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
    }

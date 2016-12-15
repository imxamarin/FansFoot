package com.fansfoot.fansfoot;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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
    //Button HomeButton, SectionButton, VideoButton, ProfileButton, SettingButton;
    static FragmentManager fragmentManager;
    static FragmentManager supportFrag;
    static Context context;

    RadioGroup bottom_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.cm_action_bar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        OpenDefaultFragment();
        //CallThisToPerformButtonAction();
        bottom_layout = (RadioGroup)findViewById(R.id.bottom_layout);
        bottom_layout.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                View radioButton = radioGroup.findViewById(i);
                int index = radioGroup.indexOfChild(radioButton);

                switch (index){
                    case 0:
                        HomeFragment();

                        break;
                    case 1:
                        SelectionFragment();
                        break;
                    case 2:
                        VideoFragment();

                        break;
                    case 3:
                        ProfileFragment();

                        break;
                    case 4:
                        SettingsFragment();
                        break;
                    default:
                        break;

                }

            }

        });
    }

    private void OpenDefaultFragment() {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HomePage homePage = new HomePage();
        fragmentTransaction.add(R.id.frag, homePage);
        fragmentTransaction.commit();
    }

    private void HomeFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        HomePage home = new HomePage();
        fragmentManager.popBackStackImmediate();
        fragmentTransaction.replace(R.id.frag, home);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    private void SelectionFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        SelectionPage selectionPage = new SelectionPage();
        fragmentManager.popBackStackImmediate();
        fragmentTransaction.replace(R.id.frag, selectionPage);
        fragmentTransaction.commit();
    }

    private void ProfileFragment() {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ProfilePage profilePage = new ProfilePage();
        fragmentManager.popBackStackImmediate();
        fragmentTransaction.replace(R.id.frag, profilePage);
        fragmentTransaction.commit();
    }

    private void VideoFragment() {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        VideoPage videoPage = new VideoPage();

        fragmentManager.popBackStackImmediate();
        fragmentTransaction.replace(R.id.frag, videoPage);
        fragmentTransaction.commit();
    }

    private void SettingsFragment() {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SettingsPage settingsPage = new SettingsPage();
        fragmentManager.popBackStackImmediate();
        fragmentTransaction.replace(R.id.frag, settingsPage);
        fragmentTransaction.commit();
    }


    private void MemesFragment() {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MemesPage memesPage = new MemesPage();
        fragmentTransaction.add(R.id.frag, memesPage);
        fragmentTransaction.commit();
    }

    private void NSFWFragment() {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        NsfwPage nsfwPage = new NsfwPage();
        fragmentTransaction.add(R.id.frag, nsfwPage);
        fragmentTransaction.commit();
    }

    private void AnimatedFragment() {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        GifPage gifPage = new GifPage();
        fragmentTransaction.add(R.id.frag, gifPage);
        fragmentTransaction.commit();
    }


    public static FragmentManager getBaseFragmentManager() {
        return fragmentManager;
    }
    public static Context getContext() {
        return context;
    }

//    public static MenuInflater gettheMenuInflater() {
//        return new MenuInflater(context);
//    }

//    public void setActionBarTitle(String title) {
//        getSupportActionBar().setTitle(title);
//    }
//public void setActionBarTitle(String title)
//{
//    View v = getSupportActionBar().getCustomView();
//    TextView titleTxtView = (TextView) v.findViewById(R.id.cm_Title);
//    titleTxtView.setText(title);
//}
//    public void setActionBarAlpha(Drawable title)
//    {
//
//        View v = getSupportActionBar().getCustomView();
//        ImageButton titleTxtView = (ImageButton) v.findViewById(R.id.cm_ActionBar_Refesh);
//        titleTxtView.setBackground(title);
//        titleTxtView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view,"Reloading",Snackbar.LENGTH_SHORT).show();
//            }
//        });
//    }

//    public void setActionBarBeta(Drawable title)
//    {
//        View v = getSupportActionBar().getCustomView();
//        ImageButton titleTxtView = (ImageButton) v.findViewById(R.id.ActionBarBetaLogo);
//        titleTxtView.setBackground(title);
//        titleTxtView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view,"Searching",Snackbar.LENGTH_SHORT).show();
//            }
//        });
//    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.dispatchTouchEvent(ev);
    }
}


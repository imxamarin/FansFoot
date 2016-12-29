package com.fansfoot.fansfoot;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.fansfoot.fansfoot.API.FacebookStatus;
import com.fansfoot.fansfoot.DefaultPages.GifPage;
import com.fansfoot.fansfoot.DefaultPages.HomePage;
import com.fansfoot.fansfoot.DefaultPages.LoginPage;
import com.fansfoot.fansfoot.DefaultPages.MemesPage;
import com.fansfoot.fansfoot.DefaultPages.NsfwPage;
import com.fansfoot.fansfoot.DefaultPages.ProfilePage;
import com.fansfoot.fansfoot.DefaultPages.SelectionPage;
import com.fansfoot.fansfoot.DefaultPages.SettingsPage;
import com.fansfoot.fansfoot.DefaultPages.VideosPage;
import com.fansfoot.fansfoot.models.LikeTransition;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class MainActivity extends AppCompatActivity  {

    RadioButton ProfileButton;
    static FragmentManager fragmentManager;
    static FragmentManager supportFrag;
    static Context context;
    SharedPreferences sharedPreferencesBeta;
    public static RadioGroup bottom_layout;
    SharedPreferences.Editor editor;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_main);
        ProfileButton=(RadioButton) findViewById(R.id.ProfileButton);
        context = this;
        sharedPreferencesBeta =context.getSharedPreferences("FansFootPerfrence", Context.MODE_PRIVATE);
        String UUID =  Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        editor = sharedPreferencesBeta.edit();
        editor.putString("UUID", UUID);
        editor.commit();
        FacebookSdk.sdkInitialize(getApplicationContext());
        CallbackManager  callbackManager = CallbackManager.Factory.create();
        OpenDefaultFragment();
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

    public void ProfileFragment() {
        boolean fb_status = FacebookStatus.CheckFbLogin();
        boolean value = sharedPreferencesBeta.getString("FbFFID","").isEmpty();
        if(fb_status == true)
        {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            ProfilePage loginprofilePage = new ProfilePage();
            fragmentManager.popBackStackImmediate();
            fragmentTransaction.replace(R.id.frag, loginprofilePage);
            fragmentTransaction.commit();
        }else if(!value){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            ProfilePage loginprofilePage = new ProfilePage();
            fragmentManager.popBackStackImmediate();
            fragmentTransaction.replace(R.id.frag, loginprofilePage);
            fragmentTransaction.commit();
        }

        else {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            LoginPage loginprofilePage = new LoginPage();
            fragmentManager.popBackStackImmediate();
            fragmentTransaction.replace(R.id.frag, loginprofilePage);
            fragmentTransaction.commit();
        }
    }

    private void VideoFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        VideosPage videoPage = new VideosPage();
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

    public static FragmentManager getBaseFragmentManager() {
        return fragmentManager;
    }
    public static Context getContext() {
        return context;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LikeTransition likeTransition){

        if(likeTransition.getFragName().equalsIgnoreCase("profile")){
            Log.e("pos",""+likeTransition.getPos());
            bottom_layout.check(R.id.ProfileButton);
            ProfileFragment();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideKeyboard(this);
    }

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

    public static void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;
        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

}


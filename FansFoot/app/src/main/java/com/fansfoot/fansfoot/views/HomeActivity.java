package com.fansfoot.fansfoot.views;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.fansfoot.fansfoot.R;
import com.fansfoot.fansfoot.fragments.HomeFragment;

public class HomeActivity extends FragmentActivity {

    HomeFragment fragment;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();


        fragment = new HomeFragment();

         /*[By default Open Fragment]*/
        changeFragment(new HomeFragment());
    }


    public void changeFragment(android.support.v4.app.Fragment targetFragment) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentParentView, targetFragment);
        fragmentTransaction.commit();
    }


}

package com.fansfoot.fansfoot;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fansfoot.fansfoot.fragments.HomeFragment;

import static com.fansfoot.fansfoot.MainActivity.fragmentManager;


/**
 * Created by xamarin on 05/12/16.
 */

public class HomePage extends FragmentActivity {
    HomeFragment fragment;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_fragment);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();


        fragment = new HomeFragment();

         /*[By default Open Fragment]*/
        changeFragment(new HomeFragment());
    }
    public void changeFragment(Fragment targetFragment) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentParentView, targetFragment);
        fragmentTransaction.commit();
    }


}




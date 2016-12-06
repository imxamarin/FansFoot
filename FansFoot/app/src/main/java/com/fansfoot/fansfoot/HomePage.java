package com.fansfoot.fansfoot;

import android.app.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * Created by xamarin on 05/12/16.
 */

public class HomePage extends Fragment {


    Button btn,btne,btnm;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homepage_fragment,null,false);

        btn = (Button) container.findViewById(R.id.alphawps);
        btne = (Button) container.findViewById(R.id.alphawqs);
        btnm = (Button) container.findViewById(R.id.alphawws);
        FragmentManager childFragMan = getChildFragmentManager();
        FragmentTransaction childFragTrans = childFragMan.beginTransaction();
        HomeAlphaFragment fragA = new HomeAlphaFragment();
        childFragTrans.add(R.id.pagerFrga, fragA);
        childFragTrans.addToBackStack("A");
        childFragTrans.commit();
        Callthats();
        return  view;

    }


       public void Callthats(){
           btn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   DoThis();
               }
           });

           btnm.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   DoThat();
               }
           });

           btne.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   DoThem();
               }
           });

    }

    private void DoThis() {
        FragmentManager childFragMan = getChildFragmentManager();
        FragmentTransaction childFragTrans = childFragMan.beginTransaction();
        HomeAlphaFragment fragA = new HomeAlphaFragment();
        childFragTrans.add(R.id.pagerFrga, fragA);
        childFragTrans.addToBackStack("A");
        childFragTrans.commit();
    }
    private void DoThat() {
        FragmentManager childFragMan = getChildFragmentManager();
        FragmentTransaction childFragTrans = childFragMan.beginTransaction();
        HomeBetaFragment fragB = new HomeBetaFragment();
        childFragTrans.add(R.id.pagerFrga, fragB);
        childFragTrans.addToBackStack("B");
        childFragTrans.commit();
    }

    private void DoThem() {
        FragmentManager childFragMan = getChildFragmentManager();
        FragmentTransaction childFragTrans = childFragMan.beginTransaction();
        HomeDeltaFragment fragC = new HomeDeltaFragment();
        childFragTrans.add(R.id.pagerFrga, fragC);
        childFragTrans.addToBackStack("C");
        childFragTrans.commit();
    }

}


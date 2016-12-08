package com.fansfoot.fansfoot.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fansfoot.fansfoot.R;

/**
 * Created by xamarin on 06/12/16.
 */

public class HomeAlphaFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alpha_home_fragment,null,false);
        return view;
    }

    public static HomeAlphaFragment newInstance() {
        HomeAlphaFragment homeAlphaFragment = new HomeAlphaFragment();
        return homeAlphaFragment;
    }
}

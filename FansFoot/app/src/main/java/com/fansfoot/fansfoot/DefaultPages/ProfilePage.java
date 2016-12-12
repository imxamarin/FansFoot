package com.fansfoot.fansfoot.DefaultPages;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fansfoot.fansfoot.Adapters.ProfileRecycleViewAdapter;
import com.fansfoot.fansfoot.MainActivity;
import com.fansfoot.fansfoot.R;

/**
 * Created by xamarin on 05/12/16.
 */

public class ProfilePage extends Fragment {

    Context context;
    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;
    String[] userDetail = {
            "Name",
            "City",
            "Country",
            "Birthday"
    };

    String[] userValues = {
            "Rohit",
            "Chandigarh",
            "India",
            "30 Feb"
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment,null,false);
        context = getActivity();
        ((MainActivity) getActivity()).setActionBarTitle("PROFILE");
        ((MainActivity) getActivity()).setActionBarAlpha(getResources().getDrawable(R.drawable.back_icon));
        ((MainActivity) getActivity()).setActionBarBeta(getResources().getDrawable(R.drawable.search));
        recyclerView = (RecyclerView) view.findViewById(R.id.ProfileRecycleView);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerViewAdapter = new ProfileRecycleViewAdapter(userDetail,userValues,context);
        recyclerView.setAdapter(recyclerViewAdapter);
        return  view;
    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        MenuInflater _menu_inflater = MainActivity.gettheMenuInflater();
//        _menu_inflater.inflate(R.menu.profile_menu,menu);
//    }



}
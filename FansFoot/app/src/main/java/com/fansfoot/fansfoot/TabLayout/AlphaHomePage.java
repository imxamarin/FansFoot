package com.fansfoot.fansfoot.TabLayout;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.fansfoot.fansfoot.Adapters.AlphaHomeRecycleViewAdapter;
import com.fansfoot.fansfoot.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by kafir on 09-Dec-16.
 */

public class AlphaHomePage extends Fragment {

    Context context;
    SearchView searchView;
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


    String [] points = {"1","2","3","2"};
    String[] comments = {"56","48","78","96"};
    int[] imgGallery = {R.drawable.post_img,
            R.drawable.post_img,
            R.drawable.post_img,
            R.drawable.post_img,
            R.drawable.post_img
    };

    String[] pics = {"http://www.redbitdev.com/wp-content/uploads/2014/04/xamarin.jpeg",
    "http://www.windows10update.com/wp-content/uploads/2016/03/xamarin_logo.jpg",
            "http://www.hexacta.com/wp-content/uploads/2016/03/xamarin.jpg",
            "https://sergeytihon.files.wordpress.com/2015/04/w2.jpg",
            "http://www.redbitdev.com/wp-content/uploads/2014/04/xamarin.jpeg"
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.alpha_home_fragment,null,false);
        context = this.getContext();

        final SwipeRefreshLayout swipe = (SwipeRefreshLayout) view.findViewById(R.id.AlphaSwipe);
        swipe.setColorSchemeColors(getResources().getColor(R.color.colorPrimaryDarkest),getResources().getColor(R.color.holo_blue_light));
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Snackbar.make(view,"Refreshing",Snackbar.LENGTH_SHORT).show();

                final Handler handler = new Handler();
                Runnable runable = new Runnable() {
                    @Override
                    public void run() {
                        swipe.setRefreshing(false);
                    }
                };
                handler.postDelayed(runable, 2000);
            }

        });

        AdView mAdView = (AdView) view.findViewById(R.id.adViewAlpha);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        recyclerView = (RecyclerView) view.findViewById(R.id.AlphaRecycleView);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerViewAdapter = new AlphaHomeRecycleViewAdapter(userDetail,pics,points,comments,context);
        recyclerView.setAdapter(recyclerViewAdapter);
        return view;
    }

}

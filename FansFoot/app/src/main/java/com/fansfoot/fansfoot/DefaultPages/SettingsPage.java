package com.fansfoot.fansfoot.DefaultPages;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fansfoot.fansfoot.MainActivity;
import com.fansfoot.fansfoot.R;
import com.fansfoot.fansfoot.Adapters.SettingsRecycleViewAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by xamarin on 05/12/16.
 */

public class SettingsPage  extends Fragment {

    Context context;
    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;
    String[] settings = {
            "Profile",
            "About us",
            "Rating",
            "Feedback",
            "NSFW",
            "Share this app",
            "Report a problem",
            "Like us on Facebook",
            "Follow us on Twitter"
    };

    int[] imageSet = {
            R.drawable.next_arrow,
            R.drawable.setting_icon_event,
            R.drawable.setting_icon_event,
            R.drawable.setting_icon_event,
            R.drawable.on_toggle,
            R.drawable.setting_icon_event,
            R.drawable.setting_icon_event,
            R.drawable.facebook,
            R.drawable.twitter
    };



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_fragment,container,false);
        context = getActivity();
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.Settingstoolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        AdView mAdView = (AdView) view.findViewById(R.id.adViewSettings);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        recyclerView = (RecyclerView) view.findViewById(R.id.settingRecycle);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerViewAdapter = new SettingsRecycleViewAdapter(settings,imageSet,context);
        recyclerView.setAdapter(recyclerViewAdapter);
        return  view;
    }
}
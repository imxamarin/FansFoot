package com.fansfoot.fansfoot;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
            R.drawable.next_arrow,
            R.drawable.next_arrow,
            R.drawable.next_arrow,
            R.drawable.on_toggle,
            R.drawable.next_arrow,
            R.drawable.next_arrow,
            R.drawable.facebook,
            R.drawable.twitter
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_fragment,null,false);
        context = getActivity();
        recyclerView = (RecyclerView) view.findViewById(R.id.settingRecycle);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerViewAdapter = new SettingsRecycleViewAdapter(settings,imageSet,context);
        recyclerView.setAdapter(recyclerViewAdapter);
        return  view;
    }
}
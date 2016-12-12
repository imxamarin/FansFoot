package com.fansfoot.fansfoot.DefaultPages;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fansfoot.fansfoot.MainActivity;
import com.fansfoot.fansfoot.R;
import com.fansfoot.fansfoot.Adapters.SettingsRecycleViewAdapter;

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
            R.drawable.off_toggle,
            R.drawable.setting_icon_event,
            R.drawable.setting_icon_event,
            R.drawable.facebook,
            R.drawable.twitter
    };



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_fragment,null,false);
        context = getActivity();
        ((MainActivity) getActivity()).setActionBarTitle("SETTINGS");
        ((MainActivity) getActivity()).setActionBarAlpha(getResources().getDrawable(R.drawable.back_icon));
        ((MainActivity) getActivity()).setActionBarBeta(getResources().getDrawable(R.drawable.search));
        recyclerView = (RecyclerView) view.findViewById(R.id.settingRecycle);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerViewAdapter = new SettingsRecycleViewAdapter(settings,imageSet,context);
        recyclerView.setAdapter(recyclerViewAdapter);
        return  view;
    }
}
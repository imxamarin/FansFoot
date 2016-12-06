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

public class SelectionPage extends Fragment {

    Context context;
    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;
    String[] subjects = {
            "Memes",
            "Videos",
            "NSFW",
            "Animated GIF"
    };

    int[] imageSub = {
            R.drawable.memes,
            R.drawable.video,
            R.drawable.nsfw,
            R.drawable.animated
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.selection_fragment,null,false);
        context = getActivity();
        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerViewAdapter = new SelectionRecyclerViewAdapter(subjects,imageSub,context);
        recyclerView.setAdapter(recyclerViewAdapter);
        return  view;
    }
}
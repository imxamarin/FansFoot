package com.fansfoot.fansfoot;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by xamarin on 09/12/16.
 */

public class SelectionFragmentActivity extends FragmentActivity {


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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selection_fragment);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerViewAdapter = new SelectionRecyclerViewAdapter(subjects,imageSub,context);
        recyclerView.setAdapter(recyclerViewAdapter);
    }
}

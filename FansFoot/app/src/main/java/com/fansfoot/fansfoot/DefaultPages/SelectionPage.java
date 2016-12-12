package com.fansfoot.fansfoot.DefaultPages;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fansfoot.fansfoot.MainActivity;
import com.fansfoot.fansfoot.R;
import com.fansfoot.fansfoot.Adapters.SelectionRecyclerViewAdapter;


/**
 * Created by xamarin on 05/12/16.
 */

public class SelectionPage extends Fragment {
    static FragmentManager fragmentManager;
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

    Button GifButton,MemesButton,NsfwButton,VideoButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getFragmentManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.selection_fragment,null,false);
        context = getContext();
        ((MainActivity) getActivity()).setActionBarTitle("SELECTION");
        ((MainActivity) getActivity()).setActionBarAlpha(getResources().getDrawable(R.drawable.back_icon));
        ((MainActivity) getActivity()).setActionBarBeta(getResources().getDrawable(R.drawable.search));
        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerViewAdapter = new SelectionRecyclerViewAdapter(subjects,imageSub,context);
        recyclerView.setAdapter(recyclerViewAdapter);



        return  view;
    }

    public static FragmentManager getFragment(){
        return fragmentManager;
    }

}
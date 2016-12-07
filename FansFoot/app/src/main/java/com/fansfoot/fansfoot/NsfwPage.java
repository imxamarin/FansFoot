package com.fansfoot.fansfoot;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xamarin on 07/12/16.
 */

public class NsfwPage extends Fragment {

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


    String [] points = {"1","2","3","2"};
    String[] comments = {"56","48","78","96"};
    int[] imgGallery = {R.drawable.post_img,
            R.drawable.post_img,
            R.drawable.post_img,
            R.drawable.post_img,
            R.drawable.post_img
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nsfw_fragment,null,false);
        context = getActivity();
        recyclerView = (RecyclerView) view.findViewById(R.id.nfswRecycle);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerViewAdapter = new ImageRecycleViewAdapter(userDetail,imgGallery,points,comments,context);
        recyclerView.setAdapter(recyclerViewAdapter);
        return view;
    }
}

package com.fansfoot.fansfoot.TabLayout;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fansfoot.fansfoot.Adapters.BetaHomeRecycleViewAdapter;
import com.fansfoot.fansfoot.R;

/**
 * Created by kafir on 09-Dec-16.
 */

public class BetaHomePage extends Fragment {


    Context context;
    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;
    String[] userDetail = {
            "Yuhu",
            "luhu",
            "home",
            "james"
    };

    String[] userValues = {
            "Rohit",
            "Chandigarh",
            "India",
            "30 Feb"
    };


    String [] points = {"81","92","37","266"};
    String[] comments = {"5","8","738","6"};
    int[] imgGallery = {R.drawable.post_img,
            R.drawable.post_img,
            R.drawable.post_img,
            R.drawable.post_img,
            R.drawable.post_img
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.beta_home_fragment,container,false);
        context = this.getActivity();
        recyclerView = (RecyclerView) view.findViewById(R.id.BetaRecycleView);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerViewAdapter = new BetaHomeRecycleViewAdapter(userDetail,imgGallery,points,comments,context);
        recyclerView.setAdapter(recyclerViewAdapter);
        return view;
    }
}

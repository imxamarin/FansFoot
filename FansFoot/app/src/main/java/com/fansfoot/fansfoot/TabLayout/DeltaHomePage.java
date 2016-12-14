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

import com.fansfoot.fansfoot.Adapters.DeltaHomeRecycleViewAdapter;
import com.fansfoot.fansfoot.R;

/**
 * Created by kafir on 09-Dec-16.
 */

public class DeltaHomePage extends Fragment {

    Context context;
    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;
    String[] userDetail = {
            "Thor",
            "DrJone",
            "Indi",
            "Raj"
    };

    String[] userValues = {
            "Hola",
            "Chandigarh",
            "India",
            "30 Feb"
    };


    String [] points = {"21","24","53","27"};
    String[] comments = {"5","4","7","6"};
    int[] imgGallery = {R.drawable.post_img,
            R.drawable.post_img,
            R.drawable.post_img,
            R.drawable.post_img,
            R.drawable.post_img
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.delta_home_fragment,container,false);
        context = this.getActivity();
        final SwipeRefreshLayout swipe = (SwipeRefreshLayout) view.findViewById(R.id.DeltaSwipe);
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
        recyclerView = (RecyclerView) view.findViewById(R.id.DeltaRecycleView);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerViewAdapter = new DeltaHomeRecycleViewAdapter(userDetail,imgGallery,points,comments,context);
        recyclerView.setAdapter(recyclerViewAdapter);
        return view;
    }
}

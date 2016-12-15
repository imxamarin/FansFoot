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
    String[] pics = {"http://www.redbitdev.com/wp-content/uploads/2014/04/xamarin.jpeg",
            "http://www.windows10update.com/wp-content/uploads/2016/03/xamarin_logo.jpg",
            "http://www.hexacta.com/wp-content/uploads/2016/03/xamarin.jpg",
            "https://sergeytihon.files.wordpress.com/2015/04/w2.jpg",
            "http://www.redbitdev.com/wp-content/uploads/2014/04/xamarin.jpeg"
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.beta_home_fragment,container,false);
        context = this.getActivity();
        final SwipeRefreshLayout swipe = (SwipeRefreshLayout) view.findViewById(R.id.BetaSwipe);
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
        recyclerView = (RecyclerView) view.findViewById(R.id.BetaRecycleView);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerViewAdapter = new BetaHomeRecycleViewAdapter(userDetail,pics,points,comments,context);
        recyclerView.setAdapter(recyclerViewAdapter);
        return view;
    }
}

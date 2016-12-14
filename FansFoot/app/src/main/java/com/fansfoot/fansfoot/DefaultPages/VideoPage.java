package com.fansfoot.fansfoot.DefaultPages;

import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.fansfoot.fansfoot.MainActivity;
import com.fansfoot.fansfoot.R;
import com.fansfoot.fansfoot.Adapters.VideoRecycleViewAdapter;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by xamarin on 05/12/16.
 */

public class VideoPage  extends Fragment {

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



    String[] ur = { "https://www.youtube.com/watch?v=jdqsiFw74Jk",
            "https://www.youtube.com/watch?v=aJ7BoNG-r2c",
            "https://www.youtube.com/watch?v=jdqsiFw74Jk",
            "https://www.youtube.com/watch?v=jdqsiFw74Jk"};

    String [] points = {"1","2","3","2"};
    String[] comments = {"56","48","78","96"};
    URI[] uris;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.video_fragment,container,false);
        context = getActivity();
        final SwipeRefreshLayout swipe = (SwipeRefreshLayout) view.findViewById(R.id.VideoSwipe);
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
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.Videotoolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ImageButton refresh = (ImageButton) view.findViewById(R.id.cm_VideoToolBar_Refesh);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"Refreshing",Snackbar.LENGTH_SHORT).show();
            }
        });
        ImageButton back = (ImageButton) view.findViewById(R.id.cm_VideoToolBar_search);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        recyclerView = (RecyclerView) view.findViewById(R.id.VideoRecycleView);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerViewAdapter = new VideoRecycleViewAdapter(userDetail,ur,points,comments,context);
        recyclerView.setAdapter(recyclerViewAdapter);
        return  view;
    }
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        MenuInflater _menu_inflater = MainActivity.gettheMenuInflater();
//        _menu_inflater.inflate(R.menu.refresh_menu,menu);
//    }
}


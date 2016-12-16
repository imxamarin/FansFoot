package com.fansfoot.fansfoot.DefaultPages;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import com.fansfoot.fansfoot.Adapters.GifPageRecycleViewAdapter;
import com.fansfoot.fansfoot.MainActivity;
import com.fansfoot.fansfoot.R;

/**
 * Created by xamarin on 07/12/16.
 */

public class GifPage extends Fragment {
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
    String[] pics = {"http://www.redbitdev.com/wp-content/uploads/2014/04/xamarin.jpeg",
            "http://www.windows10update.com/wp-content/uploads/2016/03/xamarin_logo.jpg",
            "http://www.hexacta.com/wp-content/uploads/2016/03/xamarin.jpg",
            "https://sergeytihon.files.wordpress.com/2015/04/w2.jpg",
            "http://www.redbitdev.com/wp-content/uploads/2014/04/xamarin.jpeg"
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.animated_gif_fragment,container,false);
        context = getContext();
        final SwipeRefreshLayout swipe = (SwipeRefreshLayout) view.findViewById(R.id.GifSwipe);
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
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.giftoolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ImageButton refresh = (ImageButton) view.findViewById(R.id.cm_gifToolBar_Refesh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"Refreshing",Snackbar.LENGTH_SHORT).show();
            }
        });
        CheckBox back = (CheckBox) view.findViewById(R.id.cm_gifToolBar_search);
        back.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                getActivity().onBackPressed();
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.gifRecycle);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerViewAdapter = new GifPageRecycleViewAdapter(userDetail,pics,points,comments,context);
        recyclerView.setAdapter(recyclerViewAdapter);
        return view;
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

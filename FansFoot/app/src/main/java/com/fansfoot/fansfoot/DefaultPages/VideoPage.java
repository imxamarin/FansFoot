package com.fansfoot.fansfoot.DefaultPages;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

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

    public VideoPage() throws URISyntaxException {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_fragment,null,false);
        context = getActivity();
        recyclerView = (RecyclerView) view.findViewById(R.id.VideoRecycleView);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerViewAdapter = new VideoRecycleViewAdapter(userDetail,ur,points,comments,context);
        recyclerView.setAdapter(recyclerViewAdapter);
        return  view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuInflater _menu_inflater = MainActivity.gettheMenuInflater();
        _menu_inflater.inflate(R.menu.refresh_menu,menu);
    }
}


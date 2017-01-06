package com.fansfoot.fansfoot.DefaultPages;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fansfoot.fansfoot.API.ConstServer;
import com.fansfoot.fansfoot.API.FansfootServer;
import com.fansfoot.fansfoot.API.Post;
import com.fansfoot.fansfoot.API.YouTube;
import com.fansfoot.fansfoot.API.YoutubePost;
import com.fansfoot.fansfoot.Adapters.VideosRecycleViewAdapter;
import com.fansfoot.fansfoot.MainActivity;
import com.fansfoot.fansfoot.R;
import com.fansfoot.fansfoot.Adapters.VideoRecycleViewAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;
import com.mugen.attachers.BaseAttacher;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xamarin on 05/12/16.
 */

public class VideosPage  extends Fragment {

    Context context;
    public static FragmentManager fgi;
    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    RequestQueue mRequestQueue;
    YouTube fansfootServers;
    List<YoutubePost> posts = new ArrayList<>();
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    LinearLayoutManager recylerViewLayoutManager;
    int newValue  = 0;
    ProgressBar progressBar;
    private boolean isLoading = false;
    SharedPreferences sharedPreferencesBeta;
    ProgressDialog progress;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.videos_fragment,container,false);
        context = getActivity();
        progress = new ProgressDialog(getActivity());
        progress.setMessage("Refreshing the list");
        sharedPreferencesBeta =context.getSharedPreferences("FansFootPerfrence", Context.MODE_PRIVATE);
        progressBar = (ProgressBar) view.findViewById(R.id.videosProgressBar);
        AdView mAdView = (AdView) view.findViewById(R.id.adViewVideos);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        Cache cache = new DiskBasedCache(this.getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();
        SyncOP(newValue);
        fgi = getChildFragmentManager();
        final SwipeRefreshLayout swipe = (SwipeRefreshLayout) view.findViewById(R.id.VideosSwipe);
        swipe.setColorSchemeColors(getResources().getColor(R.color.colorPrimaryDarkest),getResources().getColor(R.color.holo_blue_light));
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Snackbar.make(view,"Refreshing",Snackbar.LENGTH_SHORT).show();

//                final Handler handler = new Handler();
//                Runnable runable = new Runnable() {
//                    @Override
//                    public void run() {
                posts.clear();
                newValue = 0;
                SyncOP(newValue);
                swipe.setRefreshing(false);
//                    }
//                };
//                handler.postDelayed(runable, 2000);

            }

        });
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.Videostoolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        CheckBox refresh = (CheckBox) view.findViewById(R.id.cm_VideosToolBar_Refesh);


        refresh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                progress.show();
                final Handler handler = new Handler();
                Runnable runable = new Runnable() {
                    @Override
                    public void run() {
                        posts.clear();
                        newValue = 0;
                        SyncOP(newValue);
                    }
                };
                handler.postDelayed(runable, 3000);
            }
        });





        recyclerView = (RecyclerView) view.findViewById(R.id.VideosRecycleView);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);

        BaseAttacher attacher = Mugen.with(recyclerView, new MugenCallbacks() {
            @Override
            public void onLoadMore() {
                SyncOP(newValue);
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @Override
            public boolean hasLoadedAllItems() {
                return false;
            }
        }).start();

        attacher.setLoadMoreEnabled(true);

        attacher.setLoadMoreOffset(2);


        recyclerViewAdapter = new VideosRecycleViewAdapter(context,posts);
        recyclerView.setAdapter(recyclerViewAdapter);
        return  view;
    }

    public void SyncOP(int pageNumber){
        if(pageNumber>0){
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setEnabled(true);
            isLoading=true;
        }
        String ModUrl = ConstServer._baseUrl+
                ConstServer._type+
                ConstServer.get_channel_type+
                ConstServer._ConCat+
                ConstServer._post_type+
                ConstServer.VideopostType+
                ConstServer._ConCat+
                ConstServer._pagesToLoad+pageNumber+
                ConstServer._ConCat+
                ConstServer._deviceToken+
                FirebaseInstanceId.getInstance().getToken()+
                ConstServer._ConCat+
                ConstServer._device_type+
                ConstServer._ConCat+
                ConstServer._USERID+
                sharedPreferencesBeta.getString("FbFFID", "");
        Log.d("yuha",""+ModUrl);
        JsonObjectRequest _JsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                ModUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if(progress.isShowing()){
                    progress.dismiss();
                }
                progressBar.setVisibility(View.GONE);
                progressBar.setEnabled(false);
                isLoading=false;
                newValue=newValue+1;
                Gson _Gson = new Gson();
                fansfootServers = _Gson.fromJson(response.toString(), YouTube.class);
                posts.addAll(fansfootServers.getPost());
                Log.d("wala", "" + posts.size());
                if (posts.size() != 0) {
                    recyclerViewAdapter.notifyDataSetChanged();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(progress.isShowing()){
                    progress.dismiss();
                }
                progressBar.setVisibility(View.GONE);
                progressBar.setEnabled(false);
                isLoading=false;
            }
        });
        mRequestQueue.add(_JsonObjectRequest);
    }

    public static FragmentManager getChildFragment(){
        return fgi;
    }



}


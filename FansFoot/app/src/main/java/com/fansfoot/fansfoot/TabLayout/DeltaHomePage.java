package com.fansfoot.fansfoot.TabLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.fansfoot.fansfoot.Adapters.DeltaHomeRecycleViewAdapter;
import com.fansfoot.fansfoot.MainActivity;
import com.fansfoot.fansfoot.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;
import com.mugen.attachers.BaseAttacher;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kafir on 09-Dec-16.
 */

public class DeltaHomePage extends Fragment {

    RequestQueue mRequestQueue;
    FansfootServer fansfootServers;
    List<Post> posts = new ArrayList<>();
    Context context;
    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    LinearLayoutManager recylerViewLayoutManager;
    int newValue  = 0;
    ProgressBar progressBar;
    SharedPreferences sharedPreferencesBeta;
    private boolean isLoading = false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.delta_home_fragment,container,false);
        context = this.getActivity();
        sharedPreferencesBeta =context.getSharedPreferences("FansFootPerfrence", Context.MODE_PRIVATE);
        progressBar = (ProgressBar) view.findViewById(R.id.deltaProgressBar);
        if (mRequestQueue == null) {
            Cache cache = new DiskBasedCache(MainActivity.getContext().getCacheDir(), 1024 * 1024); // 1MB cap
            Network network = new BasicNetwork(new HurlStack());
            mRequestQueue = new RequestQueue(cache, network);
            mRequestQueue.start();
        }
        SyncOP(newValue);
        final SwipeRefreshLayout swipe = (SwipeRefreshLayout) view.findViewById(R.id.DeltaSwipe);
        swipe.setColorSchemeColors(getResources().getColor(R.color.colorPrimaryDarkest),getResources().getColor(R.color.holo_blue_light));
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Snackbar.make(view,"Refreshing",Snackbar.LENGTH_SHORT).show();
                      posts.clear();
                        newValue = 0;
                        SyncOP(newValue);
                        swipe.setRefreshing(false);
            }

        });
        AdView mAdView = (AdView) view.findViewById(R.id.adViewDelta);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        recyclerView = (RecyclerView) view.findViewById(R.id.DeltaRecycleView);
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
        recyclerViewAdapter = new DeltaHomeRecycleViewAdapter(context,posts);
        recyclerView.setAdapter(recyclerViewAdapter);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        context=null;
        if (mRequestQueue != null) {
            mRequestQueue.stop();
        }
    }

    public void SyncOP(int pageNumber){
        if(pageNumber>0){
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setEnabled(true);
            isLoading=true;
        }
        String ModUrl = ConstServer._baseUrl+
                ConstServer._type+
                ConstServer.get_post_type+
                ConstServer._ConCat+
                ConstServer._post_type+
                ConstServer.FreshpostType+
                ConstServer._ConCat+
                ConstServer._pagesToLoad+pageNumber+
                ConstServer._ConCat+
                ConstServer._deviceToken+ FirebaseInstanceId.getInstance().getToken()+
                ConstServer._ConCat+
                ConstServer._device_type+
                ConstServer._ConCat+
                ConstServer._USERID+
                sharedPreferencesBeta.getString("FbFFID", "");

        JsonObjectRequest _JsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                ModUrl, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                if(context !=null) {
                    progressBar.setVisibility(View.GONE);
                    progressBar.setEnabled(false);
                    isLoading = false;
                    newValue = newValue + 1;
                    Gson _Gson = new Gson();
                    fansfootServers = _Gson.fromJson(response.toString(), FansfootServer.class);
                    posts.addAll(fansfootServers.getPost());
                    Log.d("wala", "" + posts.size());
                    if (posts.size() != 0) {
                        recyclerViewAdapter.notifyDataSetChanged();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                progressBar.setEnabled(false);
                isLoading=false;
            }
        });
        mRequestQueue.add(_JsonObjectRequest);
    }
}

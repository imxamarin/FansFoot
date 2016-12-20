package com.fansfoot.fansfoot.TabLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
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
import com.fansfoot.fansfoot.Adapters.BetaHomeRecycleViewAdapter;
import com.fansfoot.fansfoot.R;
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

public class BetaHomePage extends Fragment {

    RequestQueue mRequestQueue;
    FansfootServer fansfootServers;
    List<Post> posts = new ArrayList<>();
    Context context;
    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    LinearLayoutManager recylerViewLayoutManager;
    int newValue  = 0;
    ProgressDialog pd;
    private boolean isLoading = false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.beta_home_fragment,container,false);
        context = this.getActivity();
        pd = ProgressDialog.show(getActivity(), "", ConstServer.get_Load_Message, true);
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        Cache cache = new DiskBasedCache(this.getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();
        SyncOP(newValue);
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
                        newValue = 0;
                        SyncOP(newValue);
                        swipe.setRefreshing(false);
                    }
                };
                handler.postDelayed(runable, 2000);

            }

        });
        recyclerView = (RecyclerView) view.findViewById(R.id.BetaRecycleView);
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

        recyclerViewAdapter = new BetaHomeRecycleViewAdapter(context,posts);
        recyclerView.setAdapter(recyclerViewAdapter);
        return view;
    }


    public void SyncOP(int pageNumber){
        if(pageNumber>0){
            pd.show();
            isLoading=true;
        }
        String ModUrl = ConstServer._baseUrl+
                ConstServer._type+
                ConstServer.get_post_type+
                ConstServer._ConCat+
                ConstServer._post_type+
                ConstServer.TrendingpostType+
                ConstServer._ConCat+
                ConstServer._pagesToLoad+pageNumber+
                ConstServer._ConCat+
                ConstServer._deviceToken+"123456"+
                ConstServer._ConCat+
                ConstServer._device_type+
                ConstServer._ConCat+
                ConstServer._USERID+"123";


        JsonObjectRequest _JsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
               ModUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pd.dismiss();
                isLoading=false;
                newValue=newValue+1;
                Gson _Gson = new Gson();
                fansfootServers =  _Gson.fromJson(response.toString(), FansfootServer.class);
                posts.addAll(fansfootServers.getPost());
                Log.d("wala",""+posts.size());
                if( posts.size()!=0){
                    recyclerViewAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                isLoading=false;
            }
        });
        mRequestQueue.add(_JsonObjectRequest);
    }
}

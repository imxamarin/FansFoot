package com.fansfoot.fansfoot.DefaultPages;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

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
import com.facebook.CallbackManager;
import com.fansfoot.fansfoot.API.ConstServer;
import com.fansfoot.fansfoot.API.FansfootServer;
import com.fansfoot.fansfoot.API.Post;
import com.fansfoot.fansfoot.Adapters.SearchHomeRecycleViewAdapter;
import com.fansfoot.fansfoot.MainActivity;
import com.fansfoot.fansfoot.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;
import com.mugen.attachers.BaseAttacher;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.fansfoot.fansfoot.R.id.view;

/**
 * Created by xamarin on 26/12/16.
 */

public class SearchFragment extends Fragment {
     View view;
    RequestQueue mRequestQueue;
    FansfootServer fansfootServers;
    List<Post> posts = new ArrayList<>();
    Context context;
    SearchView searchView;
    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    private int previousTotal = 0;
    private boolean loading = true;
    CallbackManager callbackManager;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    LinearLayoutManager recylerViewLayoutManager;
    int newValue  = 0;
    ProgressBar progressBar;
    private boolean isLoading = false;
    String searchResult;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.search_view_fragment,null,false);
        context = this.getContext();

        Bundle bundle = getArguments();
         searchResult = bundle.getString("query_string");
        progressBar = (ProgressBar) view.findViewById(R.id.SearchProgressbar);
        if (mRequestQueue == null) {
            Cache cache = new DiskBasedCache(MainActivity.getContext().getCacheDir(), 1024 * 1024); // 1MB cap
            Network network = new BasicNetwork(new HurlStack());
            mRequestQueue = new RequestQueue(cache, network);
            mRequestQueue.start();
        }

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.SearchResulttoolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        CheckBox back = (CheckBox) view.findViewById(R.id.cm_SearchResultToolBar_search);
        back.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                getActivity().onBackPressed();
            }
        });

        SyncOP(newValue);
        final SwipeRefreshLayout swipe = (SwipeRefreshLayout) view.findViewById(R.id.SearchSwipe);
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

        AdView mAdView = (AdView) view.findViewById(R.id.adViewSearch);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        recyclerView = (RecyclerView) view.findViewById(R.id.SearchRecycleView);
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

        recyclerViewAdapter = new SearchHomeRecycleViewAdapter(context,posts);
        recyclerView.setAdapter(recyclerViewAdapter);
        return view;
    }

    public void SyncOP(int pageNumber){
        if(pageNumber>0){
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setEnabled(true);
            isLoading=true;
        }

        String ModUrl = ConstServer._baseUrl+
                ConstServer._type+
                ConstServer.get_search_type+
                ConstServer._ConCat+
                ConstServer.key+
                searchResult;

        Log.d("Mod",ModUrl);

        JsonObjectRequest _JsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                ModUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if(context !=null) {
                    progressBar.setVisibility(View.GONE);
                    progressBar.setEnabled(false);
                    isLoading = false;
                    if (response.has("message")) {
                        Toast.makeText(context, "No result found", Toast.LENGTH_SHORT).show();
                    } else {
                        Gson _Gson = new Gson();
                        newValue = newValue + 1;
                        fansfootServers = _Gson.fromJson(response.toString(), FansfootServer.class);
                        posts.addAll(fansfootServers.getPost());

                        if (posts.size() != 0) {

                            recyclerViewAdapter.notifyDataSetChanged();
                        }
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        context=null;
        if (mRequestQueue != null) {
            mRequestQueue.stop();
        }
    }
}
